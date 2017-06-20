package com.vsrstudio.model.habit

import com.vsrstudio.model.SqlSpec
import io.reactivex.Observable

class SqlHabitsRepo : HabitsRepo<SqlSpec> {

    override fun add(item: com.vsrstudio.entity.Habit) {
        // TODO
    }

    override fun add(items: Iterable<com.vsrstudio.entity.Habit>) {
        // TODO
    }

    override fun update(item: com.vsrstudio.entity.Habit) {
        // TODO
    }

    override fun remove(item: com.vsrstudio.entity.Habit) {
        // TODO
    }

    override fun remove(spec: SqlSpec) {
        // TODO
    }

    override fun query(spec: SqlSpec): Observable<List<com.vsrstudio.entity.Habit>> {
        // TODO
        return Observable.fromCallable { emptyList<com.vsrstudio.entity.Habit>() }
    }

}