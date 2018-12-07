package com.nasgrad.api.model

import android.support.annotation.Keep
import java.io.Serializable

@Keep
data class Issue(val id: String,
                 val ownerId: String,
                 var title: String?,
                 var description: String?,
                 var issueType: String?,
                 var categories: List<String>?,
                 var state: String?) : Serializable