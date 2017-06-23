package com.vsrstudio.arch

interface Query<out Tp, in Strg> {
    fun query(storage: Strg) : List<Tp>
}