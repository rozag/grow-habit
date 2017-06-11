package com.vsrstudio.growhabit.repo

interface Repo<T, in S : Spec> {
    fun add(item: T)
    fun add(items: Iterable<T>)
    fun update(item: T)
    fun remove(item: T)
    fun remove(spec: S)
    fun query(spec: S): List<T>
}