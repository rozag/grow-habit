package com.vsrstudio.controller

import com.vsrstudio.arch.Controller
import com.vsrstudio.arch.IdGenerator
import com.vsrstudio.arch.Repo
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.entity.useraction.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HabitsController(val repo: Repo<Habit, *>,
                       val idGenerator: IdGenerator,
                       val observeScheduler: Scheduler = Schedulers.io()) : Controller<HabitsAction> {

    override fun subscribeOnActions(observable: Observable<HabitsAction>) {
        observable.observeOn(observeScheduler)
                .subscribe(
                        { action -> consumeAction(action) },
                        { throwable -> Timber.e(throwable) }
                )
    }

    private fun consumeAction(action: HabitsAction) = when (action) {
        is HabitCompletionChangedAction -> {
            val updatedHabit = action.habit.addCompletion(action.completion)
            repo.update(updatedHabit)
        }
        is HabitAddedAction -> {
            val newHabit = Habit(
                    Id(idGenerator.generate()),
                    action.title,
                    listOf(),
                    action.position
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