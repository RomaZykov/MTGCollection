package com.andreikslpv.navigation.presentation

import androidx.lifecycle.liveData
import com.andreikslpv.navigation.domain.repositories.MainRepository
import com.andreikslpv.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: MainRouter,
    private val mainRepository: MainRepository,
) : BaseViewModel() {

    @ExperimentalCoroutinesApi
    fun getAuthState() = liveData(Dispatchers.IO) {
        mainRepository.getAuthState().collect { response ->
            emit(response)
        }
    }

    fun startObserveUser() {
        mainRepository.startObserveUser()
    }

}