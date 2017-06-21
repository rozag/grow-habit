package com.vsrstudio.arch

import io.reactivex.Observable

interface ArchView<S : ViewState, A : UserAction> {
    fun subscribeOnViewState(observable: Observable<S>)
    fun unsubscribeFromViewStat()
    fun observeUserActions(): Observable<A>
}