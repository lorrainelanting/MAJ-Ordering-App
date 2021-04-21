package com.vitocuaderno.maj.data.repository

import androidx.lifecycle.LiveData
import com.vitocuaderno.maj.data.local.HomeContentDao
import com.vitocuaderno.maj.data.model.HomeContent

class HomeContentRepositoryImpl private constructor(private val dao: HomeContentDao): HomeContentRepository {
    companion object {
        private var instance: HomeContentRepository? = null

        fun getInstance(dao: HomeContentDao) : HomeContentRepository {
            if (instance == null) {
                instance = HomeContentRepositoryImpl(dao)
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<HomeContent>> = dao.getList()

    override fun getItem(id: Int): LiveData<HomeContent> = dao.getItem(id)
}