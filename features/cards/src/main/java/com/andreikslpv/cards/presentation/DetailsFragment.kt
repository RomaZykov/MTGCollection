package com.andreikslpv.cards.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.andreikslpv.cards.R
import com.andreikslpv.cards.databinding.FragmentDetailsBinding
import com.andreikslpv.cards.domain.entities.CardFeatureModel
import com.andreikslpv.cards.presentation.utils.LangUtils
import com.andreikslpv.presentation.BaseScreen
import com.andreikslpv.presentation.args
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.viewModelCreator
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    class Screen(
        val card: CardFeatureModel,
    ) : BaseScreen

    @Inject
    lateinit var factory: DetailsViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create(args())
    }

    private val binding by viewBinding<FragmentDetailsBinding>()

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectCard()
    }

    private fun collectCard() {
        viewModel.card.observe(viewLifecycleOwner) { card ->
            if (card != null) {
                val systemLang = LangUtils.chooseLanguage(binding.root.context)
                //binding.itemTitle.text = LangUtils.getCardNameByLanguage(card, systemLang)
                binding.toolbar.title = LangUtils.getCardNameByLanguage(card, systemLang)

                glide.load(LangUtils.getCardImageByLanguage(card, systemLang))
                    .placeholder(com.andreikslpv.presentation.R.drawable.cover_small)
                    .centerCrop()
                    .into(binding.detailsPoster)

                binding.itemButtonCollection.setOnClickListener {
                    viewModel.tryToChangeCollectionStatus(card)
                }

                viewModel.collection.observe(viewLifecycleOwner) {
                    if (it.contains(card.id)) {
                        binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having)
                        binding.itemTitle.text = getString(R.string.details_text_having)
                    } else {
                        binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having_not)
                        binding.itemTitle.text = getString(R.string.details_text_having_not)
                    }
                }
            }
        }
    }

}