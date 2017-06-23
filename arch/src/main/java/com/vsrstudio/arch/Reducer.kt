package com.vsrstudio.arch

import io.reactivex.Observable

interface Reducer<St : ViewState> {
    fun observeViewState(): Observable<St>
    fun unsubscribeFromModel()
}