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

class HabitsSqliteRepo(dbOpenHelper: HabitsSqliteOpenHelper,
                       val habitToContentValuesMapper: HabitToContentValuesMapper,
                       val habitFromCursorMapper: HabitFromCursorMapper,
                       val completionToContentValuesMapper: CompletionToContentValuesMapper,
                       val completionFromCursorMapper: CompletionFromCursorMapper) :
        Repo<Habit, Query<Habit, SQLiteDatabase>> {

    private val readableDb: SQLiteDatabase = dbOpenHelper.readableDatabase
    private val writableDb: SQLiteDatabase = dbOpenHelper.writableDatabase

    override fun add(itemToAdd: Habit) = applyToWritableDb { writableDb ->
        addHabitToWritableDb(itemToAdd, writableDb)
    }

    override fun add(itemsToAdd: List<Habit>) = applyToWritableDb { writableDb ->
        itemsToAdd.forEach { habit -> addHabitToWritableDb(habit, writableDb) }
        // TODO add habit sync data
        // TODO add completions sync data
    }

    override fun update(updatedItem: Habit) = applyToWritableDb { writableDb ->
        updateHabitWithWritableDb(updatedItem, writableDb)
    }

    override fun update(updatedItems: List<Habit>) = applyToWritableDb { writableDb ->
        updatedItems.forEach { habit -> updateHabitWithWritableDb(habit, writableDb) }
    }

    override fun remove(itemToRemove: Habit) {
        // TODO
    }

    override fun query(query: Query<Habit, SQLiteDatabase>): Observable<List<Habit>> {
        // TODO
        return Observable.empty()
    }

    private fun addHabitToWritableDb(habit: Habit, writableDb: SQLiteDatabase) {
        val habitCv = habitToContentValuesMapper.map(habit)
        writableDb.insert(Table.habit, null, habitCv)
        val completionsContentValues = completionToContentValuesMapper.batchMap(habit.completions)
        completionsContentValues.forEach { completionCv ->
            writableDb.insert(Table.completion, null, completionCv)
        }
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

    private fun applyToWritableDb(func: (SQLiteDatabase) -> Unit) {
        writableDb.beginTransaction()
        func(writableDb)
        writableDb.setTransactionSuccessful()
        writableDb.endTransaction()
    }

}