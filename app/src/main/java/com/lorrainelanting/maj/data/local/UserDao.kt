package com.lorrainelanting.maj.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lorrainelanting.maj.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getList(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id LIMIT 1")
    fun getUser(id: Int): LiveData<User>

    @Query("SELECT * FROM user_table ORDER BY id ASC LIMIT 1")
    fun get(): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("DELETE FROM user_table WHERE id = :id")
    fun delete(id: Int)
}