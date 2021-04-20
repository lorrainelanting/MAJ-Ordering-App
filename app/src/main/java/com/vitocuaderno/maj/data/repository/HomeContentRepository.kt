package com.vitocuaderno.maj.data.repository

import com.vitocuaderno.maj.data.model.HomeContent

interface HomeContentRepository {
    fun getList(callback: Callback<List<HomeContent>>)

    fun getItem(id: Int, callback: Callback<List<HomeContent>>)
}