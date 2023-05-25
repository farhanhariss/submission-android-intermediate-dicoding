package com.example.submissionapp.data

import android.content.Context
import com.example.submissionapp.data.remote.response.LoginResult
import com.example.submissionapp.di.Injection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

class TokenPreferences(private val context: Context){

    companion object{
        private val TOKEN_KEY = "token"
    }

    val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveToken(value:LoginResult){
        sharedPref?.edit()?.putString(TOKEN_KEY, value.token)?.apply()
    }

    fun getToken(): String? {
        return sharedPref.getString(TOKEN_KEY,"")
    }

    fun deleteToken() {
        sharedPref?.edit()?.remove(TOKEN_KEY)?.apply()
    }
}