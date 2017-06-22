package com.vsrstudio.controller

import com.nhaarman.mockito_kotlin.verify
import com.vsrstudio.entity.domain.*
import com.vsrstudio.entity.useraction.*
import com.vsrstudio.model.HabitsRepo
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
    private val repo = mock(HabitsRepo::class.java)
    private val controller = HabitsController(repo, Schedulers.trampoline())

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
        val changedHabit = Habit(Id("id"), Title("title"), mapOf())
        val newCompletion = Completion.EMPTY
        subject.onNext(HabitCompletionChangedAction(changedHabit, newCompletion))
        val expectedHabit = changedHabit.addCompletion(Date.current(), newCompletion)
        verify(repo, times(1)).update(expectedHabit)
    }

    @Test
    fun habitAdded_repoAddCalled() {
        val newHabitTitle = Title("title")
        subject.onNext(HabitAddedAction(newHabitTitle))
        val expectedHabit = Habit(Id.EMPTY, newHabitTitle, mapOf())
        verify(repo, times(1)).add(expectedHabit)
    }

    @Test
    fun habitRemoved_repoRemoveCalled() {
        val removedHabit = Habit(Id("id"), Title("title"), mapOf())
        subject.onNext(HabitRemovedAction(removedHabit))
        verify(repo, times(1)).remove(removedHabit)
    }

    @Test
    fun habitRenamed_repoUpdateCalled() {
        val renamedHabit = Habit(Id("id"), Title("old_title"), mapOf())
        val newTitle = Title("new_title")
        subject.onNext(HabitRenamedAction(renamedHabit, newTitle))
        val expectedHabit = renamedHabit.rename(newTitle)
        verify(repo, times(1)).update(expectedHabit)
    }

}