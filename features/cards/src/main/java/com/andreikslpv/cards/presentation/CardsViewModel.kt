package com.andreikslpv.cards.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.ChangeApiAvailabilityUseCase
import com.andreikslpv.cards.domain.usecase.GetCardsInSetUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class CardsViewModel @AssistedInject constructor(
    @Assisted private val screen: CardsFragment.Screen?,
    private val getCardsInSetUseCase: GetCardsInSetUseCase,
    private val changeApiAvailabilityUseCase: ChangeApiAvailabilityUseCase,
    private val router: CardsRouter,
) : ViewModel() {

    val set = MutableLiveData<String?>()
    val cards: Flow<PagingData<CardFeatureModel>>

    init {
        screen?.let {
            set.postValue(screen.setCode)
        }

        cards = set
            .asFlow()
            .flatMapLatest { getCardsInSetUseCase.execute(it) }
            // кешируем прлучившийся flow, чтобы на него можно было подписаться несколько раз
            .cachedIn(viewModelScope)
    }

    fun refresh() {
        set.postValue(set.value)
    }

    fun changeApiAvailability() {
        changeApiAvailabilityUseCase.execute(false)
    }


    @AssistedFactory
    interface Factory {
        fun create(screen: CardsFragment.Screen?): CardsViewModel
    }
}