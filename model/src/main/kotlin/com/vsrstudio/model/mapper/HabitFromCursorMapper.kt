package com.vsrstudio.model.mapper

import android.database.Cursor
import com.vsrstudio.entity.domain.Completion
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.entity.domain.Title
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.HabitEntry

class HabitFromCursorMapper {

    fun map(cursor: Cursor, completions: List<Completion> = listOf()): Habit {
        if (cursor.count == 0) {
            throw IllegalArgumentException("Cursor is empty")
        }
        val idColInd = cursor.getColumnIndex(HabitEntry.id)
        val titleColInd = cursor.getColumnIndex(HabitEntry.title)
        val positionColInd = cursor.getColumnIndex(HabitEntry.position)
        cursor.moveToFirst()
        val id = Id(cursor.getString(idColInd))
        val title = Title(cursor.getString(titleColInd))
        val position = cursor.getInt(positionColInd)
        return Habit(id, title, completions, position)
    }

    fun batchMap(cursor: Cursor, completionsMap: Map<String, List<Completion>> = mapOf()): List<Habit> {
        val habits = ArrayList<Habit>(cursor.count)
        val idColInd = cursor.getColumnIndex(HabitEntry.id)
        val titleColInd = cursor.getColumnIndex(HabitEntry.title)
        val positionColInd = cursor.getColumnIndex(HabitEntry.position)
        cursor.moveToFirst()
        if (cursor.count > 0) {
            do {
                val id = Id(cursor.getString(idColInd))
                val title = Title(cursor.getString(titleColInd))
                val position = cursor.getInt(positionColInd)
                val habit = Habit(
                        id,
                        title,
                        if (completionsMap.isEmpty()) {
                            listOf()
                        } else {
                            completionsMap[id.value] ?: listOf()
                        },
                        position
                )
                habits.add(habit)
            } while (cursor.moveToNext())
        }
        return habits
    }

}