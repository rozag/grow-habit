package com.vsrstudio.arch

import io.reactivex.Observable

interface Repo<Tp, in Qr : Query<Tp, *>> {
    fun add(itemToAdd: Tp)
    fun add(itemsToAdd: List<Tp>)
    fun update(updatedItem: Tp)
    fun update(updatedItems: List<Tp>)
    fun remove(itemToRemove: Tp)
    fun query(query: Qr): Observable<List<Tp>>
}