package com.vsrstudio.arch

interface Container<S : ViewState, A : Action> {
    fun init()
    fun finish()
}