package com.lorrainelanting.maj.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.lorrainelanting.maj.data.AppRoomDatabase
import com.lorrainelanting.maj.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppRoomDatabase {
        return AppRoomDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }

    @Provides
    fun provideProductDao(appRoomDatabase: AppRoomDatabase): ProductDao {
        return appRoomDatabase.productDao()
    }

    @Provides
    fun provideCartContentDao(appRoomDatabase: AppRoomDatabase): CartContentDao {
        return appRoomDatabase.cartContentDao()
    }

    @Provides
    fun provideUserDao(appRoomDatabase: AppRoomDatabase): UserDao {
        return appRoomDatabase.userDao()
    }

    @Provides
    fun provideDeliveryAddressDao(appRoomDatabase: AppRoomDatabase): DeliveryAddressDao {
        return appRoomDatabase.deliveryAddressDao()
    }

    @Provides
    fun provideOrderDao(appRoomDatabase: AppRoomDatabase): OrderDao {
        return appRoomDatabase.orderDao()
    }

    @Provides
    fun provideOrderGroupDao(appRoomDatabase: AppRoomDatabase): OrderGroupDao {
        return appRoomDatabase.orderGroupDao()
    }
}