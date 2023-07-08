package com.andreikslpv.profile.presentation

import com.andreikslpv.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val router: ProfileRouter,
) : BaseViewModel() {


}