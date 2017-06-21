package com.vsrstudio.arch

import io.reactivex.Observable

interface Reducer<S : ViewState> {
    fun observeViewState(): Observable<S>
    fun unsubscribeFromModel()
}