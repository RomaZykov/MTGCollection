package com.andreikslpv.cards.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.cards.R
import com.andreikslpv.cards.databinding.FragmentCardsBinding
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.presentation.recyclers.CardItemClickListener
import com.andreikslpv.cards.presentation.recyclers.CardPagingAdapter
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.BaseScreen
import com.andreikslpv.presentation.args
import com.andreikslpv.presentation.makeToast
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.simpleScan
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CardsFragment : Fragment(R.layout.fragment_cards) {

    class Screen(
        val setCode: String,
        val setName: String,
    ) : BaseScreen

    @Inject
    lateinit var factory: CardsViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create(args())
    }

    private val binding by viewBinding<FragmentCardsBinding>()

    private lateinit var cardAdapter: CardPagingAdapter

    private val decorator = SpaceItemDecoration(
        paddingBottomInDp = 16,
        paddingRightInDp = 8,
        paddingLeftInDp = 8,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initCardsRecycler()
        initCollectCards()
    }

    private fun initToolbar() {
        if (viewModel.getNameOfSet().isNotBlank()) {
            binding.toolbar.apply {
                this.setNavigationIcon(com.andreikslpv.presentation.R.drawable.ic_arrow_back)
                this.setNavigationOnClickListener {
                    viewModel.goBack()
                }
                this.title = viewModel.getNameOfSet()
            }
        } else {
            binding.toolbar.title = getString(R.string.title_cards)
            viewModel.refresh()
        }
    }

    private fun initCardsRecycler() {
        binding.cardsRecycler.apply {
            cardAdapter = CardPagingAdapter(
                object : CardItemClickListener {
                    override fun click(card: CardFeatureModel) {
                        //viewModel.launchCards(set)
                    }
                },
                object : CardItemClickListener {
                    override fun click(card: CardFeatureModel) {
                        val result = viewModel.tryToChangeCollectionStatus(card)
//                        if (!result) Snackbar.make(
//                            binding.root, R.string.home_snackbar_text, Snackbar.LENGTH_LONG
//                        ).setAction(R.string.home_snackbar_action) { goToAuthFragment() }.show()
                    }
                }
            )
            adapter = cardAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cardAdapter.withLoadStateHeaderAndFooter(
                header = BaseLoadStateAdapter { cardAdapter.retry() },
                footer = BaseLoadStateAdapter { cardAdapter.retry() })
            //Применяем декоратор для отступов
            addItemDecoration(decorator)
        }
        initLoadStateListening()
        handleScrollingToTopWhenSearching()
    }

    private fun initLoadStateListening() {
        this.lifecycleScope.launch {
            cardAdapter.loadStateFlow.collect {
                if (it.source.prepend is LoadState.NotLoading) {
                    binding.progressBar.show()
                }
                if (it.source.prepend is LoadState.Error) {
                    catchError((it.source.prepend as LoadState.Error).error.message ?: "")
                }
                if (it.source.append is LoadState.Error) {
                    catchError((it.source.append as LoadState.Error).error.message ?: "")
                }
                if (it.source.refresh is LoadState.NotLoading) {
                    binding.progressBar.hide()
                }
                if (it.source.refresh is LoadState.Error) {
                    binding.progressBar.hide()
                    catchError((it.source.refresh as LoadState.Error).error.message ?: "")
                }
            }
        }
    }

    private fun catchError(message: String) {
        message.makeToast(requireContext())
        viewModel.changeApiAvailability()
        cardAdapter.refresh()
    }

    // Когда пользователь меняет поисковой запрос, то отслеживаем этот момент и прокручиваем в начало списка
    private fun handleScrollingToTopWhenSearching() = this.lifecycleScope.launch {
        getRefreshLoadStateFlow().simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.cardsRecycler.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadStateFlow(): Flow<LoadState> {
        return cardAdapter.loadStateFlow.map { it.refresh }
    }

    private fun initCollectCards() {
        this.lifecycleScope.launch {
            viewModel.cards.collectLatest { pagedData ->
                cardAdapter.submitData(pagedData)
            }
        }
    }


}