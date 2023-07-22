package com.andreikslpv.sets.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import com.andreikslpv.sets.domain.usecase.GetSetsByTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SetsViewModel @Inject constructor(
    private val getSetsByTypeUseCase: GetSetsByTypeUseCase,
    private val router: SetsRouter,
) : ViewModel() {

    val sets: Flow<PagingData<SetFeatureModel>>

    private val _type = MutableLiveData("")
    val type: LiveData<String> = _type

    init {
        sets = _type
            .asFlow()
            .flatMapLatest { getSetsByTypeUseCase.execute(it) }
            // кешируем прлучившийся flow, чтобы на него можно было подписаться несколько раз
            .cachedIn(viewModelScope)
    }

    fun refresh() {
        _type.postValue(_type.value)
    }

    fun setType(newType: String) {
        _type.postValue(newType)
    }

}