package com.vsrstudio.entity.useraction

import com.vsrstudio.arch.Action
import com.vsrstudio.entity.domain.Completion
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.domain.Title

sealed class HabitsAction : Action
data class HabitCompletionChangedAction(val habit: Habit, val completion: Completion) : HabitsAction()
data class HabitAddedAction(val title: Title, val position: Int) : HabitsAction()
data class HabitRemovedAction(val habit: Habit) : HabitsAction()
data class HabitRenamedAction(val habit: Habit, val title: Title) : HabitsAction()