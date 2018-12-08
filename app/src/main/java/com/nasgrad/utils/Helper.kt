package com.nasgrad.utils

import com.nasgrad.api.model.IssueCategory
import com.nasgrad.api.model.IssueType
import java.util.*
import kotlin.collections.HashMap
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


class Helper {
    companion object {

        var issueTypes : HashMap<String, IssueType> = HashMap()

        var issueCategories: HashMap<String, IssueCategory> = HashMap()

        const val USER_ID_KEY = "user_id"

        fun randomGUID(): String {
            return UUID.randomUUID().toString()
        }

        fun decodePicturePreview (picturePreview:String): Bitmap {
            val decodedString = Base64.decode(picturePreview,Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }

        fun encodePicturePreview (picturePreview: Bitmap) : String{
            val stream = ByteArrayOutputStream()
            picturePreview.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            return stream.toByteArray().toString()
        }
    }
}