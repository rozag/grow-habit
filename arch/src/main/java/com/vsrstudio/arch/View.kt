package com.vsrstudio.arch

interface View<out C : Controller, out R : Reducer, in S : ViewState> {
    val controller: C
    val reducer: R
    fun updateViewState(viewState: S)
}