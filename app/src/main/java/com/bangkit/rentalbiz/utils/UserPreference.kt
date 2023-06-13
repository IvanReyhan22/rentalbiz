package com.bangkit.rentalbiz.utils

import android.content.Context
import android.net.Uri
import com.bangkit.rentalbiz.data.UserCredentials

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USER_ID = "user_id"
        private const val NAME = "name"
        private const val TOKEN = "token"
        private const val EMAIL = "email"
        private const val IS_FIRST_TIME = "is_first_time"
        private const val TEMP_IMAGE = "temp_image"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setAuthKey(model: UserCredentials) {
        val editor = preferences.edit()
        editor.apply {
            putString(USER_ID, model.userId)
            putString(NAME, model.name)
            putString(EMAIL, model.email)
            putString(TOKEN, model.token)
            putBoolean(IS_FIRST_TIME, model.isFirstTime)
            apply()
        }
    }

    fun setFirstTime(isTrue: Boolean) {
        val editor = preferences.edit()
        editor.apply {
            putBoolean(IS_FIRST_TIME, isTrue)
            apply()
        }
    }

    fun getIsFirstTime(): Boolean {
        return preferences.getBoolean(IS_FIRST_TIME, true)
    }

    fun getAuthKey(): UserCredentials {
        val model = UserCredentials()
        model.apply {
            name = preferences.getString(NAME, "")
            userId = preferences.getString(USER_ID, "")
            email = preferences.getString(EMAIL, "")
            token = preferences.getString(TOKEN, "")
            isFirstTime = preferences.getBoolean(IS_FIRST_TIME, false)
        }
        return model
    }

    fun getAuthorizationToken(): String = preferences.getString(TOKEN, "") ?: ""

    fun deleteAuthKey() {
        val editor = preferences.edit()
        editor.apply() {
            remove(USER_ID)
            remove(NAME)
            remove(TOKEN)
            remove(EMAIL)
            apply()
        }
    }

    fun saveImage(image: Uri) {
        val data = image.toString()
        val editor = preferences.edit()
        editor.apply {
            putString(TEMP_IMAGE, data)
            apply()
        }
    }

    fun getImage(): Uri {
        val data = preferences.getString(TEMP_IMAGE, "")
        return data?.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) } ?: Uri.EMPTY
    }

    fun deleteImage() {
        val editor = preferences.edit()
        editor.apply() {
            remove(TEMP_IMAGE)
            apply()
        }
    }

}