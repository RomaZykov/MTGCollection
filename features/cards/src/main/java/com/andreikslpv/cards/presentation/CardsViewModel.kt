package com.andreikslpv.cards.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.ChangeApiAvailabilityUseCase
import com.andreikslpv.cards.domain.usecase.GetCardsUseCase
import com.andreikslpv.cards.domain.usecase.TryToChangeCollectionStatusUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class CardsViewModel @AssistedInject constructor(
    @Assisted private val screen: CardsFragment.Screen?,
    private val getCardsUseCase: GetCardsUseCase,
    private val changeApiAvailabilityUseCase: ChangeApiAvailabilityUseCase,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
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
            .flatMapLatest {
                println("AAA flatMapLatest $it")
                getCardsUseCase.execute(it)
            }
            // кешируем прлучившийся flow, чтобы на него можно было подписаться несколько раз
            .cachedIn(viewModelScope)
    }

    fun getNameOfSet() = screen?.setName ?: ""

    fun goBack() {
        router.goBack()
    }

    fun refresh() {
        set.postValue(set.value)
    }

    fun changeApiAvailability() {
        changeApiAvailabilityUseCase.execute(false)
    }

    fun tryToChangeCollectionStatus(cardId: String): Boolean {
        return tryToChangeCollectionStatusUseCase.execute(cardId)
    }


    @AssistedFactory
    interface Factory {
        fun create(screen: CardsFragment.Screen?): CardsViewModel
    }
}