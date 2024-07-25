package com.angelemv.android.pruebaintercamaemv.viewmodel

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelemv.android.pruebaintercamaemv.models.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var _user: String? = null
    private var _password: String? = null
    private lateinit var database: AppDatabase

    fun setUser(user: String) {
        _user = user
    }
    fun setDatabase(db: AppDatabase) {
        database = db
    }

    fun setPassword(password: String) {
        _password = password
    }

    fun validateUser():Boolean {
        if (!_user.isNullOrEmpty() && !_password.isNullOrEmpty()) {
            return true
        } else {
            return false
        }
    }

    private fun encriptToBase64(textToEncrypt : String): String {
        val data = textToEncrypt.toByteArray(Charsets.UTF_8)
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }
    fun decryptFromBase64(encoded: String): String {
        val data = Base64.decode(encoded, Base64.NO_WRAP)
        return String(data, Charsets.UTF_8)
    }

}

