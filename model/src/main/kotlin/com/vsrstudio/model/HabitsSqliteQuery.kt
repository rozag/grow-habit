package com.vsrstudio.model

import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.entity.domain.Habit

interface HabitsSqliteQuery : SqliteQuery<Habit>, HabitsQuery<SQLiteDatabase>