package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lorrainelanting.maj.data.model.DeliveryAddress

@Dao
interface DeliveryAddressDao {
    @Query("SELECT * FROM delivery_address_table ORDER BY id ASC")
    fun getList(): LiveData<List<DeliveryAddress>>

    @Query("SELECT * FROM delivery_address_table WHERE id = :id LIMIT 1")
    fun getAddress(id: Int): LiveData<DeliveryAddress>

    @Query("SELECT * FROM delivery_address_table ORDER BY id ASC LIMIT 1")
    fun getDeliveryAddress(): DeliveryAddress?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(deliveryAddress: DeliveryAddress)

    @Update
    fun update(deliveryAddress: DeliveryAddress)

    @Query("DELETE FROM delivery_address_table")
    suspend fun deleteAll()

    @Query("DELETE FROM delivery_address_table WHERE id = :id")
    fun delete(id: Int)
}