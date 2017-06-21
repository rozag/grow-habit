package com.vsrstudio.arch

interface Reducer {
    val view: View<*, *, *>
    fun subscribe()
    fun unsubscribe()
}