package com.vsrstudio.model.mapper

import android.content.ContentValues
import com.vsrstudio.entity.domain.Completion
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.CompletionEntry

// TODO: test
class CompletionToContentValuesMapper {

    fun map(completion: Completion): ContentValues {
        val cv = ContentValues()
        cv.put(CompletionEntry.id, completion.id.value)
        cv.put(CompletionEntry.habitId, completion.habitId.value)
        cv.put(CompletionEntry.status, completion.status.value)
        cv.put(CompletionEntry.date, completion.date.seconds)
        return cv
    }

    fun batchMap(completions: List<Completion>): List<ContentValues> {
        return completions.map { completion ->
            map(completion)
        }
    }

}