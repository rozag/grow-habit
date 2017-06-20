package com.vsrstudio.view.app.habits

import com.vsrstudio.view.arch.ArchView
import com.vsrstudio.view.model.Habit
import com.vsrstudio.view.model.Id
import com.vsrstudio.view.model.Title
import com.vsrstudio.view.repo.Spec
import com.vsrstudio.view.repo.habit.HabitsRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Test

class HabitsReducerTest {

    @Test
    fun habitsListRetrieved_correctViewStatePassedToView() {
        val expectedHabits = listOf<Habit>(
                Habit(Id("test_id_1"), Title("test_title_1"), mapOf()),
                Habit(Id("test_id_2"), Title("test_title_2"), mapOf()),
                Habit(Id("test_id_3"), Title("test_title_3"), mapOf()),
                Habit(Id("test_id_4"), Title("test_title_4"), mapOf()),
                Habit(Id("test_id_5"), Title("test_title_5"), mapOf())
        )
        val habitsRepo: HabitsRepo<Spec> = object : HabitsRepo<Spec> {
            override fun add(item: Habit) = Unit
            override fun add(items: Iterable<Habit>) = Unit
            override fun update(item: Habit) = Unit
            override fun remove(item: Habit) = Unit
            override fun remove(spec: Spec) = Unit
            override fun query(spec: Spec): Observable<List<Habit>> {
                return Observable.fromCallable { expectedHabits }
            }
        }
        val view = object : ArchView<HabitsController, HabitsReducer, HabitsViewState> {
            override val controller: HabitsController = HabitsController(habitsRepo)
            override val reducer: HabitsReducer = HabitsReducer(
                    this,
                    habitsRepo,
                    Schedulers.newThread()
            )

            override fun updateViewState(viewState: HabitsViewState) {
                assertEquals(expectedHabits, viewState.habits)
            }
        }
        view.reducer.subscribe()
    }

}