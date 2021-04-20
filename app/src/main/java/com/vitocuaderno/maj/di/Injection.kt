package com.vitocuaderno.maj.di

import android.content.Context
import com.vitocuaderno.maj.data.AppRoomDatabase
import com.vitocuaderno.maj.data.repository.HomeContentRepository
import com.vitocuaderno.maj.data.repository.HomeContentRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Injection {
    companion object {
        fun provideAppDatabase(context: Context) : AppRoomDatabase {
            val applicationScope = CoroutineScope(SupervisorJob())
            return AppRoomDatabase.getDatabase(context, applicationScope)
        }

        fun provideHomeContentRepository(context: Context) : HomeContentRepository {
            return HomeContentRepositoryImpl.getInstance(provideAppDatabase(context).homeContentDao())
        }
    }
}