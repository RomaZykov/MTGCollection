package com.andreikslpv.sets.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.sets.R
import com.andreikslpv.sets.databinding.FragmentSetsBinding


class SetsFragment : Fragment(R.layout.fragment_sets) {

    private val viewModel by viewModels<SetsViewModel>()

    private val binding by viewBinding<FragmentSetsBinding>()

}