package com.example.smena

import android.app.Application
import com.example.smena.repository.AppDatabase

class App : Application() {

    var appDatabase: AppDatabase? = null

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        appDatabase = AppDatabase.getInstance(applicationContext)
    }

    fun destroyDatabase() {
        AppDatabase.destroyInstance()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}