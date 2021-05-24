package com.lorrainelanting.maj.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("MAJ", Context.MODE_PRIVATE)
    }

    fun getProductsVersion(): Int {
        return sharedPreferences.getInt(KEY_PRODUCT_VERSION, 0)
    }

    fun setProductsVersion(int: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_PRODUCT_VERSION, int)
        editor.commit()
    }

    companion object {
        const val KEY_PRODUCT_VERSION = "KEY_PRODUCT_VERSION"

        private var instance: SharedPrefs? = null

        fun getInstance(context: Context): SharedPrefs {
            if (instance == null) {
                instance =
                    SharedPrefs(context)
            }
            return instance!!
        }
    }
}