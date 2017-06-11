package com.vsrstudio.growhabit.repo

interface ApiSpec : Spec {
    fun endpoint(): String
    fun params(): Map<String, String>
}