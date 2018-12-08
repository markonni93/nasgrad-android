package com.nasgrad.utils

import com.nasgrad.api.model.IssueCategory
import com.nasgrad.api.model.IssueType
import java.util.*
import kotlin.collections.HashMap

class Helper {
    companion object {

        var issueTypes : HashMap<String, IssueType> = HashMap()

        var issueCategories: HashMap<String, IssueCategory> = HashMap()

        const val USER_ID_KEY = "user_id"

        fun randomGUID(): String {
            return UUID.randomUUID().toString()
        }
    }
}