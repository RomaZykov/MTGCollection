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
import com.andreikslpv.common.Constants
import com.andreikslpv.common.Core
import com.andreikslpv.common.Response
import com.andreikslpv.domain_sets.entities.SetModel
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.makeToast
import com.andreikslpv.presentation.observeStateOn
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.simpleScan
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation_sets.databinding.FragmentSetsBinding
import com.andreikslpv.presentation_sets.recyclers.SetItemClickListener
import com.andreikslpv.presentation_sets.recyclers.SetPagingAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel by viewModels<SetsViewModel>()

    private val binding by viewBinding<FragmentSetsBinding>()

    private lateinit var setAdapter: SetPagingAdapter

    private val decorator = SpaceItemDecoration(
        paddingBottomInDp = 16,
        paddingRightInDp = 8,
        paddingLeftInDp = 8,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSetsRecycler()
        initCollectSets()
        initSetsTypeSpinner()
    }

    private fun initSetsTypeSpinner() {
        viewModel.typesOfSet.observe(viewLifecycleOwner) { types ->
            (binding.setTypeText as? MaterialAutoCompleteTextView)?.apply {
                this.setSimpleItems(types.toTypedArray())
                this.setOnItemClickListener { parent, _, position, _ ->
                    viewModel.setType(parent.getItemAtPosition(position) as String)
                }
                viewModel.type.observe(viewLifecycleOwner) { type ->
                    this.setText(type, false)
                }
            }
        }
    }

    private fun initSetsRecycler() {
        binding.setsRecycler.apply {
            setAdapter = SetPagingAdapter(
                object : SetItemClickListener {
                    override fun click(set: SetModel) {
                        viewModel.launchCards(set)
                    }
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
        setAdapter.refresh()
    }

    // Когда пользователь меняет поисковой запрос, то отслеживаем этот момент и прокручиваем в начало списка
    private fun handleScrollingToTopWhenSearching() = this.lifecycleScope.launch {
        getRefreshLoadStateFlow().simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.setsRecycler.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadStateFlow(): Flow<LoadState> {
        return setAdapter.loadStateFlow.map { it.refresh }
    }

    private fun initCollectSets() {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sets.collectLatest { pagedData ->
                        setAdapter.submitData(pagedData)
                    }
                }
            }
        }
    }

}