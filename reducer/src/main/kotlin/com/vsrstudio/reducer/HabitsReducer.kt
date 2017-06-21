package com.vsrstudio.reducer

import com.vsrstudio.arch.Reducer
import com.vsrstudio.entity.viewstate.HabitsViewState
import io.reactivex.Observable

class HabitsReducer : Reducer<HabitsViewState> {

    override fun observeViewState(): Observable<HabitsViewState> {
        // TODO
        return Observable.empty()
    }

    override fun unsubscribeFromModel() {
        // TODO
    }

}