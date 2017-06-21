package com.vsrstudio.arch

import io.reactivex.Observable

interface Controller<A : Action> {
    fun subscribeOnUserActions(observable: Observable<A>)
    fun unsubscribeFromUserActions()
}