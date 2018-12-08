package com.nasgrad.utils

import com.nasgrad.api.model.IssueCategory
import com.nasgrad.api.model.IssueType
import java.util.*
import kotlin.collections.HashMap

class Helper {
    companion object {

        var issueTypes : HashMap<String, IssueType> = HashMap()

        var issueCategories: HashMap<String, IssueCategory> = HashMap()

        fun getCategoriesForType(issueType: IssueType) : MutableList<IssueCategory> {
            val categoriesToReturn : MutableList<IssueCategory> = mutableListOf()
            val issueCats = issueType.categories

            for (cat in issueCats) {
                if (issueCategories.containsKey(cat)) {
                    val temp : IssueCategory = issueCategories[cat]!!
                    categoriesToReturn.add(temp)
                }
            }
            return categoriesToReturn
        }

        const val USER_ID_KEY = "user_id"

        fun randomGUID(): String {
            return UUID.randomUUID().toString()
        }
    }
}