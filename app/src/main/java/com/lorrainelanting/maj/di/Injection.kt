package com.lorrainelanting.maj.di

import android.content.Context
import com.lorrainelanting.maj.data.AppRoomDatabase
import com.lorrainelanting.maj.data.local.SharedPrefs
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.cart.CartRepositoryImpl
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepositoryImpl
import com.lorrainelanting.maj.data.repository.orders.OrdersRepository
import com.lorrainelanting.maj.data.repository.orders.OrdersRepositoryImpl
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepositoryImpl
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.data.repository.user.UserRepositoryImpl

class Injection {
    companion object {
        private fun provideAppDatabase(context: Context): AppRoomDatabase {
            return AppRoomDatabase.INSTANCE!!
        }

        fun provideProductRepository(context: Context): ProductRepository {
            return ProductRepositoryImpl.getInstance(provideAppDatabase(context).productDao())
        }

        fun provideCartRepository(context: Context): CartRepository {
            return CartRepositoryImpl.getInstance(provideAppDatabase(context).cartContentDao())
        }

        fun provideUserRepository(context: Context): UserRepository {
            return UserRepositoryImpl.getInstance(provideAppDatabase(context).userDao())
        }

        fun provideDeliveryAddressRepository(context: Context): DeliveryAddressRepository {
            return DeliveryAddressRepositoryImpl.getInstance(provideAppDatabase(context).deliveryAddressDao())
        }

        fun provideOrdersRepository(context: Context): OrdersRepository {
            return OrdersRepositoryImpl.getInstance(
                provideAppDatabase(context).orderDao(),
                provideAppDatabase(context).orderGroupDao(),
                provideAppDatabase(context).cartContentDao(),
                provideAppDatabase(context).productDao(),
                provideAppDatabase(context).userDao(),
                provideAppDatabase(context).deliveryAddressDao()
            )
        }

        fun provideSharedPrefs(context: Context): SharedPrefs {
            return SharedPrefs.getInstance(context)
        }

    }
}