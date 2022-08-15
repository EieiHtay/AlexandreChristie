package com.example.alexandrechristie

import android.app.Application
import com.example.alexandrechristie.data.database.WatchDatabase

class AppConfig : Application() {
    lateinit var database: WatchDatabase
    override fun onCreate() {
        super.onCreate()
        database = WatchDatabase.getDatabase(applicationContext)
    }
}
