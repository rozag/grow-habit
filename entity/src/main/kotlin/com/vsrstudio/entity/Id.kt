package com.vsrstudio.entity

data class Id(val value: String) {
    companion object {
        val EMPTY: Id = Id("")
    }
}