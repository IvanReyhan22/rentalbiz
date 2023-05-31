package com.bangkit.rentalbiz.utils

import org.json.JSONException
import org.json.JSONObject

object Helper {
    /*
    * Parse error message from api
    * */
    fun parseErrorMessage(errorResponse: String): String {
        return try {
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.optString("error", "Unknown error")
            errorMessage
        } catch (e: JSONException) {
            "Unknown error"
        }
    }
}