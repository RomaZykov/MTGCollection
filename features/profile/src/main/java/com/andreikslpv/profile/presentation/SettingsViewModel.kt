package com.andreikslpv.profile.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    //private val profileRepository: ProfileRepository,
    private val router: ProfileRouter,
) : ViewModel() {

    fun goBack() {
        router.goBack()
    }

}