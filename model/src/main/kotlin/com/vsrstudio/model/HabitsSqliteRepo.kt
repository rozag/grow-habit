package com.vsrstudio.model

import com.vsrstudio.entity.domain.Habit
import io.reactivex.Observable

class HabitsSqliteRepo : HabitsRepo<HabitsSqliteQuery>, SqliteRepo<Habit, HabitsSqliteQuery> {

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

    override fun query(spec: HabitsSqliteQuery): Observable<List<Habit>> {
        // TODO
        return Observable.empty()
    }

}