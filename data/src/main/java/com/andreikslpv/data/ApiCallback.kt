package com.andreikslpv.data

interface ApiCallback {
    fun onSuccess(items: List<*>)
    fun onFailure()
}