package com.andreikslpv.presentation_settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation_settings.databinding.FragmentSettingsBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel by viewModels<SettingsViewModel>()

    private val binding by viewBinding<FragmentSettingsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initStartSetsType()
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_settings)
        binding.toolbar.setNavigationIcon(com.andreikslpv.presentation.R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener { viewModel.goBack() }
    }

    private fun initStartSetsType() {
        viewModel.typesOfSet.observe(viewLifecycleOwner) {
            (binding.startSetTypeText as? MaterialAutoCompleteTextView)?.apply {
                this.setSimpleItems(it.toTypedArray())
                this.setText(viewModel.getStartedTypeOfSet(), false)
                this.setOnItemClickListener { parent, _, position, _ ->
                    viewModel.setStartedTypeOfSet(parent.getItemAtPosition(position) as String)
                }
            }
        }
    }

}