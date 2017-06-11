package com.vsrstudio.growhabit.repo

interface SqlSpec : Spec {
    fun query(): String
}