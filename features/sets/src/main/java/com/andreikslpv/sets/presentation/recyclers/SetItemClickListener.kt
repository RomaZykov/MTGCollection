package com.andreikslpv.sets.presentation.recyclers

import com.andreikslpv.sets.domain.entities.SetFeatureModel

interface SetItemClickListener {
    fun click(set: SetFeatureModel)
}