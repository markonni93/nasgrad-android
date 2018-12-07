package com.nasgrad.utils

import java.util.*

class Helper {
    companion object {
        const val USER_ID_KEY = "user_id"

        fun randomGUID(): String {
            return UUID.randomUUID().toString()
        }
    }
}