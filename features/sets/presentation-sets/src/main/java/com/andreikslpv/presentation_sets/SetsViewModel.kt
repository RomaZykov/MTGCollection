package com.andreikslpv.presentation_sets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreikslpv.domain_sets.SetsRouter
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.domain_sets.entities.TypeOfSetEntity
import com.andreikslpv.domain_sets.usecase.GetAllTypesOfSetUseCase
import com.andreikslpv.domain_sets.usecase.GetSetsByCodeOfTypeUseCase
import com.andreikslpv.domain_sets.usecase.GetStartedTypeOfSetUseCase
import com.andreikslpv.domain_sets.usecase.GetTypeCodeByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    coroutineContext: CoroutineContext,
) : ViewModel() {

    var sets: Flow<PagingData<SetEntity>>

    val typesOfSet = liveData(coroutineContext) {
        getAllTypesOfSetUseCase().collect { response ->
            emit(response.map { convertTypeToUIModel(it) }.toTypedArray())
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
            .cachedIn(viewModelScope)
    }

    fun setType(selectedType: String) {
        val nameOfSelectedType = getNameOfTypeFromUIModel(selectedType)
        _nameOfCurrentType.postValue(nameOfSelectedType)
        CoroutineScope(Dispatchers.IO).launch {
            getTypeCodeByNameUseCase(nameOfSelectedType).collectLatest { codeOfType ->
                if (codeOfType != null) codeOfCurrentType.postValue(codeOfType)
            }
        }
    }

    fun launchCards(set: SetEntity) = router.launchCards(set)

    private fun convertTypeToUIModel(type: TypeOfSetEntity) = "${type.name} (${type.countOfSet})"

    private fun getNameOfTypeFromUIModel(uiModel: String): String {
        return if (uiModel.contains("(")) uiModel.take(uiModel.indexOf("(")).trim()
        else uiModel
    }

}