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

    override fun save(user: User) {
        val existingUser = dao.get()

        if (existingUser == null) {
            val model = User()
            model.id = 0
            model.storeName = user.storeName
            model.fullName = user.fullName
            model.contactNum = user.contactNum
            dao.insert(model)
        } else {
            existingUser.storeName = user.storeName
            existingUser.fullName = user.fullName
            existingUser.contactNum = user.contactNum
            dao.update(existingUser)
        }
    }

    override fun update(user: User) = dao.update(user)

    override fun delete(id: Int) = dao.delete(id)
}