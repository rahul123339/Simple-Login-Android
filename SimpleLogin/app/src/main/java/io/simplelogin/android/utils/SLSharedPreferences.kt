package io.simplelogin.android.utils

import android.content.Context
import android.content.SharedPreferences

class SLSharedPreferences {
    companion object {
        private const val PREFERENCE_FILE_KEY = "io.simplelogin.android"

        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        }

        //region API KEY
        private const val API_KEY = "API_KEY"

        fun getApiKey(context: Context) : String? {
            return getSharedPreferences(context).getString(API_KEY, null)
        }

        fun setApiKey(context: Context, apiKey: String) {
            with(getSharedPreferences(context).edit()) {
                putString(API_KEY, apiKey)
                commit()
            }
        }

        fun removeApiKey(context: Context) {
            with(getSharedPreferences(context).edit()) {
                remove(API_KEY)
                commit()
            }
        }
        //endregion

        //region API URL
        private const val API_URL = "API_URL"
        private const val defaultApiUrl = "https://app.simplelogin.io"

        fun getApiUrl(context: Context) : String {
            return getSharedPreferences(context).getString(API_URL, null) ?: defaultApiUrl
        }

        fun setApiUrl(context: Context, apiUrl: String) {
            with(getSharedPreferences(context).edit()) {
                putString(API_URL, apiUrl)
                commit()
            }
        }

        fun resetApiUrl(context: Context) {
            with(getSharedPreferences(context).edit()) {
                putString(API_URL, defaultApiUrl)
                commit()
            }
        }
        //endregion
    }
}