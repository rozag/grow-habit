package com.vsrstudio.view.model

data class Id(val value: String) {
    companion object {
        val EMPTY: Id = Id("")
    }
}