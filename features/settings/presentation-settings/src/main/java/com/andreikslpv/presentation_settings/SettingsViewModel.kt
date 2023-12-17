package com.andreikslpv.presentation_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.domain_settings.repositories.SettingsExternalRepository
import com.andreikslpv.domain_settings.repositories.SettingsRepository
import com.andreikslpv.domain_settings.repositories.SettingsRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val settingsExternalRepository: SettingsExternalRepository,
    private val router: SettingsRouter,
) : ViewModel() {

    val typesOfSet = liveData {
        settingsExternalRepository.getTypesOfSet().collect { response ->
            emit(response)
        }
    }

    fun getStartedTypeOfSet() = settingsRepository.getStartedTypeOfSet()

    fun setStartedTypeOfSet(type: String) = settingsRepository.setStartedTypeOfSet(type)

    fun goBack() = router.goBack()

}