package com.vsrstudio.model.habit

import com.vsrstudio.model.ApiSpec
import io.reactivex.Observable

class ApiHabitsRepo : HabitsRepo<ApiSpec> {

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

    override fun remove(spec: ApiSpec) {
        // TODO
    }

    override fun query(spec: ApiSpec): Observable<List<com.vsrstudio.entity.Habit>> {
        // TODO
        return Observable.fromCallable { emptyList<com.vsrstudio.entity.Habit>() }
    }

}