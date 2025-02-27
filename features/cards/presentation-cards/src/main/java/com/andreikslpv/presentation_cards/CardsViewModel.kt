package com.andreikslpv.presentation_cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain.usecase.GetCollectionUseCase
import com.andreikslpv.domain.usecase.TryToChangeCollectionStatusUseCase
import com.andreikslpv.domain_cards.entities.CardFilters
import com.andreikslpv.domain_cards.repositories.CardsRouter
import com.andreikslpv.domain_cards.usecase.GetCardsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class CardsViewModel @AssistedInject constructor(
    @Assisted private val screen: CardsFragment.Screen?,
    private val getCardsUseCase: GetCardsUseCase,
    getCollectionUseCase: GetCollectionUseCase,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val router: CardsRouter,
    private val coroutineContext: CoroutineContext,
) : ViewModel() {

    private val filters = MutableLiveData<CardFilters?>()
    private val _cards: Flow<PagingData<CardEntity>>
    val cards: Flow<PagingData<CardUiEntity>>

    init {
        if (screen != null) filters.postValue(CardFilters(codeOfSet = screen.setCode))
        else filters.postValue(null)

        _cards = filters
            .asFlow()
            .flatMapLatest { getCardsUseCase(it) }
            .cachedIn(viewModelScope)

        cards = combine(
            _cards,
            getCollectionUseCase(),
            ::merge
        )
    }

    private fun merge(
        pagingData: PagingData<CardEntity>,
        collection: List<String>,
    ): PagingData<CardUiEntity> {
        return pagingData.map { card ->
            CardUiEntity(
                card = card,
                isInCollection = collection.contains(card.id)
            )
        }
    }

    fun getNameOfSet() = screen?.setName ?: ""

    fun goBack() = router.goBack()

    fun launchDetails(card: CardUiEntity) = router.launchDetails(card)

    fun refresh() = filters.postValue(filters.value)

    fun tryToChangeCollectionStatus(card: CardUiEntity) {
        viewModelScope.launch(coroutineContext) {
            tryToChangeCollectionStatusUseCase(card as CardEntity)
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(screen: CardsFragment.Screen?): CardsViewModel
    }
}