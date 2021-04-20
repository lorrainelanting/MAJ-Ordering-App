package com.vitocuaderno.maj.data.repository

import java.lang.Exception

interface Callback<R> {
    fun onComplete(result: R)

    fun onError(exception: Exception)
}