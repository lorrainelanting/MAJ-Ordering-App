package com.lorrainelanting.maj.data.model

import androidx.room.Entity

@Entity(tableName = "user_table")
class User: BaseModel() {
    var fullName: String = ""
//    var lastName: String = ""
//    var firstName: String = ""
//    var middleInitial: String = ""
    var contactNum: String = ""
    var storeName: String = ""
}