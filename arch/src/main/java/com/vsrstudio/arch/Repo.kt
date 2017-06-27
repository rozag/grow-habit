package com.vsrstudio.arch

import io.reactivex.Observable

interface Repo<Tp, in Qr : Query<Tp, *>, in Upd : Update<Tp, *>> {
    fun add(item: Tp)
    fun add(items: List<Tp>)
    fun update(item: Tp)
    fun update(items: List<Tp>, update: Upd)
    fun remove(item: Tp)
    fun query(query: Qr): Observable<List<Tp>>
}