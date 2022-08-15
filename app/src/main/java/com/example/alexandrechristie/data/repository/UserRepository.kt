package com.example.alexandrechristie.data.repository

import android.app.Application
import android.provider.ContactsContract
import android.security.identity.AccessControlProfileId
import androidx.lifecycle.LiveData
import com.example.alexandrechristie.AppConfig
import com.example.alexandrechristie.data.entity.User

class UserRepository(application: Application) {
    val db = (application as AppConfig).database

    fun getUser(): LiveData<List<User>> {
        val user=db.userDao.getUser()
        return user
    }
    suspend fun insertUser(user: User){
        db.userDao.insertUser(user)
    }
    fun deleteAllUser(id: Int){
        db.userDao.deleteUser(id)
    }
}