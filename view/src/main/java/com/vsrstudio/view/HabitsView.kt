package com.vsrstudio.view

import android.content.Context
import android.widget.FrameLayout
import com.vsrstudio.arch.ArchView
import com.vsrstudio.entity.useraction.HabitsAction
import com.vsrstudio.entity.viewstate.HabitsViewState
import io.reactivex.Observable
import timber.log.Timber

class HabitsView(context: Context?) :
        FrameLayout(context),
        ArchView<HabitsViewState, HabitsAction> {

    init {
        Timber.d("init")
        inflate(context, R.layout.view_habits, this)
    }

    override fun subscribeOnViewState(observable: Observable<HabitsViewState>) {
        // TODO
    }

    override fun unsubscribeFromViewState() {
        // TODO
    }

    override fun observeActions(): Observable<HabitsAction> {
        // TODO
        return Observable.empty()
    }

}