package com.vsrstudio.model

import com.vsrstudio.arch.Repo

interface SqliteRepo<T, in Q : SqliteQuery<T>> : Repo<T, Q>