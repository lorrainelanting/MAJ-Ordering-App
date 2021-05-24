package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lorrainelanting.maj.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table ORDER BY createdAtTimeStamp ASC")
    fun getList(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE id = :id LIMIT 1")
    fun getItem(id: String): LiveData<Product>

    @Query("SELECT * FROM product_table WHERE id = :id LIMIT 1")
    fun get(id: String): Product?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Product>)

    @Query("DELETE FROM product_table")
    fun deleteAll()
}