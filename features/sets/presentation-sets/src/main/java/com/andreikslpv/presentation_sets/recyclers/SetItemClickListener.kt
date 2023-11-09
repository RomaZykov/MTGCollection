package com.andreikslpv.presentation_sets.recyclers

import com.andreikslpv.domain_sets.entities.SetEntity

interface SetItemClickListener {
    fun click(set: SetEntity)
}