package com.andreikslpv.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.profile.domain.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val router: ProfileRouter,
) : ViewModel() {

    val typesOfSet = liveData {
        settingsRepository.getTypesOfSet().collect { response ->
            emit(response)
        }
    }

    fun getStartedTypeOfSet() = settingsRepository.getStartedTypeOfSet()

    fun setStartedTypeOfSet(type: String) = settingsRepository.setStartedTypeOfSet(type)

    fun goBack() = router.goBack()

}