package com.andreikslpv.data.sets

import com.andreikslpv.data.sets.entities.SetDataModel

interface SetsApiCallback {
    fun onSuccess(items: List<SetDataModel>)
    fun onFailure()
}