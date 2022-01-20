package com.lorrainelanting.maj

import android.app.Application
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lorrainelanting.maj.data.AppRoomDatabase
import com.lorrainelanting.maj.data.local.SharedPrefs
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.orders.OrdersRepository
import com.lorrainelanting.maj.data.repository.product.ProductRepository
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.di.Injection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MajApplication : Application() {
    private var remoteVersion: Int = 0
    private var localVersion: Int = 0
    private val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var localDatabase: AppRoomDatabase
    lateinit var sharedPrefs: SharedPrefs

    private val remoteDatabase by lazy { Firebase.firestore }
    lateinit var productRepository: ProductRepository
    lateinit var ordersRepository: OrdersRepository

    override fun onCreate() {
        super.onCreate()
        localDatabase = AppRoomDatabase.getDatabase(this, applicationScope)
        sharedPrefs = Injection.provideSharedPrefs(this)
        localVersion = sharedPrefs.getProductsVersion()
        productRepository = Injection.provideProductRepository(this)
        ordersRepository = Injection.provideOrdersRepository(this)

        Log.d("MajApplication", localDatabase.toString())

        compareVersions()


        val order = hashMapOf(
            "quantity" to 2,
            "delivery_option" to Constants.OPTION_DELIVER,
            "status" to Constants.OPTION_DELIVER,
            "delivery_date" to "30 May 2021",
            "order_date" to "26 May 2021",
            "product_id" to "1XmZqHO3L3Nx7Yalyjax",
            "product_image_url" to "https://images-na.ssl-images-amazon.com/images/I/51FN8q2j5KL._AC_UL600_SR600,600_.jpg",
            "product_name" to "Sprite 1.5L 12's",
            "product_price" to 658,
            "delivery_address" to "001 Test St., Sabang, Lipa City",
            "customer_name" to "Juan Dela Cruz",
            "customer_contact_number" to "09123456789"
        )

//        remoteDatabase.collection("orders").add(order).addOnSuccessListener { documentReference ->
//            documentReference.id
//        }
//        .addOnFailureListener { e ->
//            Log.w("Firestore failed", "Error adding document", e)
//        }
    }

    private fun compareVersions() {
        remoteDatabase.collection("logs").orderBy("timestamp", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    remoteVersion = document.id.toInt()
                    if (localVersion < remoteVersion) {
                        Log.d("Firestore Success(LOGS)", "${document.id} => ${document.data}")
                        fetchRemoteProducts()
                        break
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore Failed(LOGS)", "Error getting documents.", exception)
            }

    }

    fun fetchRemoteProducts() {
        val data = mutableListOf<Product>()
        remoteDatabase.collection("products").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("Firestore Success", "${document.id} => ${document.data}")
                Log.d("Products Data", "${document.data}")
                val product: Product = Product.newInstance(
                    id = document.id,
                    name = document.data["name"] as String,
                    imgUrl = document.data["image_url"] as String,
                    description = "",
                    price = document.data["price"].toString().toDouble()
                )
                data.add(product) // Fetch remote products
            }
            syncLocalProducts(data)
        }
            .addOnFailureListener { exception ->
                Log.w("Firestore Failed", "Error getting documents.", exception)
            }
    }

    private fun syncLocalProducts(remoteProducts: MutableList<Product>) {
        productRepository.deleteAll() // Clear local products
        productRepository.insertAll(remoteProducts) // Save remote products
        // Update local version
        sharedPrefs.setProductsVersion(remoteVersion)
        localVersion = remoteVersion
    }
}