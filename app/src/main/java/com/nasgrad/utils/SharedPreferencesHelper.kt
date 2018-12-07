package com.nasgrad.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesHelper (private val context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStringValue(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue)
    }

    fun setStringValue(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}