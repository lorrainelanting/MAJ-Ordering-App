package com.lorrainelanting.maj

import android.app.Application
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lorrainelanting.maj.data.AppRoomDatabase
import com.lorrainelanting.maj.data.local.SharedPrefs
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.repository.product.ProductRepository
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

    override fun onCreate() {
        super.onCreate()
        localDatabase = AppRoomDatabase.getDatabase(this, applicationScope)
        sharedPrefs = Injection.provideSharedPrefs(this)
        localVersion = sharedPrefs.getProductsVersion()
        productRepository = Injection.provideProductRepository(this)
        Log.d("MajApplication", localDatabase.toString())

        compareVersions()
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

    private fun fetchRemoteProducts() {
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