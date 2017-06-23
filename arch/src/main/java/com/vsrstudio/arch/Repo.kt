package com.vsrstudio.arch

import io.reactivex.Observable

interface Repo<Tp, in Qr : Query<Tp, *>> {
    fun add(item: Tp)
    fun add(items: Iterable<Tp>)
    fun update(item: Tp)
    fun remove(item: Tp)
    fun query(spec: Qr): Observable<List<Tp>>
}