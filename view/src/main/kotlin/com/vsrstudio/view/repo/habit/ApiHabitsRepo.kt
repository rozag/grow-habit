package com.vsrstudio.view.repo.habit

import com.vsrstudio.view.model.Habit
import com.vsrstudio.view.repo.ApiSpec
import io.reactivex.Observable

class ApiHabitsRepo : HabitsRepo<ApiSpec> {

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

    override fun remove(spec: ApiSpec) {
        // TODO
    }

    override fun query(spec: ApiSpec): Observable<List<Habit>> {
        // TODO
        return Observable.fromCallable { emptyList<Habit>() }
    }

}