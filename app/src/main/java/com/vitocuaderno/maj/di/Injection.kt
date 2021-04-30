package com.vitocuaderno.maj.di

import android.content.Context
import com.vitocuaderno.maj.data.AppRoomDatabase
import com.vitocuaderno.maj.data.repository.cart.CartRepository
import com.vitocuaderno.maj.data.repository.cart.CartRepositoryImpl
import com.vitocuaderno.maj.data.repository.product.ProductRepository
import com.vitocuaderno.maj.data.repository.product.ProductRepositoryImpl

class Injection {
    companion object {
        fun provideAppDatabase(context: Context) : AppRoomDatabase {
            return AppRoomDatabase.INSTANCE!!
        }

        fun provideProductRepository(context: Context) : ProductRepository {
            return ProductRepositoryImpl.getInstance(provideAppDatabase(context).productDao())
        }

        fun provideCartRepository(context: Context) : CartRepository {
            return CartRepositoryImpl.getInstance(provideAppDatabase(context).cartContentDao())
        }
    }
}