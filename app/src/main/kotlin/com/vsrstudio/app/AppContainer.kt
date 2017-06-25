package com.vsrstudio.app

import android.content.Context
import com.vsrstudio.model.HabitsRepo
import com.vsrstudio.model.HabitsSqliteQuery
import com.vsrstudio.model.HabitsSqliteRepo

class AppContainer(val context: Context) {
    val habitsRepo: HabitsRepo<HabitsSqliteQuery> = HabitsSqliteRepo()
}