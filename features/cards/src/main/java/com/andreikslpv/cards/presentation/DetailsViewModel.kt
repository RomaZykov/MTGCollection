package com.andreikslpv.cards.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.domain.entities.AvailableCardFeatureModel
import com.andreikslpv.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.AddCardToCollectionUseCase
import com.andreikslpv.cards.domain.usecase.GetCardFromCollectionUseCase
import com.andreikslpv.cards.domain.usecase.GetCollectionUseCase
import com.andreikslpv.cards.domain.usecase.SetHistoryUseCase
import com.andreikslpv.cards.domain.usecase.TryToChangeCollectionStatusUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val screen: DetailsFragment.Screen?,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val getCollectionUseCase: GetCollectionUseCase,
    private val getCardFromCollectionUseCase: GetCardFromCollectionUseCase,
    private val addCardToCollectionUseCase: AddCardToCollectionUseCase,
    private val setHistoryUseCase: SetHistoryUseCase,
    private val router: CardsRouter,
) : ViewModel() {

    val card = MutableLiveData<com.andreikslpv.domain.entities.CardFeatureModel?>()

    val collection = liveData(Dispatchers.IO) {
        getCollectionUseCase.execute().collect { response ->
            emit(response)
        }
    }

    private var currentCard = com.andreikslpv.domain.entities.CardFeatureModel()

    val selectedAvailableItem = MutableStateFlow(mutableListOf<com.andreikslpv.domain.entities.AvailableCardFeatureModel>())

    init {
        card.postValue(screen?.card)
    }

    fun getCardFromCollection(cardId: String) = liveData(Dispatchers.IO) {
        getCardFromCollectionUseCase.execute(cardId).collect { response ->
            emit(response)
            currentCard = response
        }
    }

    fun tryToChangeCollectionStatus(card: com.andreikslpv.domain.entities.CardFeatureModel): Boolean {
        return tryToChangeCollectionStatusUseCase.execute(card)
    }

    fun tryToAddAvailableItem(newAvailableItem: com.andreikslpv.domain.entities.AvailableCardFeatureModel, rewrite: Boolean): Boolean {
        currentCard.availableCards.forEach {item->
            if (item.language == newAvailableItem.language
                && item.condition == newAvailableItem.condition
                && item.foiled == newAvailableItem.foiled) {
                return if (rewrite) {
                    item.count = newAvailableItem.count
                    addCardToCollectionUseCase.execute(currentCard)
                    true
                } else false
            }
        }
        currentCard.availableCards.add(newAvailableItem)
        addCardToCollectionUseCase.execute(currentCard)
        return true
    }

    fun removeAvailableItem(availableItem: com.andreikslpv.domain.entities.AvailableCardFeatureModel) {
        currentCard.availableCards.remove(availableItem)
        addCardToCollectionUseCase.execute(currentCard)
    }

    fun changeSelectedStatus(availableItem: com.andreikslpv.domain.entities.AvailableCardFeatureModel) {
        if (selectedAvailableItem.value.contains(availableItem))
            selectedAvailableItem.value.remove(availableItem)
        else
            selectedAvailableItem.value.add(availableItem)
    }

    fun selectAll() {
        refreshAvailableList(currentCard.availableCards)
    }

    fun unSelectAll() {
        refreshAvailableList(mutableListOf())
    }

    private fun refreshAvailableList(newList: MutableList<com.andreikslpv.domain.entities.AvailableCardFeatureModel>) {
        CoroutineScope(Dispatchers.Main).launch {
            selectedAvailableItem.emit(newList)
        }
    }

    fun removeSelectedFromAvailableList() {
        currentCard.availableCards.removeAll(selectedAvailableItem.value)
        addCardToCollectionUseCase.execute(currentCard)
    }

    fun removeAllFromAvailableList() {
        currentCard.availableCards.clear()
        addCardToCollectionUseCase.execute(currentCard)
    }

    fun setHistory(card: com.andreikslpv.domain.entities.CardFeatureModel) {
        CoroutineScope(Dispatchers.IO).launch {
            setHistoryUseCase.execute(card)
        }
    }

    fun goBack() {
        router.goBack()
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: DetailsFragment.Screen?): DetailsViewModel
    }
}