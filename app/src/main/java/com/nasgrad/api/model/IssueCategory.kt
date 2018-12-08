package com.nasgrad.api.model

import android.support.annotation.Keep

@Keep
data class IssueCategory(val id: String, val name: String, val description: String, val email: String, val color: String)