package com.vsrstudio.view.arch

interface Reducer {
    val view: ArchView<*, *, *>
    fun subscribe()
    fun unsubscribe()
}