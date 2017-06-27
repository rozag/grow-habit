package com.vsrstudio.model

import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.arch.Update
import com.vsrstudio.entity.domain.Habit
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
        Repo<Habit, Query<Habit, SQLiteDatabase>, Update<Habit, SQLiteDatabase>> {

    private val readableDb: SQLiteDatabase = dbOpenHelper.readableDatabase
    private val writableDb: SQLiteDatabase = dbOpenHelper.writableDatabase

    override fun add(item: Habit) = applyToWritableDb { writableDb ->
        addHabitToWritableDb(item, writableDb)
    }

    override fun add(items: List<Habit>) = applyToWritableDb { writableDb ->
        items.map { habit -> addHabitToWritableDb(habit, writableDb) }
        // TODO add habit sync data
        // TODO add completions sync data
    }

    override fun update(item: Habit) {
        // TODO
    }

    override fun update(items: List<Habit>, update: Update<Habit, SQLiteDatabase>) {
        // TODO
    }

    override fun remove(item: Habit) {
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

    private fun applyToWritableDb(func: (SQLiteDatabase) -> Unit) {
        writableDb.beginTransaction()
        func(writableDb)
        writableDb.setTransactionSuccessful()
        writableDb.endTransaction()
    }

}