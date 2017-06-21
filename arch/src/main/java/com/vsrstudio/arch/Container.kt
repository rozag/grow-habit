package com.vsrstudio.arch

interface Container<S : ViewState, A : UserAction> {
    fun init()
    fun finish()
}