package com.vitocuaderno.maj.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

open class BaseModel(@PrimaryKey @ColumnInfo(name = "id") val id: Int = -1) {
    var createdAtTimeStamp: Long = System.currentTimeMillis() / 1000
    var updatedAtTimeStamp: Long = -1
    var deletedAtTimeStamp: Long = -1
}