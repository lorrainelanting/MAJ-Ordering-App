package com.lorrainelanting.maj

import android.app.Application
import android.util.Log
import com.lorrainelanting.maj.data.AppRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MajApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppRoomDatabase.getDatabase(this, applicationScope) }

    override fun onCreate() {
        super.onCreate()
        Log.d("MajApplication", database.toString())
    }
}