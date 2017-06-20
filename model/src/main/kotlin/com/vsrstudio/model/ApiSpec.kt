package com.vsrstudio.model

interface ApiSpec : Spec {
    fun endpoint(): String
    fun params(): Map<String, String>
}