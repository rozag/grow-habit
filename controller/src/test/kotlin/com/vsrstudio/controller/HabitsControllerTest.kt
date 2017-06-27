package com.vsrstudio.controller

import com.nhaarman.mockito_kotlin.verify
import com.vsrstudio.arch.IdGenerator
import com.vsrstudio.arch.Repo
import com.vsrstudio.entity.domain.*
import com.vsrstudio.entity.useraction.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times

class HabitsControllerTest {

    private val subject: Subject<HabitsAction> = PublishSubject.create()
    @Suppress("UNCHECKED_CAST")
    private val repo = mock(Repo::class.java) as Repo<Habit, *>
    private val idGenerator = object : IdGenerator {
        override fun generate(): String = "test_id"
    }
    private val controller = HabitsController(repo, idGenerator, Schedulers.trampoline())

    @Before
    fun setUp() {
        controller.subscribeOnActions(subject)
    }

    @After
    fun tearDown() {
        subject.onComplete()
    }

    @Test
    fun habitCompletionChanged_repoUpdateCalled() {
        val changedHabit = Habit(Id("id"), Title("title"), listOf(), 0)
        val newCompletion = Completion(
                Id("completion_id"),
                changedHabit.id,
                Completion.Status.EMPTY,
                Date.current()
        )
        subject.onNext(HabitCompletionChangedAction(changedHabit, newCompletion))
        val expectedHabit = changedHabit.addCompletion(newCompletion)
        verify(repo, times(1)).update(expectedHabit)
    }

    @Test
    fun habitAdded_repoAddCalled() {
        val newHabitTitle = Title("title")
        subject.onNext(HabitAddedAction(newHabitTitle, 0))
        val expectedHabit = Habit(Id("test_id"), newHabitTitle, listOf(), 0)
        verify(repo, times(1)).add(expectedHabit)
    }

    @Test
    fun habitRemoved_repoRemoveCalled() {
        val removedHabit = Habit(Id("id"), Title("title"), listOf(), 0)
        subject.onNext(HabitRemovedAction(removedHabit))
        verify(repo, times(1)).remove(removedHabit)
    }

    @Test
    fun habitRenamed_repoUpdateCalled() {
        val renamedHabit = Habit(Id("id"), Title("old_title"), listOf(), 0)
        val newTitle = Title("new_title")
        subject.onNext(HabitRenamedAction(renamedHabit, newTitle))
        val expectedHabit = renamedHabit.rename(newTitle)
        verify(repo, times(1)).update(expectedHabit)
    }

}