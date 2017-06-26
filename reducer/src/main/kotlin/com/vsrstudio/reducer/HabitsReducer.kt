package com.vsrstudio.reducer

import android.database.sqlite.SQLiteDatabase
import com.vsrstudio.arch.Query
import com.vsrstudio.arch.Reducer
import com.vsrstudio.arch.Repo
import com.vsrstudio.arch.Update
import com.vsrstudio.entity.domain.Habit
import com.vsrstudio.entity.viewstate.HabitsViewState
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

// TODO: specify query
class HabitsReducer(val repo: Repo<Habit, Query<Habit, SQLiteDatabase>, Update<Habit, SQLiteDatabase>>) : Reducer<HabitsViewState> {

    private val subject: Subject<HabitsViewState> = PublishSubject.create()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun observeViewState(): Observable<HabitsViewState> {
        repo.query(object : Query<Habit, SQLiteDatabase> {
            override fun query(readableStorage: SQLiteDatabase): List<Habit> = emptyList()
        }).subscribe(object : Observer<List<Habit>> {
            override fun onNext(habits: List<Habit>) {
                subject.onNext(HabitsViewState(habits))
            }

            override fun onError(throwable: Throwable) {
                Timber.e(throwable)
            }

            override fun onComplete() = Unit
            override fun onSubscribe(disposable: Disposable) {
                compositeDisposable.add(disposable)
            }
        })
        return subject
    }

    override fun unsubscribeFromModel() {
        compositeDisposable.dispose()
    }

}