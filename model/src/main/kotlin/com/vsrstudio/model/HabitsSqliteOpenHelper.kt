package com.vsrstudio.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HabitsSqliteOpenHelper(context: Context) :
        SQLiteOpenHelper(context, DatabaseInfo.name, null, DatabaseInfo.version) {

    companion object Scheme {
        object DatabaseInfo {
            val name = "habits.sqlite"
            val version = 1
        }

        object Table {
            val habit = "habit"
            val completion = "completion"
            val habitSync = "habit_sync"
            val completionSync = "completion_sync"
        }

        object Habit {
            val id = "id"
            val title = "title"
            val created = "created"
            val updated = "updated"
        }

        object Completion {
            val id = "id"
            val habitId = "habit_id"
            val status = "status"
            val date = "date"
            val created = "created"
            val updated = "updated"
        }

        object HabitSync {
            val habitId = "habit_id"
            val synced = "synced"
        }

        object CompletionSync {
            val completionId = "completion_id"
            val synced = "synced"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${Table.habit} (" +
                "${Habit.id} TEXT NOT NULL CHECK(${Habit.id} != ''), " +
                "${Habit.title} TEXT NOT NULL CHECK(${Habit.title} != ''), " +
                "${Habit.created} INTEGER NOT NULL CHECK(${Habit.created} > 0), " +
                "${Habit.updated} INTEGER NOT NULL CHECK(${Habit.updated} > 0), " +
                "PRIMARY KEY(${Habit.id})" +
                ");")
        db?.execSQL("CREATE TABLE ${Table.habitSync} (" +
                "${HabitSync.habitId} TEXT NOT NULL CHECK(${HabitSync.habitId} != ''), " +
                "${HabitSync.synced} INTEGER NOT NULL CHECK(${HabitSync.synced}>=0 AND ${HabitSync.synced}<=1), " +
                "PRIMARY KEY(${HabitSync.habitId}), " +
                "FOREIGN KEY(${HabitSync.habitId}) REFERENCES ${Table.habit}(${Habit.id})" +
                ");")
        db?.execSQL("CREATE TABLE ${Table.completion} (" +
                "${Completion.id} TEXT NOT NULL CHECK(${Completion.id} != ''), " +
                "${Completion.habitId} TEXT NOT NULL CHECK(${Completion.habitId} != ''), " +
                "${Completion.status} INTEGER NOT NULL CHECK(${Completion.status}>=0 AND ${Completion.status}<=3), " +
                "${Completion.date} INTEGER NOT NULL CHECK(${Completion.date} > 0), " +
                "${Completion.created} INTEGER NOT NULL CHECK(${Completion.created} > 0), " +
                "${Completion.updated} INTEGER NOT NULL CHECK(${Completion.updated} > 0), " +
                "PRIMARY KEY(${Completion.id}), " +
                "FOREIGN KEY(${Completion.habitId}) REFERENCES ${Table.habit}(${Habit.id})" +
                ");")
        db?.execSQL("CREATE TABLE ${Table.completionSync} (" +
                "${CompletionSync.completionId} TEXT NOT NULL CHECK(${CompletionSync.completionId} != ''), " +
                "${CompletionSync.synced} INTEGER NOT NULL CHECK(${CompletionSync.synced}>=0 AND ${CompletionSync.synced}<=1), " +
                "PRIMARY KEY(${CompletionSync.completionId}), " +
                "FOREIGN KEY(${CompletionSync.completionId}) REFERENCES ${Table.completion}(${Completion.id}) "+
                ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        val upgradeTo = oldVersion + 1
//        while (upgradeTo<=newVersion) {
//            @Suppress("UNUSED_EXPRESSION")
//            when (upgradeTo) {
//                2 -> {
//                }
//            }
//        }
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        db?.setForeignKeyConstraintsEnabled(true)
    }

}