package com.andreikslpv.presentation_sets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.andreikslpv.domain_sets.SetsRouter
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.domain_sets.entities.TypeOfSetUiEntity
import com.andreikslpv.domain_sets.usecase.GetAllTypesOfSetUseCase
import com.andreikslpv.domain_sets.usecase.GetSetsByCodeOfTypeUseCase
import com.andreikslpv.domain_sets.usecase.GetStartedTypeOfSetUseCase
import com.andreikslpv.domain_sets.usecase.GetTypeCodeByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SetsViewModel @Inject constructor(
    private val getAllTypesOfSetUseCase: GetAllTypesOfSetUseCase,
    private val getSetsByCodeOfTypeUseCase: GetSetsByCodeOfTypeUseCase,
    private val getTypeCodeByNameUseCase: GetTypeCodeByNameUseCase,
    getStartedTypeOfSetUseCase: GetStartedTypeOfSetUseCase,
    private val router: SetsRouter,
    private val coroutineContext: CoroutineContext,
) : ViewModel() {

    val sets: Flow<List<SetEntity>>

    val typesOfSet = liveData(coroutineContext) {
        getAllTypesOfSetUseCase().collect { response ->
            emit(response.map { TypeOfSetUiEntity(it) })
        }
    }

    private val _nameOfCurrentType = MutableLiveData("")
    val nameOfCurrentType: LiveData<String> = _nameOfCurrentType

    private val codeOfCurrentType: MutableLiveData<String> = MutableLiveData("")

    init {
        setType(getStartedTypeOfSetUseCase())

        sets = codeOfCurrentType
            .asFlow()
            .flatMapLatest { getSetsByCodeOfTypeUseCase(it) }
    }

    fun setType(position: Int) {
        val type = typesOfSet.value?.get(position)
        type?.let {
            codeOfCurrentType.postValue(it.code)
            _nameOfCurrentType.postValue(it.name)
        }
    }

    private fun setType(nameOfType: String) {
        _nameOfCurrentType.postValue(nameOfType)
        viewModelScope.launch(coroutineContext) {
            getTypeCodeByNameUseCase(nameOfType).collectLatest { codeOfType ->
                @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
                if (codeOfType != null) codeOfCurrentType.postValue(codeOfType!!)
            }
        }
    }

    fun launchCards(set: SetEntity) = router.launchCards(set)

}