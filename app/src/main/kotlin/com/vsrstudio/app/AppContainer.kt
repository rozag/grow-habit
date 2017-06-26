package com.vsrstudio.app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.IdGenerator
import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.arch.Update
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.model.HabitsSqliteOpenHelper
import com.vsrstudio.model.HabitsSqliteRepo
import com.vsrstudio.model.mapper.CompletionFromCursorMapper
import com.vsrstudio.model.mapper.CompletionToContentValuesMapper
import com.vsrstudio.model.mapper.HabitFromCursorMapper
import com.vsrstudio.model.mapper.HabitToContentValuesMapper

class AppContainer(context: Context) {

    val idGenerator: IdGenerator = FirebaseIdGenerator()

    val habitsSqliteOpenHelper: HabitsSqliteOpenHelper = HabitsSqliteOpenHelper(context)

    val completionToContentValuesMapper = CompletionToContentValuesMapper()
    val completionFromCursorMapper = CompletionFromCursorMapper()
    val habitToContentValuesMapper = HabitToContentValuesMapper()
    val habitFromCursorMapper = HabitFromCursorMapper()

    val habitsRepo: Repo<Habit, Query<Habit, SQLiteDatabase>, Update<Habit, SQLiteDatabase>> =
            HabitsSqliteRepo(
                    habitsSqliteOpenHelper,
                    habitToContentValuesMapper,
                    habitFromCursorMapper,
                    completionToContentValuesMapper,
                    completionFromCursorMapper
            )

}