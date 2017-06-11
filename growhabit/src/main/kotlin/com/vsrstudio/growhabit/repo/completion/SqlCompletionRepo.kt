package com.vsrstudio.growhabit.repo.completion

import com.vsrstudio.growhabit.model.Completion
import com.vsrstudio.growhabit.repo.Repo
import com.vsrstudio.growhabit.repo.SqlSpec

class SqlCompletionRepo : CompletionRepo<SqlSpec> {

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

    override fun remove(spec: SqlSpec) {
        // TODO
    }

    override fun query(spec: SqlSpec): List<Completion> {
        // TODO
        return emptyList()
    }

}