package com.example.alexandrechristie.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.alexandrechristie.AppConfig
import com.example.alexandrechristie.data.entity.Watch

class WatchRepository(application: Application) {
    val db = (application as AppConfig).database

    fun getWatchList(query: String): LiveData<List<Watch>> {
        val watchList= db.watchDao.searchWatchListing(query)
        return watchList
    }
    fun getWatchListById(id:Int): LiveData<Watch> {
        val watch=db.watchDao.getWatchById(id)
        return watch
    }
    fun getWatchByMen():LiveData<List<Watch>>{
        val men = db.watchDao.getWatchByMen()
        return men
    }
    fun getWatchByWomen():LiveData<List<Watch>>{
        val women = db.watchDao.getWatchByWomen()
        return women
    }
    fun getWatchByPair():LiveData<List<Watch>>{
        val pair = db.watchDao.getWatchByPair()
        return pair
    }
    fun getLatestWatch():LiveData<List<Watch>>{
        val latestWatch=db.watchDao.getLatestWatch()
        return latestWatch
    }
    suspend fun insertWatch(watch: Watch){
        db.watchDao.insertWatch(watch)
    }
}