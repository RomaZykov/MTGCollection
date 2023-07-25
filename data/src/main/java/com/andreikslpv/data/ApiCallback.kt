package com.andreikslpv.data

import com.andreikslpv.data.sets.entities.SetDataModel

interface ApiCallback {
    fun onSuccess(items: List<SetDataModel>)
    fun onFailure()
}