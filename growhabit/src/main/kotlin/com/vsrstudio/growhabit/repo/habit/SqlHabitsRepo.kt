package com.vsrstudio.growhabit.repo.habit

import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.SqlSpec

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

    override fun query(spec: SqlSpec): List<Habit> {
        // TODO
        return emptyList()
    }

}