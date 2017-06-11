package com.vsrstudio.growhabit.repo.completion

import com.vsrstudio.growhabit.model.Completion
import com.vsrstudio.growhabit.repo.ApiSpec

class ApiCompletionRepo : CompletionRepo<ApiSpec> {

    override fun add(item: Completion) {
        // TODO
    }

    override fun add(items: Iterable<Completion>) {
        // TODO
    }

    override fun update(item: Completion) {
        // TODO
    }

    override fun remove(item: Completion) {
        // TODO
    }

    override fun remove(spec: ApiSpec) {
        // TODO
    }

    override fun query(spec: ApiSpec): List<Completion> {
        // TODO
        return emptyList()
    }

}