package com.vsrstudio.model

import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.CompletionEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.HabitEntry
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.Table
import com.vsrstudio.model.mapper.CompletionFromCursorMapper
import com.vsrstudio.model.mapper.CompletionToContentValuesMapper
import com.vsrstudio.model.mapper.HabitFromCursorMapper
import com.vsrstudio.model.mapper.HabitToContentValuesMapper
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject

private typealias HabitList = List<Habit>
private typealias HabitSqliteQuery = Query<Habit, SQLiteDatabase>
private typealias QuerySubjectPair = Pair<HabitSqliteQuery, Subject<HabitList>>

class HabitsSqliteRepo(dbOpenHelper: HabitsSqliteOpenHelper,
                       val habitToContentValuesMapper: HabitToContentValuesMapper,
                       val habitFromCursorMapper: HabitFromCursorMapper,
                       val completionToContentValuesMapper: CompletionToContentValuesMapper,
                       val completionFromCursorMapper: CompletionFromCursorMapper) :
        Repo<Habit, HabitSqliteQuery> {

    private val readableDb: SQLiteDatabase = dbOpenHelper.readableDatabase
    private val writableDb: SQLiteDatabase = dbOpenHelper.writableDatabase
    private val subscribers: MutableMap<String, QuerySubjectPair> = mutableMapOf()

    override fun add(itemToAdd: Habit) = applyToWritableDb { writableDb ->
        addHabitToWritableDb(itemToAdd, writableDb)
    }

    override fun add(itemsToAdd: HabitList) = applyToWritableDb { writableDb ->
        itemsToAdd.forEach { habit -> addHabitToWritableDb(habit, writableDb) }
    }

    override fun update(updatedItem: Habit) = applyToWritableDb { writableDb ->
        updateHabitWithWritableDb(updatedItem, writableDb)
    }

    override fun update(updatedItems: HabitList) = applyToWritableDb { writableDb ->
        updatedItems.forEach { habit -> updateHabitWithWritableDb(habit, writableDb) }
    }

    override fun remove(itemToRemove: Habit) = applyToWritableDb { writableDb ->
        itemToRemove.completions.forEach { completion ->
            writableDb.delete(
                    Table.completion,
                    "${CompletionEntry.id} = ?",
                    arrayOf(completion.id.value)
            )
        }
        writableDb.delete(
                Table.habit,
                "${HabitEntry.id} = ?",
                arrayOf(itemToRemove.id.value)
        )
    }

    override fun query(query: HabitSqliteQuery): Observable<HabitList> {
        val subject = createOrRetrieveCachedSubject(query)
        val habits = query.query(readableDb)
        subject.onNext(habits)
        return subject
    }

    private fun addHabitToWritableDb(habit: Habit, writableDb: SQLiteDatabase) {
        val habitCv = habitToContentValuesMapper.map(habit)
        writableDb.insert(Table.habit, null, habitCv)
        val completionsContentValues = completionToContentValuesMapper.batchMap(habit.completions)
        completionsContentValues.forEach { completionCv ->
            writableDb.insert(Table.completion, null, completionCv)
        }
        // TODO add habit sync data
        // TODO add completions sync data
    }

    private fun updateHabitWithWritableDb(habit: Habit, writableDb: SQLiteDatabase) {
        val habitCv = habitToContentValuesMapper.map(habit)
        writableDb.update(
                Table.habit,
                habitCv,
                "${HabitEntry.id} = ?",
                arrayOf(habit.id.value)
        )
        habit.completions.map { completion ->
            val completionCv = completionToContentValuesMapper.map(completion)
            val insertResult = writableDb.insertWithOnConflict(
                    Table.completion,
                    null,
                    completionCv,
                    SQLiteDatabase.CONFLICT_IGNORE
            )
            if (insertResult == -1L) {
                writableDb.update(
                        Table.completion,
                        completionCv,
                        "${CompletionEntry.id} = ?",
                        arrayOf(completion.id.value)
                )
            }
        }
    }

    private inline fun applyToWritableDb(operation: (SQLiteDatabase) -> Unit) {
        writableDb.beginTransaction()
        operation(writableDb)
        writableDb.setTransactionSuccessful()
        writableDb.endTransaction()
        notifyAboutUpdates()
    }

    private fun notifyAboutUpdates() {
        for ((_, querySubjectPair) in subscribers.entries) {
            val (query, subject) = querySubjectPair
            val habits = query.query(readableDb)
            subject.onNext(habits)
        }
    }

    private fun createOrRetrieveCachedSubject(query: HabitSqliteQuery): Subject<HabitList> {
        val queryId = query.uniqueId()
        val querySubjectPair: QuerySubjectPair? = subscribers[queryId]
        val subject = if (querySubjectPair == null) {
            val newSubject = ReplaySubject.createWithSize<HabitList>(1)
            subscribers[queryId] = QuerySubjectPair(query, newSubject)
            newSubject
        } else {
            querySubjectPair.second
        }
        return subject
    }

}