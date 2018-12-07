package com.nasgrad.api.model

import android.support.annotation.Keep

@Keep
data class Issue(val id: String,
                 val ownerId: String?,
                 val title: String?,
                 val description: String?,
                 val issueType: String?,
                 val categories: List<String>?,
                 val state: String?)