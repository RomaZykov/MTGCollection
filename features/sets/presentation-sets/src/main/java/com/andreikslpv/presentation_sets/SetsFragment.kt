package com.andreikslpv.presentation_sets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.andreikslpv.domain_sets.entities.SetEntity
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation_sets.databinding.FragmentSetsBinding
import com.andreikslpv.presentation_sets.entities.SetUiEntity
import com.andreikslpv.presentation_sets.recyclers.SetItemClickListener
import com.andreikslpv.presentation_sets.recyclers.SetsAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel by viewModels<SetsViewModel>()

    private val binding by viewBinding<FragmentSetsBinding>()

    private lateinit var setAdapter: SetsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSetsRecycler()
        initCollectSets()
        initSetsTypeSpinner()
    }

    private fun initSetsTypeSpinner() {
        (binding.setTypeText as? MaterialAutoCompleteTextView)?.apply {
            this.setOnItemClickListener { _, _, position, _ ->
                viewModel.setType(position)
            }
            viewModel.nameOfCurrentType.observe(viewLifecycleOwner) { type ->
                this.setText(type, false)
            }
            viewModel.typesOfSet.observe(viewLifecycleOwner) { types ->
                this.setSimpleItems(types.map { it.uiName }.toTypedArray())
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
            setAdapter = SetsAdapter(
                object : SetItemClickListener {
                    override fun click(set: SetEntity) = viewModel.launchCards(set)
                }
            )
            adapter = setAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            //Применяем декоратор для отступов
            addItemDecoration(decorator)
        }
    }

    private fun initCollectSets() {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sets.collectLatest { sets ->
                        setAdapter.changeItems(sets.map { SetUiEntity(it) })
                    }
                }
            }
        }
    }

}