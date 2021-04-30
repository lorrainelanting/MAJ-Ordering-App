package com.vitocuaderno.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitocuaderno.maj.data.model.CartContent

@Dao
interface CartContentDao {
    @Query("SELECT * FROM cart_content_table ORDER BY id ASC")
    fun getList(): LiveData<List<CartContent>>

    @Query("SELECT * FROM cart_content_table WHERE id = :id LIMIT 1")
    fun getItem(id: Int): LiveData<CartContent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: CartContent)

    @Query("DELETE FROM cart_content_table")
    suspend fun deleteAll()

    @Query("DELETE FROM cart_content_table WHERE id = :id")
    fun delete(id: Int)
}