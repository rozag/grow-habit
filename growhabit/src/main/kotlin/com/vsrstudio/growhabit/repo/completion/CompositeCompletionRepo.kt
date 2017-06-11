package com.vsrstudio.growhabit.repo.completion

import com.vsrstudio.growhabit.model.Completion
import com.vsrstudio.growhabit.repo.CompositeSpec

class CompositeCompletionRepo : CompletionRepo<CompositeSpec> {

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

    override fun remove(spec: CompositeSpec) {
        // TODO
    }

    override fun query(spec: CompositeSpec): List<Completion> {
        // TODO
        return emptyList()
    }

}