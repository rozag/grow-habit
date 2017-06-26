package com.vsrstudio.arch

// TODO: standardise all generics' names
interface Update<in Tp, in WrStrg> {
    fun update(items: Iterable<Tp>, writableStorage: WrStrg)
}