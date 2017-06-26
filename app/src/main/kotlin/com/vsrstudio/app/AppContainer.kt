package com.vsrstudio.app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.arch.Update
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.model.FirebaseIdGenerator
import com.vsrstudio.model.HabitsSqliteRepo

class AppContainer(val context: Context) {
    val firebaseIdGenerator: FirebaseIdGenerator = FirebaseIdGenerator()
    val habitsRepo: Repo<Habit, Query<Habit, SQLiteDatabase>, Update<Habit, SQLiteDatabase>> = HabitsSqliteRepo()
}