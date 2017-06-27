package com.vsrstudio.model.mapper

import android.database.Cursor
import com.vsrstudio.entity.domain.Completion
import com.vsrstudio.entity.domain.Date
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.model.HabitsSqliteOpenHelper.Scheme.CompletionEntry

// TODO: test
class CompletionFromCursorMapper {

    fun map(cursor: Cursor): Completion {
        val idColInd = cursor.getColumnIndex(CompletionEntry.id)
        val habitIdColInd = cursor.getColumnIndex(CompletionEntry.habitId)
        val statusColInd = cursor.getColumnIndex(CompletionEntry.status)
        val dateColInd = cursor.getColumnIndex(CompletionEntry.date)
        cursor.moveToFirst()
        val id = Id(cursor.getString(idColInd))
        val habitId = Id(cursor.getString(habitIdColInd))
        val status = Completion.Status.fromInt(cursor.getInt(statusColInd))
        val date = Date(cursor.getLong(dateColInd))
        return Completion(id, habitId, status, date)
    }

    fun batchMap(cursor: Cursor): List<Completion> {
        val completions = ArrayList<Completion>(cursor.count)
        val idColInd = cursor.getColumnIndex(CompletionEntry.id)
        val habitIdColInd = cursor.getColumnIndex(CompletionEntry.habitId)
        val statusColInd = cursor.getColumnIndex(CompletionEntry.status)
        val dateColInd = cursor.getColumnIndex(CompletionEntry.date)
        cursor.moveToFirst()
        if (cursor.count > 0) {
            do {
                val id = Id(cursor.getString(idColInd))
                val habitId = Id(cursor.getString(habitIdColInd))
                val status = Completion.Status.fromInt(cursor.getInt(statusColInd))
                val date = Date(cursor.getLong(dateColInd))
                val completion = Completion(id, habitId, status, date)
                completions.add(completion)
            } while (cursor.moveToNext())
        }
        return completions
    }

}