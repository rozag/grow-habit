package com.vsrstudio.model.mapper

import android.content.ContentValues
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.HabitEntry

// TODO: test
class HabitToContentValuesMapper {

    fun map(habit: Habit): ContentValues {
        val cv = ContentValues()
        cv.put(HabitEntry.id, habit.id.value)
        cv.put(HabitEntry.title, habit.title.value)
        cv.put(HabitEntry.position, habit.position)
        return cv
    }

    fun batchMap(habits: List<Habit>): List<ContentValues> {
        return habits.map { habit ->
            map(habit)
        }
    }

}