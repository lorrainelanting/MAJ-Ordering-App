package com.lorrainelanting.maj.ui.addresses

import android.content.Intent
import com.lorrainelanting.maj.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetBarangayActivity : SetAddressesActivity() {
    override fun populate() {
        val barangays = resources.getStringArray(R.array.barangays)
        for (barangay in barangays) {
            list.add(barangay)
        }
    }

    override fun onItemClick(item: String) {
        viewModel.saveBarangay(item)
        val intent = Intent(this, SetStreetActivity().javaClass)
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