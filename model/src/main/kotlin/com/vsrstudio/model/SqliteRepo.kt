package com.vsrstudio.model

import com.vsrstudio.arch.Repo

interface SqliteRepo<Tp, in Qr : SqliteQuery<Tp>> : Repo<Tp, Qr>