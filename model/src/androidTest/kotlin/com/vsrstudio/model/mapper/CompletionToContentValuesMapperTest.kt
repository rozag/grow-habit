package com.vsrstudio.model.mapper

import android.content.ContentValues
import com.vsrstudio.entity.domain.Completion
import com.vsrstudio.entity.domain.Id
import com.vsrstudio.model.assertCompletionMatchesCv
import com.vsrstudio.model.generateCompletion
import com.vsrstudio.model.generateCompletionsList
import junit.framework.Assert.assertEquals
import org.junit.Test

class CompletionToContentValuesMapperTest {

    private val mapper = CompletionToContentValuesMapper()

    @Test
    fun mapCompletion_correctCvReturned() {
        val completion = generateCompletion(Id("completion_id"), Id("habit_id"))
        val cv = mapper.map(completion)
        assertCompletionMatchesCv(completion, cv)
    }

    @Test
    fun batchMapCompletions_correctCvListReturned() {
        val completions = generateCompletionsList(Id("habit_id"), 0)
        val cvList = mapper.batchMap(completions)
        completions.zip(cvList)
                .forEach { (completion, cv) -> assertCompletionMatchesCv(completion, cv) }
    }

    @Test
    fun batchMapEmpty_emptyCvListReturned() {
        val completions = emptyList<Completion>()
        val cvList = mapper.batchMap(completions)
        assertEquals(cvList, listOf<ContentValues>())
    }

}