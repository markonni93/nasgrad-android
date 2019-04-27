package com.nasgrad.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.nasgrad.api.model.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap


class Helper {
    companion object {

        var issueTypes: MutableMap<String, IssueType> = HashMap()

        var issueCategories: MutableMap<String, IssueCategory> = HashMap()

        var cityServices: MutableMap<String, CityService> = HashMap()

        var allTypes: MutableMap<String, Type> = HashMap()

        var cityServicesTypes: MutableMap<String, CityCerviceType> = HashMap()

        fun getTypeName(typeId: String?): String? {
            val issueType = issueTypes[typeId]
            return issueType?.name
        }

        fun getCategoriesForType(issueType: IssueType): MutableList<IssueCategory> {
            val categoriesToReturn: MutableList<IssueCategory> = mutableListOf()
            val issueCats = issueType.categories

            for (cat in issueCats) {
                if (issueCategories.containsKey(cat)) {
                    val temp: IssueCategory = issueCategories[cat]!!
                    categoriesToReturn.add(temp)
                }
            }
            return categoriesToReturn
        }

        fun getCategoryForCategoryId(catId: String): IssueCategory? {
            return if (issueCategories.containsKey(catId)) {
                issueCategories[catId]
            } else {
                null
            }
        }

        fun getCategoryNameForCategoryId(catId: String?): String? {
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

        fun decodePicturePreview(picturePreview: String): Bitmap? {
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

        fun getCategories(): MutableList<IssueCategory> {
            val categories = mutableListOf<IssueCategory>()

            for (category in issueCategories) {
                val temp = category.value
                categories.add(temp)
            }
            return categories
        }

        fun getIssueType(): MutableList<IssueType> {
            val types = mutableListOf<IssueType>()

            for (type in issueTypes) {
                val temp = type.value
                types.add(temp)
            }
            return types
        }

        fun getCityServiceType(): MutableList<CityCerviceType> {
            val cityServiceTypes = mutableListOf<CityCerviceType>()

            for (type in cityServicesTypes) {
                val temp = type.value
                cityServiceTypes.add(temp)
            }
            return cityServiceTypes
        }

        fun getTypes(): MutableList<Type> {
            val types = mutableListOf<Type>()

            for (type in allTypes) {
                val temp = type.value
                types.add(temp)
            }

            return types
        }
    }
}