package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lorrainelanting.maj.data.model.CartContent

@Dao
interface CartContentDao {
    @Query("SELECT * FROM cart_content_table ORDER BY createdAtTimeStamp ASC")
    fun getLiveDataList(): LiveData<List<CartContent>>

    @Query("SELECT * FROM cart_content_table ORDER BY createdAtTimeStamp ASC")
    fun getList(): List<CartContent>

    @Query("SELECT * FROM cart_content_table WHERE id = :id LIMIT 1")
    fun getItem(id: String): LiveData<CartContent>

    @Query("SELECT * FROM cart_content_table WHERE productId = :id LIMIT 1")
    suspend fun getItemByProduct(id: String): CartContent?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartContent)

    @Update
    fun update(item: CartContent)

    @Query("DELETE FROM cart_content_table")
    suspend fun deleteAll()

    @Query("DELETE FROM cart_content_table WHERE id = :id")
    fun delete(id: String)
}