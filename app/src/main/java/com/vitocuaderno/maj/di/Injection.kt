package com.vitocuaderno.maj.di

import android.content.Context
import com.vitocuaderno.maj.data.AppRoomDatabase
import com.vitocuaderno.maj.data.repository.ProductRepository
import com.vitocuaderno.maj.data.repository.ProductRepositoryImpl

class Injection {
    companion object {
        fun provideAppDatabase(context: Context) : AppRoomDatabase {
            return AppRoomDatabase.INSTANCE!!
        }

        fun provideProductRepository(context: Context) : ProductRepository {
            return ProductRepositoryImpl.getInstance(provideAppDatabase(context).productDao())
        }
    }
}