package com.vsrstudio.model

import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.arch.Update
import com.vsrstudio.entity.domain.Habit
import io.reactivex.Observable

class HabitsSqliteRepo : Repo<Habit, Query<Habit, SQLiteDatabase>, Update<Habit, SQLiteDatabase>> {

    override fun add(item: Habit) {
        // TODO
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

}