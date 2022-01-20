package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lorrainelanting.maj.data.model.OrderGroup

@Dao
interface OrderGroupDao {
    @Query("SELECT * FROM order_group_table WHERE id = :id LIMIT 1")
    fun getOrderGroup(id: String): OrderGroup

    @Query("SELECT * FROM order_group_table ORDER BY createdAtTimeStamp ASC")
    fun getOrderGroups(): List<OrderGroup>

    @Query("SELECT * FROM order_group_table ORDER BY createdAtTimeStamp ASC")
    fun getOrderGroupsLiveData(): LiveData<List<OrderGroup>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(orderGroup: OrderGroup)

    @Update
    fun update(orderGroup: OrderGroup)

    @Query("DELETE FROM order_group_table WHERE id = :id")
    fun delete(id: String)
}