package com.vsrstudio.arch

import io.reactivex.Observable

interface ArchView<S : ViewState, A : Action> {
    fun subscribeOnViewState(observable: Observable<S>)
    fun unsubscribeFromViewState()
    fun observeActions(): Observable<A>
    fun stopEmittingActions()
}