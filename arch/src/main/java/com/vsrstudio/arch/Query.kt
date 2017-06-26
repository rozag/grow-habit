package com.vsrstudio.arch

interface Query<out Tp, in RdStrg> {
    fun query(readableStorage: RdStrg) : List<Tp>
}