package com.vsrstudio.entity.domain

data class Id(val value: String) {
    companion object {
        val EMPTY: com.vsrstudio.entity.domain.Id = com.vsrstudio.entity.domain.Id("")
    }
}