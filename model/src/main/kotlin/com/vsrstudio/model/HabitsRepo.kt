package com.vsrstudio.model

import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.entity.domain.Habit

interface HabitsRepo<in Q : Query<Habit, *>> : Repo<Habit, Q>