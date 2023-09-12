package com.andreikslpv.sets.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.makeToast
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.simpleScan
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.sets.R
import com.andreikslpv.sets.databinding.FragmentSetsBinding
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import com.andreikslpv.sets.presentation.recyclers.SetItemClickListener
import com.andreikslpv.sets.presentation.recyclers.SetPagingAdapter
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
        viewModel.typesOfSet.observe(viewLifecycleOwner) {
            (binding.setTypeText as? MaterialAutoCompleteTextView)?.apply {
                this.setSimpleItems(it.toTypedArray())
                if (viewModel.type.value.isNullOrBlank())
                    this.setText(viewModel.getStartedTypeOfSet(), false)
                this.setOnItemClickListener { parent, _, position, _ ->
                    viewModel.setType(parent.getItemAtPosition(position) as String)
                }
            }
        }
    }

    private fun initSetsRecycler() {
        binding.setsRecycler.apply {
            setAdapter = SetPagingAdapter(
                object : SetItemClickListener {
                    override fun click(set: SetFeatureModel) {
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
        this.lifecycleScope.launch {
            // Suspend the coroutine until the lifecycle is DESTROYED.
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                // Safely collect from source when the lifecycle is STARTED
                // and stop collecting when the lifecycle is STOPPED
                viewLifecycleOwner.lifecycleScope.launch {
                    setAdapter.loadStateFlow.collect {
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
            // Note: at this point, the lifecycle is DESTROYED!
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