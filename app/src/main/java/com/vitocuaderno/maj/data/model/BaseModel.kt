package com.vitocuaderno.maj.data.model

open  class BaseModel(id: Int = -1) {
    var createdAtTimeStamp: Long = -1
    var updatedAtTimeStamp: Long = -1
    var deletedAtTimeStamp: Long = -1
}