package com.nasgrad.api.model

data class IssueRequestBody(
        val categories: List<String?>?,
        val description: String?,
        val id: String?,
        val issueType: String?,
        val location: Location?,
        val ownerId: String?,
        val state: Int?,
        val title: String?
)