package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lorrainelanting.maj.data.model.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM order_table ORDER BY createdAtTimeStamp ASC")
    fun getListLiveData(): LiveData<List<Order>>

    @Query("SELECT * FROM order_table ORDER BY createdAtTimeStamp ASC")
    fun getOrders(): List<Order>

    @Query("SELECT * FROM order_table WHERE id = :id LIMIT 1")
    fun getItemLiveData(id: String): LiveData<Order>

    @Query("SELECT * FROM order_table WHERE productId = :id LIMIT 1")
    fun getItemByProduct(id: String): Order?

    @Query("SELECT * FROM order_table WHERE orderGroupId = :id")
    fun getOrdersByGroupId(id: String): LiveData<List<Order>>

    @Query("SELECT COUNT(orderGroupId) FROM order_table WHERE orderGroupId = :id")
    fun getOrderSizeByGroupId(id: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(order: Order)

    @Update
    fun update(order: Order)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()

    @Query("DELETE FROM order_table WHERE id = :id")
    fun delete(id: String)
}