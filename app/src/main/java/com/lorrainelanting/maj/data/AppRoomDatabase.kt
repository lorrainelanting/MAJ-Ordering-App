package com.lorrainelanting.maj.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lorrainelanting.maj.data.local.CartContentDao
import com.lorrainelanting.maj.data.local.DeliveryAddressDao
import com.lorrainelanting.maj.data.local.ProductDao
import com.lorrainelanting.maj.data.local.UserDao
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.DeliveryAddress
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Product::class, CartContent::class, User::class, DeliveryAddress::class],
    version = 1,
    exportSchema = false
)
public abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartContentDao(): CartContentDao
    abstract fun userDao(): UserDao
    abstract fun deliveryAddressDao(): DeliveryAddressDao

    companion object {
        @Volatile
        var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(
            context: Context, scope: CoroutineScope
        ): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "app_database"
                ).addCallback(AppDatabaseCallback(scope)).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.productDao())
                }
            }
        }

        suspend fun populateDatabase(dao: ProductDao) {
            // Delete all content here.
            dao.deleteAll()

            var list = mutableListOf<Product>()

            list.add(
                Product.newInstance(
                    productName = "Coke 1.5L",
                    productImgUrl = "https://www.rakhoiwholesale.com/wp-content/uploads/2017/10/coca-cola-bottle-1.5l-block-247x247.jpg",
                    productDescription = "Coke 1.5L",
                    productUnitCost = 658.00
                )
            )

            list.add(
                Product.newInstance(
                    productName = "Sprite 1.5L",
                    productImgUrl = "https://images-na.ssl-images-amazon.com/images/I/51FN8q2j5KL._AC_UL600_SR600,600_.jpg",
                    productDescription = "Sprite 1.5L",
                    productUnitCost = 658.00
                )
            )

            list.add(
                Product.newInstance(
                    productName = "Royal 1.5L",
                    productImgUrl = "https://ph-test-11.slatic.net/p/7115bea5d01a66cc06fd1c914c9ff2cf.jpg",
                    productDescription = "Royal 1.5L",
                    productUnitCost = 658.00
                )
            )

            list.add(
                Product.newInstance(
                    productName = "Coke Mismo 295ml",
                    productImgUrl = "https://lookingfourapp.s3-ap-southeast-1.amazonaws.com/d_ii/ii_10479_5af0080f4dc0545fca0f94adc39ef1d7.jpg",
                    productDescription = "Coke Mismo 295ml",
                    productUnitCost = 137.00
                )
            )

            list.add(
                Product.newInstance(
                    productName = "Sprite Mismo 295ml",
                    productImgUrl = "https://ph-test-11.slatic.net/p/8cab9dc22fa5f80220652a8aa78ade7f.jpg",
                    productDescription = "Sprite Mismo 295ml",
                    productUnitCost = 137.00
                )
            )

            list.add(
                Product.newInstance(
                    productName = "Royal Mismo 295ml",
                    productImgUrl = "https://lookingfourapp.s3-ap-southeast-1.amazonaws.com/d_ii/ii_10488_7b5ffeda48cb8f11b918a6239b0463d2.jpg",
                    productDescription = "Royal Mismo 295ml",
                    productUnitCost = 137.00
                )
            )

            list.forEach { dao.insert(it) }
        }
    }
}