package com.andreikslpv.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.profile.R
import com.andreikslpv.profile.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel by viewModels<SettingsViewModel>()

    private val binding by viewBinding<FragmentSettingsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.settings_title)
        binding.toolbar.setNavigationIcon(com.andreikslpv.presentation.R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener { viewModel.goBack() }
    }

}