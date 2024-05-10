package com.andreikslpv.presentation_cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.CardEntity
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain.usecase.GetCollectionUseCase
import com.andreikslpv.domain.usecase.TryToChangeCollectionStatusUseCase
import com.andreikslpv.domain_cards.repositories.CardsRouter
import com.andreikslpv.domain_cards.usecase.AddCardToCollectionUseCase
import com.andreikslpv.domain_cards.usecase.GetCardFromCollectionUseCase
import com.andreikslpv.domain_cards.usecase.SetHistoryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val screen: DetailsFragment.Screen?,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val getCollectionUseCase: GetCollectionUseCase,
    private val getCardFromCollectionUseCase: GetCardFromCollectionUseCase,
    private val addCardToCollectionUseCase: AddCardToCollectionUseCase,
    private val setHistoryUseCase: SetHistoryUseCase,
    private val router: CardsRouter,
    private val coroutineContext: CoroutineContext,
) : ViewModel() {

    val card = MutableLiveData<CardUiEntity?>()

    val collection = liveData(coroutineContext) {
        getCollectionUseCase().collect { emit(it) }
    }

    private var currentCard = CardUiEntity(null)

    val selectedAvailableItem = MutableStateFlow(mutableListOf<AvailableCardEntity>())

    init {
        card.postValue(screen?.card)
    }

    fun getCardFromCollection(cardId: String) = liveData(coroutineContext) {
        getCardFromCollectionUseCase(cardId).collect { card ->
            val cardUi = CardUiEntity(card, true)
            emit(cardUi)
            currentCard = cardUi
        }
    }

    fun tryToChangeCollectionStatus(card: CardUiEntity) {
        CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
            tryToChangeCollectionStatusUseCase(card as CardUiEntity)
        }
    }

    fun tryToAddAvailableItem(newAvailableItem: AvailableCardEntity, rewrite: Boolean): Boolean {
        currentCard.availableCards.forEach { item ->
            if (item.language == newAvailableItem.language
                && item.condition == newAvailableItem.condition
                && item.foiled == newAvailableItem.foiled
            ) {
                return if (rewrite) {
                    item.count = newAvailableItem.count
                    CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
                        addCardToCollectionUseCase(currentCard as CardEntity)
                    }
                    true
                } else false
            }
        }
        currentCard.availableCards.add(newAvailableItem)
        CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
            addCardToCollectionUseCase(currentCard as CardEntity)
        }
        return true
    }

    fun removeAvailableItem(availableItem: AvailableCardEntity) {
        currentCard.availableCards.remove(availableItem)
        CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
            addCardToCollectionUseCase(currentCard as CardEntity)
        }
    }

    fun changeSelectedStatus(availableItem: AvailableCardEntity) {
        if (selectedAvailableItem.value.contains(availableItem))
            selectedAvailableItem.value.remove(availableItem)
        else
            selectedAvailableItem.value.add(availableItem)
    }

    fun selectAll() = refreshAvailableList(currentCard.availableCards)

    fun unSelectAll() = refreshAvailableList(mutableListOf())

    private fun refreshAvailableList(newList: MutableList<AvailableCardEntity>) {
        viewModelScope.launch(coroutineContext) {
            selectedAvailableItem.emit(newList)
        }
    }

    fun removeSelectedFromAvailableList() {
        currentCard.availableCards.removeAll(selectedAvailableItem.value)
        CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
            addCardToCollectionUseCase(currentCard)
        }
    }

    fun removeAllFromAvailableList() {
        currentCard.availableCards.clear()
        CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
            addCardToCollectionUseCase(currentCard)
        }
    }

    fun setHistory(card: CardUiEntity) {
        CoroutineScope(Dispatchers.IO).launch(coroutineContext) {
            setHistoryUseCase(card as CardEntity)
        }
    }

    fun goBack() = router.goBack()

    @AssistedFactory
    interface Factory {
        fun create(screen: DetailsFragment.Screen?): DetailsViewModel
    }
}