package com.example.alexandrechristie.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alexandrechristie.data.entity.User
import com.example.alexandrechristie.data.entity.UserLogin
import com.example.alexandrechristie.data.repository.UserLoginRepository
import com.example.alexandrechristie.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLoginViewModel (application: Application) : AndroidViewModel(application){
    private val repository : UserLoginRepository by lazy { UserLoginRepository(application) }

    var userLoginSearch = MutableLiveData<String>()

    fun getUserLogin(): LiveData<List<UserLogin>> {
        return repository.getUserLogin()
    }
    fun insertUserLogin(userLogin: UserLogin){
        viewModelScope.launch {
            repository.insertUserLogin(userLogin)
        }
    }
    fun deleteAllUserLogin(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllUserLogin()
        }
    }
}