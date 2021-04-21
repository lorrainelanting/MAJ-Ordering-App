package com.vitocuaderno.maj.data.repository

import androidx.lifecycle.LiveData
import com.vitocuaderno.maj.data.model.HomeContent

interface HomeContentRepository {
    fun getList() : LiveData<List<HomeContent>>

    fun getItem(id: Int) : LiveData<HomeContent>
}