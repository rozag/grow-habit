package com.vsrstudio.growhabit.arch

interface ArchView<out C : Controller, out R : Reducer, in S : ViewState> {
    val controller: C
    val reducer: R
    fun updateViewState(viewState: S)
}