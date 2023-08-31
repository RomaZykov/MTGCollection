package com.andreikslpv.cards.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.domain.usecase.GetCollectionUseCase
import com.andreikslpv.cards.domain.usecase.TryToChangeCollectionStatusUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val screen: DetailsFragment.Screen?,
    private val tryToChangeCollectionStatusUseCase: TryToChangeCollectionStatusUseCase,
    private val getCollectionUseCase: GetCollectionUseCase,
    private val router: CardsRouter,
) : ViewModel() {

    val card = MutableLiveData<CardFeatureModel?>()

    val collection = liveData(Dispatchers.IO) {
        getCollectionUseCase.execute().collect { response ->
            emit(response)
        }
    }

    init {
        card.postValue(screen?.card)
    }

    fun tryToChangeCollectionStatus(card: CardFeatureModel): Boolean {
        return tryToChangeCollectionStatusUseCase.execute(card)
    }

    fun goBack() {
        router.goBack()
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: DetailsFragment.Screen?): DetailsViewModel
    }
}