package com.vsrstudio.model.habit

import com.vsrstudio.model.CompositeSpec
import io.reactivex.Observable

class CompositeHabitsRepo : HabitsRepo<CompositeSpec> {

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

    override fun remove(spec: CompositeSpec) {
        // TODO
    }

    override fun query(spec: CompositeSpec): Observable<List<com.vsrstudio.entity.Habit>> {
        // TODO
        return Observable.fromCallable { emptyList<com.vsrstudio.entity.Habit>() }
    }

}