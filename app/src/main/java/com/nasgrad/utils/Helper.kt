package com.nasgrad.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.nasgrad.api.model.IssueCategory
import com.nasgrad.api.model.IssueType
import com.nasgrad.nasGradApp.R
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap
import android.R.attr.bitmap


class Helper {
    companion object {

        var issueTypes: MutableMap<String, IssueType> = HashMap()

        var issueCategories: MutableMap<String, IssueCategory> = HashMap()

        fun getTypeName(typeId: String?) : String? {
            val issueType = issueTypes[typeId]
            return issueType?.name
        }

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

        fun getCategoryForCategoryId(catId: String) : IssueCategory? {
            return if (issueCategories.containsKey(catId)) {
                issueCategories[catId]
            } else {
                null
            }
        }

        fun getCategoryNameForCategoryId(catId: String?) : String? {
            return if (issueCategories.containsKey(catId)) {
                issueCategories[catId]!!.name
            } else {
                ""
            }
        }

        const val USER_ID_KEY = "user_id"

        fun randomGUID(): String {
            return UUID.randomUUID().toString()
        }

        fun decodePicturePreview( picturePreview: String): Bitmap? {
            val decodedString = Base64.decode(picturePreview, Base64.DEFAULT)
            if (decodedString != null) {
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            }
            return null
        }

        fun encodePicturePreview(picturePreview: Bitmap): String {
            val baos = ByteArrayOutputStream()
            picturePreview.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageBytes = baos.toByteArray()
            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }

    }
}