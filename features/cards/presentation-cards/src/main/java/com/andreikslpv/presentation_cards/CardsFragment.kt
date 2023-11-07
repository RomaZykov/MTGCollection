package com.andreikslpv.presentation_cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.BaseScreen
import com.andreikslpv.presentation.args
import com.andreikslpv.presentation.observeStateOn
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.simpleScan
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.viewModelCreator
import com.andreikslpv.presentation.visible
import com.andreikslpv.presentation_cards.databinding.FragmentCardsBinding
import com.andreikslpv.presentation_cards.recyclers.CardPagingAdapter
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
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

    @Inject
    lateinit var glide: RequestManager

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
        val decorator = SpaceItemDecoration(
            paddingBottomInDp = 16,
            paddingRightInDp = 8,
            paddingLeftInDp = 8,
        )
        binding.cardsRecycler.apply {
            cardAdapter = CardPagingAdapter(
                object : CardItemClickListener {
                    override fun click(card: CardUiEntity) = viewModel.launchDetails(card)
                },
                object : CardItemClickListener {
                    override fun click(card: CardUiEntity) {
                        viewModel.tryToChangeCollectionStatus(card)
                    }
                },
                glide
            )
            adapter = cardAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cardAdapter.withLoadStateHeaderAndFooter(
                header = BaseLoadStateAdapter { cardAdapter.retry() },
                footer = BaseLoadStateAdapter { cardAdapter.retry() })
            addItemDecoration(decorator)
        }
        initLoadStateListening()
        if (viewModel.getNameOfSet().isBlank())
            observeAdaptersItemCount()
    }

    // Tracking the number of elements in the response and if the response is empty - showing a special view group
    private fun observeAdaptersItemCount() = this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewLifecycleOwner.lifecycleScope.launch {
                getRefreshLoadStateFlow().simpleScan(count = 2)
                    .collectLatest { (previousState, currentState) ->
                        if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                            if (cardAdapter.itemCount > 0) binding.cardEmptyView.visible(false)
                            else binding.cardEmptyView.visible(true)
                        }
                    }
            }
        }
    }

    private fun getRefreshLoadStateFlow() = cardAdapter.loadStateFlow.map { it.refresh }

    private fun initLoadStateListening() {
        cardAdapter.loadStateFlow.observeStateOn(viewLifecycleOwner) {
            if (it.mediator?.prepend is LoadState.NotLoading)
                Core.loadStateHandler.setLoadState(Response.Loading)
            if (it.mediator?.refresh is LoadState.NotLoading)
                Core.loadStateHandler.setLoadState(Response.Success(true))
            if (it.mediator?.refresh is LoadState.Error)
                Core.loadStateHandler.setLoadState(
                    Response.Failure((it.mediator?.refresh as LoadState.Error).error)
                )
        }
    }

    private fun initCollectCards() = this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.cards.collectLatest { cardAdapter.submitData(it) }
            }
        }
    }


}