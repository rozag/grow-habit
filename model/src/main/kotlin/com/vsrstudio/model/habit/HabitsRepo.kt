package com.vsrstudio.model.habit

import com.vsrstudio.entity.Habit
import com.vsrstudio.model.Repo
import com.vsrstudio.model.Spec

interface HabitsRepo<in S : Spec> : Repo<Habit, S>