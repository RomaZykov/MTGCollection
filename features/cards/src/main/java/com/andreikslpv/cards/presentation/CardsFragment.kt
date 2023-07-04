package com.andreikslpv.cards.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreikslpv.cards.R
import com.andreikslpv.cards.databinding.FragmentCardsBinding
import com.andreikslpv.presentation.viewBinding


class CardsFragment : Fragment(R.layout.fragment_cards) {

    private val viewModel by viewModels<CardsViewModel>()

    private val binding by viewBinding<FragmentCardsBinding>()

}