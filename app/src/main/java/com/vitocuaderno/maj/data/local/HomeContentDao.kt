package com.vitocuaderno.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitocuaderno.maj.data.model.HomeContent

@Dao
interface HomeContentDao {
    @Query("SELECT * FROM home_content_table ORDER BY id ASC")
    fun getList(): LiveData<List<HomeContent>>

    @Query("SELECT * FROM home_content_table WHERE id = :id LIMIT 1")
    fun getItem(id: Int): LiveData<HomeContent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: HomeContent)

    @Query("DELETE FROM home_content_table")
    suspend fun deleteAll()
}