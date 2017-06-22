package com.vsrstudio.arch

interface Query<out T, in D> {
    fun query(storage: D) : List<T>
}