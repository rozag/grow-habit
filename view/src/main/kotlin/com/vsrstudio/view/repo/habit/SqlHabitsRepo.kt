package com.vsrstudio.view.repo.habit

import com.vsrstudio.view.model.Habit
import com.vsrstudio.view.repo.SqlSpec
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