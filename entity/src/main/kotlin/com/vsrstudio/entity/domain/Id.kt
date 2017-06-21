package com.vsrstudio.entity.domain

data class Id(val value: String) {
    companion object {
        val EMPTY: Id = Id("")
    }
}