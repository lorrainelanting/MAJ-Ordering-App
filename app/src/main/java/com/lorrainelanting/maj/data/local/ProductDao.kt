package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lorrainelanting.maj.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getList(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE id = :id LIMIT 1")
    fun getItem(id: Int): LiveData<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()
}