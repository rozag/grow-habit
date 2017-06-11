package com.vsrstudio.growhabit.repo.habit

import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.Repo
import com.vsrstudio.growhabit.repo.Spec

interface HabitRepo<in S : Spec> : Repo<Habit, S>