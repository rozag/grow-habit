package com.vsrstudio.growhabit.repo.completion

import com.vsrstudio.growhabit.model.Completion
import com.vsrstudio.growhabit.repo.Repo
import com.vsrstudio.growhabit.repo.Spec

interface CompletionRepo<in S : Spec> : Repo<Completion, S>