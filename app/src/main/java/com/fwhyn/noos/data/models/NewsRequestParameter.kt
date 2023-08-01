package com.fwhyn.noos.data.models

import java.io.Serializable

data class NewsRequestParameter(
    val keyword: String? = null,
    val category: String? = null,
    val source: String? = null
) : Serializable