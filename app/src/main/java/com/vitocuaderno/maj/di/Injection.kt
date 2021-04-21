package com.vitocuaderno.maj.di

import android.content.Context
import com.vitocuaderno.maj.data.AppRoomDatabase
import com.vitocuaderno.maj.data.repository.HomeContentRepository
import com.vitocuaderno.maj.data.repository.HomeContentRepositoryImpl

class Injection {
    companion object {
        fun provideAppDatabase(context: Context) : AppRoomDatabase {
            return AppRoomDatabase.INSTANCE!!
        }

        fun provideHomeContentRepository(context: Context) : HomeContentRepository {
            return HomeContentRepositoryImpl.getInstance(provideAppDatabase(context).homeContentDao())
        }
    }
}