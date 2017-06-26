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
        // TODO add habit sync data
        // TODO add completions sync data
        val habitCv = habitToContentValuesMapper.map(item)
        writableDb.insert(Table.habit, null, habitCv)
        val completionsContentValues = completionToContentValuesMapper.batchMap(item.completions)
        completionsContentValues.forEach { completionCv ->
            writableDb.insert(Table.completion, null, completionCv)
        }
    }

    override fun add(items: Iterable<Habit>) {
        // TODO
    }

    override fun update(item: Habit) {
        // TODO
    }

    override fun update(items: Iterable<Habit>, update: Update<Habit, SQLiteDatabase>) {
        // TODO
    }

    override fun remove(item: Habit) {
        // TODO
    }

    override fun query(query: Query<Habit, SQLiteDatabase>): Observable<List<Habit>> {
        // TODO
        return Observable.empty()
    }

    private fun applyToWritableDb(func: (SQLiteDatabase) -> Unit) {
        writableDb.beginTransaction()
        func(writableDb)
        writableDb.setTransactionSuccessful()
        writableDb.endTransaction()
    }

}