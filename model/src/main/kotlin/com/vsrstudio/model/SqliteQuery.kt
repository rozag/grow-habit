package com.vsrstudio.model

import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query

interface SqliteQuery<out Tp> : Query<Tp, SQLiteDatabase>