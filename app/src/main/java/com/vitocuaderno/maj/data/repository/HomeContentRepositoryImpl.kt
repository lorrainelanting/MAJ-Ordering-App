package com.vitocuaderno.maj.data.repository

import com.vitocuaderno.maj.data.local.HomeContentDao
import com.vitocuaderno.maj.data.model.HomeContent

class HomeContentRepositoryImpl private constructor(private val dao: HomeContentDao): HomeContentRepository {
    companion object {
        private lateinit var instance: HomeContentRepository

        fun getInstance(dao: HomeContentDao) : HomeContentRepository {
            if (instance == null) {
                instance = HomeContentRepositoryImpl(dao)
            }
            return instance
        }
    }

    override fun getList(callback: Callback<List<HomeContent>>) {
        var list = mutableListOf<HomeContent>()

        list.add(HomeContent.newInstance(
            id = 1,
            productImgUrl = "https://www.rakhoiwholesale.com/wp-content/uploads/2017/10/coca-cola-bottle-1.5l-block-247x247.jpg",
            productDescription = "Coke 1.5L",
            productUnitCost = 658.00
        ))

        list.add(HomeContent.newInstance(
            id = 2,
            productImgUrl = "https://images-na.ssl-images-amazon.com/images/I/51FN8q2j5KL._AC_UL600_SR600,600_.jpg",
            productDescription = "Sprite 1.5L",
            productUnitCost = 658.00
        ))

        list.add(HomeContent.newInstance(
            id = 3,
            productImgUrl = "https://ph-test-11.slatic.net/p/7115bea5d01a66cc06fd1c914c9ff2cf.jpg",
            productDescription = "Royal 1.5L",
            productUnitCost = 658.00
        ))

        list.add(HomeContent.newInstance(
            id = 4,
            productImgUrl = "https://lookingfourapp.s3-ap-southeast-1.amazonaws.com/d_ii/ii_10479_5af0080f4dc0545fca0f94adc39ef1d7.jpg",
            productDescription = "Coke Mismo 295ml",
            productUnitCost = 137.00
        ))

        list.add(HomeContent.newInstance(
            id = 5,
            productImgUrl = "https://ph-test-11.slatic.net/p/8cab9dc22fa5f80220652a8aa78ade7f.jpg",
            productDescription = "Sprite Mismo 295ml",
            productUnitCost = 137.00
        ))

        list.add(HomeContent.newInstance(
            id = 6,
            productImgUrl = "https://lookingfourapp.s3-ap-southeast-1.amazonaws.com/d_ii/ii_10488_7b5ffeda48cb8f11b918a6239b0463d2.jpg",
            productDescription = "Royal Mismo 295ml",
            productUnitCost = 137.00
        ))

        callback.onComplete(list)
    }

    override fun getItem(id: Int, callback: Callback<List<HomeContent>>) {
    }

}