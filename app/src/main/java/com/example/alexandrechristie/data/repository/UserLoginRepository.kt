package com.example.alexandrechristie.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.alexandrechristie.AppConfig
import com.example.alexandrechristie.data.entity.UserLogin

class UserLoginRepository(application: Application) {
    val db = (application as AppConfig).database

    fun getUserLogin(): LiveData<List<UserLogin>> {
        val userLogin=db.userLoginDao.getUserLogin()
        return userLogin
    }
    suspend fun insertUserLogin(userLogin: UserLogin){
        db.userLoginDao.insertUserLogin(userLogin)
    }
    fun deleteAllUserLogin(){
        db.userLoginDao.deleteAllUserLogin()
    }
}