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

        object HabitEntry {
            val id = "id"
            val title = "title"
            val position = "position"
        }

        object CompletionEntry {
            val id = "id"
            val habitId = "habit_id"
            val status = "status"
            val date = "date"
        }

        object HabitSyncEntry {
            val habitId = "habit_id"
            val synced = "synced"
        }

        object CompletionSyncEntry {
            val completionId = "completion_id"
            val synced = "synced"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${Table.habit} (" +
                "${HabitEntry.id} TEXT NOT NULL CHECK(${HabitEntry.id} != ''), " +
                "${HabitEntry.title} TEXT NOT NULL CHECK(${HabitEntry.title} != ''), " +
                "${HabitEntry.position} INTEGER NOT NULL CHECK(${HabitEntry.position} >= 0), " +
                "PRIMARY KEY(${HabitEntry.id})" +
                ");")
        db?.execSQL("CREATE TABLE ${Table.habitSync} (" +
                "${HabitSyncEntry.habitId} TEXT NOT NULL CHECK(${HabitSyncEntry.habitId} != ''), " +
                "${HabitSyncEntry.synced} INTEGER NOT NULL CHECK(${HabitSyncEntry.synced}>=0 AND ${HabitSyncEntry.synced}<=1), " +
                "PRIMARY KEY(${HabitSyncEntry.habitId}), " +
                "FOREIGN KEY(${HabitSyncEntry.habitId}) REFERENCES ${Table.habit}(${HabitEntry.id})" +
                ");")
        db?.execSQL("CREATE TABLE ${Table.completion} (" +
                "${CompletionEntry.id} TEXT NOT NULL CHECK(${CompletionEntry.id} != ''), " +
                "${CompletionEntry.habitId} TEXT NOT NULL CHECK(${CompletionEntry.habitId} != ''), " +
                "${CompletionEntry.status} INTEGER NOT NULL CHECK(${CompletionEntry.status}>=0 AND ${CompletionEntry.status}<=3), " +
                "${CompletionEntry.date} INTEGER NOT NULL CHECK(${CompletionEntry.date} > 0), " +
                "PRIMARY KEY(${CompletionEntry.id}), " +
                "FOREIGN KEY(${CompletionEntry.habitId}) REFERENCES ${Table.habit}(${HabitEntry.id})" +
                ");")
        db?.execSQL("CREATE TABLE ${Table.completionSync} (" +
                "${CompletionSyncEntry.completionId} TEXT NOT NULL CHECK(${CompletionSyncEntry.completionId} != ''), " +
                "${CompletionSyncEntry.synced} INTEGER NOT NULL CHECK(${CompletionSyncEntry.synced}>=0 AND ${CompletionSyncEntry.synced}<=1), " +
                "PRIMARY KEY(${CompletionSyncEntry.completionId}), " +
                "FOREIGN KEY(${CompletionSyncEntry.completionId}) REFERENCES ${Table.completion}(${CompletionEntry.id}) "+
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