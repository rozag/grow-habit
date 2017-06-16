package com.vsrstudio.growhabit.app.habits

import com.vsrstudio.growhabit.arch.ArchView
import com.vsrstudio.growhabit.arch.Reducer
import com.vsrstudio.growhabit.model.Habit
import com.vsrstudio.growhabit.repo.SqlSpec
import com.vsrstudio.growhabit.repo.habit.HabitsRepo
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HabitsReducer(
        override val view: ArchView<*, *, HabitsViewState>,
        val habitsRepo: HabitsRepo<SqlSpec>,
        val observeScheduler: Scheduler = AndroidSchedulers.mainThread()
) : Reducer {

    val disposable: CompositeDisposable = CompositeDisposable()

    override fun subscribe() {
        habitsRepo.query(object : SqlSpec {
            override fun query() = ""
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(observeScheduler)
                .subscribe(object : Observer<List<Habit>> {
                    override fun onComplete() {}

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(habits: List<Habit>?) {
                        habits?.let { habits -> view.updateViewState(HabitsViewState(habits)) }
                    }

                    override fun onError(e: Throwable?) {}
                })
    }

    override fun unsubscribe() {
        disposable.dispose()
    }

}