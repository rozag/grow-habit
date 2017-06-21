package com.vsrstudio.view

import android.content.Context
import android.widget.FrameLayout
import com.vsrstudio.arch.ArchView
import com.vsrstudio.entity.useraction.HabitsAction
import com.vsrstudio.entity.viewstate.HabitsViewState
import io.reactivex.Observable

class HabitsView(context: Context?) :
        FrameLayout(context),
        ArchView<HabitsViewState, HabitsAction> {

    init {
        inflate(context, R.layout.view_habits, this)
    }

    override fun subscribeOnViewState(observable: Observable<HabitsViewState>) {
        // TODO
    }

    override fun unsubscribeFromViewState() {
        // TODO
    }

    override fun observeUserActions(): Observable<HabitsAction> {
        // TODO
        return Observable.empty()
    }

}