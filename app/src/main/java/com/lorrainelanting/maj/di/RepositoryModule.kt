package com.lorrainelanting.maj.di

import com.lorrainelanting.maj.data.local.*
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun provideProductRepository(dao: ProductDao): ProductRepository {
        return ProductRepositoryImpl.getInstance(dao)
    }

    @Provides
    fun provideCartRepository(dao: CartContentDao): CartRepository {
        return CartRepositoryImpl.getInstance(dao)
    }

    @Provides
    fun provideUserRepository(dao: UserDao): UserRepository {
        return UserRepositoryImpl.getInstance(dao)
    }

    @Provides
    fun provideDeliveryAddressRepository(dao: DeliveryAddressDao): DeliveryAddressRepository {
        return DeliveryAddressRepositoryImpl.getInstance(dao)
    }

    @Provides
    fun provideOrdersRepository(
        orderDao: OrderDao,
        orderGroupDao: OrderGroupDao,
        cartContentDao: CartContentDao,
        productDao: ProductDao,
        userDao: UserDao,
        deliveryAddressDao: DeliveryAddressDao
    ): OrdersRepository {
        return OrdersRepositoryImpl.getInstance(
            orderDao,
            orderGroupDao,
            cartContentDao,
            productDao,
            userDao,
            deliveryAddressDao
        )
    }

}