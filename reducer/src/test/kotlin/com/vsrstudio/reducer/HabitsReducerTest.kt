package com.vsrstudio.reducer

import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Repo
import com.vsrstudio.arch.Update
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.entity.domain.Title
import com.vsrstudio.entity.viewstate.HabitsViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Test

class HabitsReducerTest {

    private val habits = listOf(
            Habit(Id("id_1"), Title("title_1"), listOf(), 0),
            Habit(Id("id_2"), Title("title_2"), listOf(), 1),
            Habit(Id("id_3"), Title("title_3"), listOf(), 2)
    )
    private val subject: Subject<List<Habit>> = PublishSubject.create()
    private val repo = object : Repo<Habit, Query<Habit, *>, Update<Habit, *>> {
        override fun add(item: Habit) = Unit
        override fun add(items: List<Habit>) = Unit
        override fun update(item: Habit) = Unit
        override fun update(items: List<Habit>, update: Update<Habit, *>) = Unit
        override fun remove(item: Habit) = Unit
        override fun query(query: Query<Habit, *>): Observable<List<Habit>> = subject
    }
    private val reducer = HabitsReducer(repo)

    @After
    fun tearDown() {
        reducer.unsubscribeFromModel()
    }

    @Test
    fun modelUpdatedWhileSubscribed_correctViewStateEmitted() {
        val expectedViewState = HabitsViewState(habits)
        reducer.observeViewState()
                .subscribe { habitsViewState ->
                    assertEquals(habitsViewState, expectedViewState)
                }
        subject.onNext(habits)
    }

    @Test
    fun modelUpdatedWhileUnsubscribed_nothingEmitted() {
        val testObserver = reducer.observeViewState().test()
        reducer.unsubscribeFromModel()
        subject.onNext(habits)
        testObserver.assertNoValues()
    }

}