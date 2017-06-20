package com.vsrstudio.common.arch

interface Reducer {
    val view: ArchView<*, *, *>
    fun subscribe()
    fun unsubscribe()
}