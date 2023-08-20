package com.andreikslpv.cards.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.entities.CardsInitialData
import com.andreikslpv.cards.domain.repositories.CardsRepository
import com.andreikslpv.cards.domain.usecase.ChangeApiAvailabilityUseCase
import com.andreikslpv.cards.domain.usecase.GetCardsUseCase
import com.andreikslpv.cards.domain.usecase.TryToChangeCollectionStatusUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class CardsViewModel @AssistedInject constructor(
    @Assisted private val screen: CardsFragment.Screen?,
    private val getCardsUseCase: GetCardsUseCase,
    private val changeApiAvailabilityUseCase: ChangeApiAvailabilityUseCase,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val cardsRepository: CardsRepository,
    private val router: CardsRouter,
) : ViewModel() {

    private val initialData = MutableLiveData<CardsInitialData>()
    val cards: Flow<PagingData<CardFeatureModel>>

    init {
        if (screen != null) {
            val newInitialData = CardsInitialData(codeOfSet = screen.setCode)
            initialData.postValue(newInitialData)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                cardsRepository.getCollection().collect {
                    withContext(Dispatchers.Main) {
                        val newInitialData = CardsInitialData(ids = it)
                        initialData.postValue(newInitialData)
                    }
                }
            }
        }

        cards = initialData
            .asFlow()
            .flatMapLatest { getCardsUseCase.execute(it) }
            // кешируем прлучившийся flow, чтобы на него можно было подписаться несколько раз
            .cachedIn(viewModelScope)
    }

    fun getNameOfSet() = screen?.setName ?: ""

    fun goBack() {
        router.goBack()
    }

    fun refresh() {
        initialData.postValue(initialData.value)
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