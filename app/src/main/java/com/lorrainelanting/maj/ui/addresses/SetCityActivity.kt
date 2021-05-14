package com.lorrainelanting.maj.ui.addresses

import android.content.Intent
import com.lorrainelanting.maj.R

class SetCityActivity : SetAddressesActivity() {
    override fun populate() {
        var cities = resources.getStringArray(R.array.cities)
        for (city in cities) {
            list.add(city)
        }
    }

    override fun onItemClick(city: String) {
        viewModel.repository.saveCity(city)
        val intent = Intent(this, SetBarangayActivity().javaClass)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            setResult(1)
            finish()
        }
    }
}