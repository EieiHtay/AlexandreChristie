package com.example.alexandrechristie.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.data.repository.WatchRepository
import kotlinx.coroutines.launch

class WatchViewModel(application: Application) : AndroidViewModel(application){
    private val repository : WatchRepository by lazy { WatchRepository(application) }

    var watchSearch = MutableLiveData<String>()

    fun getWatch(query: String):LiveData<List<Watch>>{
        return repository.getWatchList(query)
    }

    fun getWatchById(id:Int): LiveData<Watch> {
        return repository.getWatchListById(id)
    }
    fun getWatchMen(): LiveData<List<Watch>> {
        return repository.getWatchByMen()
    }
    fun getWatchWomen(): LiveData<List<Watch>> {
        return repository.getWatchByWomen()
    }
    fun getWatchPair(): LiveData<List<Watch>> {
        return repository.getWatchByPair()
    }
    fun getLatestWatch():LiveData<List<Watch>>{
        return repository.getLatestWatch()
    }
    fun insertWatchForm(watch: Watch){
        viewModelScope.launch {
            repository.insertWatch(watch)
        }
    }
}