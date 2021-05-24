package com.lorrainelanting.maj.data.repository.user

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.User

interface UserRepository {
    fun getList() : LiveData<List<User>>

    fun getUser(id: String) : LiveData<User>

    fun save(user: User)

    fun update(user: User)

    fun delete(id: String)
}