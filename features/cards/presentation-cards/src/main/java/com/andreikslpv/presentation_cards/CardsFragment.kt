package com.andreikslpv.presentation_cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.common.Constants
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain.entities.CardModel
import com.andreikslpv.domain_cards.usecase.GetCollectionUseCase
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.BaseScreen
import com.andreikslpv.presentation.args
import com.andreikslpv.presentation.makeToast
import com.andreikslpv.presentation.observeStateOn
import com.andreikslpv.presentation.recyclers.CardItemClickListener
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.viewModelCreator
import com.andreikslpv.presentation_cards.databinding.FragmentCardsBinding
import com.andreikslpv.presentation_cards.recyclers.CardPagingAdapter
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
    lateinit var getCollectionUseCase: GetCollectionUseCase

    @Inject
    lateinit var glide: RequestManager

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
                    override fun click(card: CardModel) {
                        viewModel.launchDetails(card)
                    }
                },
                object : CardItemClickListener {
                    override fun click(card: CardModel) {
                        viewModel.tryToChangeCollectionStatus(card)
                    }
                },
                getCollectionUseCase,
                glide
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
    }

    private fun initLoadStateListening() {
        cardAdapter.loadStateFlow.observeStateOn(viewLifecycleOwner) {
            if (it.source.prepend is LoadState.NotLoading) {
                Core.loadStateHandler.setLoadState(Response.Loading)
            }
            if (it.source.prepend is LoadState.Error) {
                catchError((it.source.prepend as LoadState.Error).error.message ?: "")
            }
            if (it.source.append is LoadState.Error) {
                catchError((it.source.append as LoadState.Error).error.message ?: "")
            }
            if (it.source.refresh is LoadState.NotLoading) {
                Core.loadStateHandler.setLoadState(Response.Success(true))
            }
            if (it.source.refresh is LoadState.Error) {
                val error = (it.source.refresh as LoadState.Error).error
                Core.loadStateHandler.setLoadState(Response.Failure(error))
                catchError(error.message ?: Constants.UNKNOWN_ERROR)
            }
        }
    }

    private fun catchError(message: String) {
        message.makeToast(requireContext())
        viewModel.changeApiAvailability()
        cardAdapter.refresh()
    }

    private fun initCollectCards() {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.cards.collectLatest { pagedData ->
                        cardAdapter.submitData(pagedData)
                    }
                }
            }
        }
    }


}