package com.vsrstudio.model

import com.vsrstudio.arch.Query
import com.vsrstudio.entity.domain.Habit

interface HabitsQuery<in S> : Query<Habit, S>