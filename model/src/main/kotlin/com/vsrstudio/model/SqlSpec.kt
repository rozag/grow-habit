package com.vsrstudio.model

interface SqlSpec : Spec {
    fun query(): String
}