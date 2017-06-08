package com.vsrstudio.growhabit.arch

interface Reducer {
    val view: ArchView<*, *, *>
    fun bind()
    fun unbind()
}