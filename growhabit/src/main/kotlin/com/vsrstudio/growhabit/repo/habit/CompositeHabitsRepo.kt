package com.vsrstudio.growhabit.repo.habit

import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.CompositeSpec
import io.reactivex.Observable

class CompositeHabitsRepo : HabitsRepo<CompositeSpec> {

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

    override fun remove(spec: CompositeSpec) {
        // TODO
    }

    override fun query(spec: CompositeSpec): Observable<List<Habit>> {
        // TODO
        return Observable.fromCallable { emptyList<Habit>() }
    }

}