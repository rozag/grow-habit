package com.vsrstudio.growhabit.repo.habit

import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.SqlSpec
import io.reactivex.Observable

class SqlHabitsRepo : HabitsRepo<SqlSpec> {

    override fun add(item: Habit) {
        // TODO
    }

    override fun add(items: Iterable<Habit>) {
        // TODO
    }

    override fun update(item: Habit) {
        // TODO
    }

    override fun remove(item: Habit) {
        // TODO
    }

    override fun remove(spec: SqlSpec) {
        // TODO
    }

    override fun query(spec: SqlSpec): Observable<List<Habit>> {
        // TODO
        return Observable.fromCallable { emptyList<Habit>() }
    }

}