package com.lorrainelanting.maj.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

open class BaseModel() {
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String = ""
    var createdAtTimeStamp: Long = System.currentTimeMillis() / 1000
    var updatedAtTimeStamp: Long = -1
    var deletedAtTimeStamp: Long = -1
}