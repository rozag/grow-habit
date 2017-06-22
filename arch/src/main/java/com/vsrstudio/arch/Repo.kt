package com.vsrstudio.arch

import io.reactivex.Observable

interface Repo<T, in S : Query<T, *>> {
    fun add(item: T)
    fun add(items: Iterable<T>)
    fun update(item: T)
    fun remove(item: T)
    fun query(spec: S): Observable<List<T>>
}