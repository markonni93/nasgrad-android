package com.nasgrad.api.model

import android.support.annotation.Keep

@Keep
data class Issue(val id: String,
                 val ownerId: String,
                 val title: String,
                 val description: String,
                 val issueTyper: String,
                 val categories: List<String>,
                 val pictures: List<String>,
                 val state: String,
                 val location: Pair<Double,Double>)