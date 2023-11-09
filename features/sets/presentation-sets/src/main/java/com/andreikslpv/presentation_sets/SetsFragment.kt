package com.andreikslpv.presentation_sets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.observeStateOn
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.simpleScan
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation_sets.databinding.FragmentSetsBinding
import com.andreikslpv.presentation_sets.recyclers.SetItemClickListener
import com.andreikslpv.presentation_sets.recyclers.SetPagingAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel by viewModels<SetsViewModel>()

    private val binding by viewBinding<FragmentSetsBinding>()

    private lateinit var setAdapter: SetPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSetsRecycler()
        initCollectSets()
        initSetsTypeSpinner()
    }

    private fun initSetsTypeSpinner() {
        (binding.setTypeText as? MaterialAutoCompleteTextView)?.apply {
            this.setOnItemClickListener { parent, _, position, _ ->
                viewModel.setType(parent.getItemAtPosition(position) as String)
            }
            viewModel.nameOfCurrentType.observe(viewLifecycleOwner) { type ->
                this.setText(type, false)
            }
            viewModel.typesOfSet.observe(viewLifecycleOwner) { types ->
                this.setSimpleItems(types)
            }
        }
    }

    private fun initSetsRecycler() {
        val decorator = SpaceItemDecoration(
            paddingBottomInDp = 16,
            paddingRightInDp = 8,
            paddingLeftInDp = 8,
        )

        binding.setsRecycler.apply {
            setAdapter = SetPagingAdapter(
                object : SetItemClickListener {
                    override fun click(set: SetEntity) = viewModel.launchCards(set)
                }
            )
            adapter = setAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = setAdapter.withLoadStateHeaderAndFooter(
                header = BaseLoadStateAdapter { setAdapter.retry() },
                footer = BaseLoadStateAdapter { setAdapter.retry() })
            //Применяем декоратор для отступов
            addItemDecoration(decorator)
        }
        initLoadStateListening()
        handleScrollingToTopWhenSearching()
    }

    private fun initLoadStateListening() {
        setAdapter.loadStateFlow.observeStateOn(viewLifecycleOwner) {
            if (it.source.prepend is LoadState.NotLoading)
                Core.loadStateHandler.setLoadState(Response.Loading)
            if (it.source.refresh is LoadState.NotLoading)
                Core.loadStateHandler.setLoadState(Response.Success(true))
            if (it.source.refresh is LoadState.Error)
                Core.loadStateHandler.setLoadState(
                    Response.Failure((it.source.refresh as LoadState.Error).error)
                )
        }
    }

    // Когда пользователь меняет поисковой запрос, то отслеживаем этот момент и прокручиваем в начало списка
    private fun handleScrollingToTopWhenSearching() = this.lifecycleScope.launch {
        getRefreshLoadStateFlow().simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading)
                    binding.setsRecycler.scrollToPosition(0)
            }
    }

    private fun getRefreshLoadStateFlow() = setAdapter.loadStateFlow.map { it.refresh }

    private fun initCollectSets() {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sets.collectLatest { setAdapter.submitData(it) }
                }
            }
        }
    }

}