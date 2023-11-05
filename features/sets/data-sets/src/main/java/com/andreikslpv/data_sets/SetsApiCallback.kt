package com.andreikslpv.data_sets

import com.andreikslpv.domain_sets.entities.SetModel

interface SetsApiCallback {
    fun onSuccess(items: List<SetModel>)
    fun onFailure()
}