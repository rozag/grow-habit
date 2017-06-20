package com.vsrstudio.view.repo.habit

import com.vsrstudio.view.model.Habit
import com.vsrstudio.view.repo.Repo
import com.vsrstudio.view.repo.Spec

interface HabitsRepo<in S : Spec> : Repo<Habit, S>