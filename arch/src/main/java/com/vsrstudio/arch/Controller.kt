package com.vsrstudio.arch

import io.reactivex.Observable

interface Controller<Act : Action> {
    fun subscribeOnActions(observable: Observable<Act>)
}