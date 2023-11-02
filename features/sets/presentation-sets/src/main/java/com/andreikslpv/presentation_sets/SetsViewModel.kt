package com.andreikslpv.presentation_sets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreikslpv.domain_sets.SetsExternalRepository
import com.andreikslpv.domain_sets.SetsRepository
import com.andreikslpv.domain_sets.SetsRouter
import com.andreikslpv.domain_sets.entities.SetModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SetsViewModel @Inject constructor(
    private val setsRepository: SetsRepository,
    setsExternalRepository: SetsExternalRepository,
    private val router: SetsRouter,
) : ViewModel() {

    val sets: Flow<PagingData<SetModel>>

    val typesOfSet = liveData {
        setsRepository.getTypesOfSet().collect { response ->
            emit(response)
        }
    }

    private val _type = MutableLiveData(setsExternalRepository.getStartedTypeOfSet())
    val type: LiveData<String> = _type

    init {
        sets = _type
            .asFlow()
            .flatMapLatest { setsRepository.getSetsByType(it) }
            // кешируем прлучившийся flow, чтобы на него можно было подписаться несколько раз
            .cachedIn(viewModelScope)
    }

    fun refresh() = _type.postValue(_type.value)

    fun setType(newType: String) = _type.postValue(newType)

    fun changeApiAvailability() = setsRepository.changeApiAvailability(false)

    fun launchCards(set: SetModel) = router.launchCards(set)

}