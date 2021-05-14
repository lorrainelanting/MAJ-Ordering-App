package com.lorrainelanting.maj.data.repository.user

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.UserDao
import com.lorrainelanting.maj.data.model.User

class UserRepositoryImpl private constructor(private val dao: UserDao) : UserRepository {
    companion object {
        private var instance: UserRepository? = null

        fun getInstance(dao: UserDao): UserRepository {
            if (instance == null) {
                instance =
                    UserRepositoryImpl(dao)
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<User>> = dao.getList()

    override fun getUser(id: Int): LiveData<User> = dao.getUser(id)

    override fun save(user: User) = dao.insert(user)

    override fun update(user: User) = dao.update(user)

    override fun delete(id: Int) = dao.delete(id)
}