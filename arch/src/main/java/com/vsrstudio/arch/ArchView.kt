package com.vsrstudio.arch

import io.reactivex.Observable

interface ArchView<St : ViewState, Act : Action> {
    fun subscribeOnViewState(observable: Observable<St>)
    fun unsubscribeFromViewState()
    fun observeActions(): Observable<Act>
    fun stopEmittingActions()
}