package com.vsrstudio.view.repo

interface SqlSpec : Spec {
    fun query(): String
}