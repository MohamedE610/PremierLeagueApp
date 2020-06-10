package com.example.premierleagueapp.core.data.source.local.sharedpref

import android.content.SharedPreferences
import javax.inject.Inject


class UserPreference @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveUserName(name: String) {
        sharedPreferences.edit().putString(USER_NAME, name).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, "NONE") ?: "NONE"
    }

    companion object {
        const val USER_NAME = "user_name"
    }
}
