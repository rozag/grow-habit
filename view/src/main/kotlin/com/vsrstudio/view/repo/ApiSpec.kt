package com.vsrstudio.view.repo

interface ApiSpec : Spec {
    fun endpoint(): String
    fun params(): Map<String, String>
}