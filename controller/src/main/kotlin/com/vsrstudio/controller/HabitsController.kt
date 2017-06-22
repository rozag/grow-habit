package com.vsrstudio.controller

import com.vsrstudio.arch.Controller
import com.vsrstudio.entity.domain.Date
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.entity.useraction.*
import com.vsrstudio.model.HabitsRepo
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HabitsController(val repo: HabitsRepo<*>,
                       val observeScheduler: Scheduler = Schedulers.io()) :
        Controller<HabitsAction> {

    override fun subscribeOnActions(observable: Observable<HabitsAction>) {
        observable.observeOn(observeScheduler)
                .subscribe(
                        {action -> consumeAction(action)},
                        {throwable -> Timber.e(throwable)}
                )
    }

    private fun consumeAction(action: HabitsAction) = when(action) {
        is HabitCompletionChangedAction -> {
            val updatedHabit = action.habit.addCompletion(
                    Date.current(),
                    action.completion
            )
            repo.update(updatedHabit)
        }
        is HabitAddedAction -> {
            val newHabit = Habit(
                    Id.EMPTY,
                    action.title,
                    mapOf()
            )
            repo.add(newHabit)
        }
        is HabitRemovedAction -> {
            repo.remove(action.habit)
        }
        is HabitRenamedAction -> {
            repo.update(action.habit.rename(action.title))
        }
    }

}