package com.andreikslpv.sets.presentation

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.presentation.BaseLoadStateAdapter
import com.andreikslpv.presentation.makeToast
import com.andreikslpv.presentation.simpleScan
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.sets.R
import com.andreikslpv.sets.databinding.FragmentSetsBinding
import com.andreikslpv.sets.domain.entities.SetFeatureModel
import com.andreikslpv.sets.presentation.recyclers.SetItemClickListener
import com.andreikslpv.sets.presentation.recyclers.SetPagingAdapter
import com.andreikslpv.sets.presentation.recyclers.itemDecoration.SpaceItemDecoration
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

        initRecipeListRecycler()
        initCollectSets()
        initSpinner()
        viewModel.setType("expansion")
    }

    private fun initSpinner() {
        viewModel.typesOfSet.observe(viewLifecycleOwner) {
            // из списка доступных типов формируем список пунктов выпадающего меню
            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item ,
                it
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item )
            binding.setsSpinner.adapter = spinnerAdapter

            binding.setsSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.typesOfSet.value?.let { types ->
                        if (p2 >= 0 && p2 < types.size) viewModel.setType(types[p2])
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun initRecipeListRecycler() {
        binding.setsRecycler.apply {
            setAdapter = SetPagingAdapter(
                object : SetItemClickListener {
                    override fun click(set: SetFeatureModel) {
//                        viewModel.setCategoryDish(id)
//                        goToCatalogFragment()
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
            setAdapter.loadStateFlow.collect {
                if (it.source.prepend is LoadState.NotLoading) {
                    binding.progressBar.show()
                }
                if (it.source.prepend is LoadState.Error) {
                    (it.source.prepend as LoadState.Error).error.message?.makeToast(
                        requireContext()
                    )
                }
                if (it.source.append is LoadState.Error) {
                    (it.source.append as LoadState.Error).error.message?.makeToast(
                        requireContext()
                    )
                }
                if (it.source.refresh is LoadState.NotLoading) {
                    binding.progressBar.hide()
                }
                if (it.source.refresh is LoadState.Error) {
                    binding.progressBar.hide()
                    (it.source.refresh as LoadState.Error).error.message?.makeToast(
                        requireContext()
                    )
                }
            }
        }
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
            viewModel.sets.collectLatest { pagedData ->
                setAdapter.submitData(pagedData)
            }
        }
    }

}