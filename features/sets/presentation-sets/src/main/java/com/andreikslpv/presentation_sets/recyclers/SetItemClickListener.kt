package com.andreikslpv.presentation_sets.recyclers

import com.andreikslpv.domain_sets.entities.SetModel

interface SetItemClickListener {
    fun click(set: SetModel)
}