package com.vsrstudio.reducer

import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.entity.domain.Title
import com.vsrstudio.entity.viewstate.HabitsViewState
import com.vsrstudio.model.HabitsQuery
import com.vsrstudio.model.HabitsRepo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class HabitsReducerTest {

    private val habits = listOf(
            Habit(Id("id_1"), Title("title_1"), mapOf()),
            Habit(Id("id_2"), Title("title_2"), mapOf()),
            Habit(Id("id_3"), Title("title_3"), mapOf())
    )
    private val subject: Subject<List<Habit>> = PublishSubject.create()
    private val repo = object : HabitsRepo<HabitsQuery<*>> {
        override fun add(item: Habit) = Unit
        override fun add(items: Iterable<Habit>) = Unit
        override fun update(item: Habit) = Unit
        override fun remove(item: Habit) = Unit
        override fun query(spec: HabitsQuery<*>): Observable<List<Habit>> {
            return subject
        }
    }
    private val reducer = HabitsReducer(repo)

    @Before
    fun setUp() {
    }

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