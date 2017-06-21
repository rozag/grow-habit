package com.vsrstudio.arch

import io.reactivex.Observable

interface Controller<A : Action> {
    fun subscribeOnActions(observable: Observable<A>)
    fun unsubscribeFromActions()
}