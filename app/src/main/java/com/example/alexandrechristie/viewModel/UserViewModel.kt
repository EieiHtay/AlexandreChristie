package com.example.alexandrechristie.viewModel

import android.app.Application
import android.security.identity.AccessControlProfileId
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alexandrechristie.data.entity.User
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.data.repository.UserRepository
import com.example.alexandrechristie.data.repository.WatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application){
    private val repository : UserRepository by lazy { UserRepository(application) }

    var userSearch = MutableLiveData<String>()

    fun getUser(): LiveData<List<User>> {
        return repository.getUser()
    }
    fun insertUserForm(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }
    fun deleteAllUser(id: Int){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllUser(id)
        }
    }

}