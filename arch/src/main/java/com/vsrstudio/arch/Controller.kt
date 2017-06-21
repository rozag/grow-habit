package com.vsrstudio.arch

import io.reactivex.Observable

interface Controller<A : UserAction> {
    fun subscribeOnUserActions(observable: Observable<A>)
    fun unsubscribeFromUserActions()
}