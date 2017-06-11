package com.vsrstudio.growhabit.repo.habit

import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.ApiSpec

class ApiHabitRepo : HabitRepo<ApiSpec> {

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

    override fun query(spec: ApiSpec): List<Habit> {
        // TODO
        return emptyList()
    }

}