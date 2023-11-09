package com.andreikslpv.presentation_cards

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreikslpv.domain.entities.AvailableCardEntity
import com.andreikslpv.domain.entities.CardLanguage
import com.andreikslpv.domain.entities.CardUiEntity
import com.andreikslpv.domain_cards.entities.CardCondition
import com.andreikslpv.presentation.BaseScreen
import com.andreikslpv.presentation.args
import com.andreikslpv.presentation.makeToast
import com.andreikslpv.presentation.recyclers.itemDecoration.SpaceItemDecoration
import com.andreikslpv.presentation.utils.LangUtils
import com.andreikslpv.presentation.viewBinding
import com.andreikslpv.presentation.viewModelCreator
import com.andreikslpv.presentation.visible
import com.andreikslpv.presentation_cards.databinding.FragmentDetailsBinding
import com.andreikslpv.presentation_cards.recyclers.AvailableItemClickListener
import com.andreikslpv.presentation_cards.recyclers.AvailableRecyclerAdapter
import com.bumptech.glide.RequestManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    class Screen(
        val card: CardUiEntity,
    ) : BaseScreen

    @Inject
    lateinit var factory: DetailsViewModel.Factory
    private val viewModel by viewModelCreator {
        factory.create(args())
    }

    private val binding by viewBinding<FragmentDetailsBinding>()

    @Inject
    lateinit var glide: RequestManager

    private val dialogAnimDuration = 500L
    private val dialogAnimAlfa = 1f
    private val defaultCount = "0"

    private lateinit var recyclerAdapter: AvailableRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        collectCard()
        initRecyclers()
        initSelectAllButton()
        initClearButton()
        initAddButton()
        initClearListButton()
        initShareButton()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(com.andreikslpv.presentation.R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener { viewModel.goBack() }
    }

    private fun initRecyclers() {
        binding.availableRecycler.apply {
            recyclerAdapter = AvailableRecyclerAdapter(
                object : AvailableItemClickListener {
                    override fun click(availableItem: AvailableCardEntity) {
                        showDialogEdit(availableItem)
                    }
                },
                object : AvailableItemClickListener {
                    override fun click(availableItem: AvailableCardEntity) {
                        viewModel.changeSelectedStatus(availableItem)
                    }
                },
                viewModel.selectedAvailableItem
            )
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = SpaceItemDecoration(
                paddingBottomInDp = 4,
                paddingLeftInDp = 16,
                paddingRightInDp = 16,
            )
            addItemDecoration(decorator)
        }
    }

    private fun collectCard() {
        viewModel.card.observe(viewLifecycleOwner) { card ->
            if (card != null) {
                val systemLang = LangUtils.chooseLanguage(binding.root.context)
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
                        binding.availableRecyclerContainer.visible(true)
                        binding.availableAddButton.visible(true)
                    } else {
                        binding.itemButtonCollection.setImageResource(com.andreikslpv.presentation.R.drawable.ic_having_not)
                        binding.itemTitle.text = getString(R.string.details_text_having_not)
                        binding.availableRecyclerContainer.visible(false)
                        binding.availableAddButton.visible(false)
                    }
                }

                observeCardInCollection(card.id)

                viewModel.setHistory(card)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeCardInCollection(cardId: String) {
        viewModel.getCardFromCollection(cardId).observe(viewLifecycleOwner) { response ->
            recyclerAdapter.changeItems(response.availableCards)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    private fun initSelectAllButton() {
        binding.availableSelectAllCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) viewModel.unSelectAll()
            else viewModel.selectAll()
        }
    }

    private fun initClearButton() {
        binding.availableClearButton.setOnClickListener {
            viewModel.removeSelectedFromAvailableList()
            binding.availableSelectAllCheckBox.isChecked = false
        }
    }

    private fun initAddButton() {
        binding.availableAddButton.setOnClickListener {
            showDialogAdd()
        }
    }

    private fun showDialogEdit(availableItem: AvailableCardEntity) {
        binding.availableDialog.apply {
            //Делаем видимой
            this.visible(true)
            //Анимируем появление
            animate()
                .setDuration(dialogAnimDuration)
                .alpha(dialogAnimAlfa)
                .start()

            deleteButton.visible(true)
            dialogTitle.text = getString(R.string.available_dialog_title_edit)
            actionButton.text = getString(R.string.available_dialog_action_edit)
            // устанавливаем начальные значения
            (languageText as? MaterialAutoCompleteTextView)?.setText(
                availableItem.language,
                false
            )
            languageField.isEnabled = false
            (conditionText as? MaterialAutoCompleteTextView)?.setText(
                availableItem.condition,
                false
            )
            conditionField.isEnabled = false
            (foilText as? MaterialAutoCompleteTextView)?.setText(
                if (availableItem.foiled) getString(R.string.foil_yes) else getString(R.string.foil_no),
                false
            )
            foilField.isEnabled = false
            countText.setText(availableItem.count.toString())

            actionButton.setOnClickListener {
                val newAvailableItem = getAvailableItemFromDialog()
                newAvailableItem?.let {
                    viewModel.tryToAddAvailableItem(it, true)
                    this.visible(false)
                }
            }

            deleteButton.setOnClickListener {
                viewModel.removeAvailableItem(availableItem)
                this.visible(false)
            }
        }
    }

    private fun showDialogAdd() {
        binding.availableDialog.apply {
            this.visible(true)
            animate()
                .setDuration(dialogAnimDuration)
                .alpha(dialogAnimAlfa)
                .start()

            deleteButton.visible(false)
            dialogTitle.text = getString(R.string.available_dialog_title_add)
            actionButton.text = getString(R.string.available_dialog_action_add)

            // устанавливаем начальные значения
            (languageText as? MaterialAutoCompleteTextView)?.setText(
                CardLanguage.NONE.cardLang,
                false
            )
            languageField.isEnabled = true
            (conditionText as? MaterialAutoCompleteTextView)?.setText(
                CardCondition.NONE.fullName,
                false
            )
            conditionField.isEnabled = true
            (foilText as? MaterialAutoCompleteTextView)?.setText(
                getString(R.string.foil_no),
                false
            )
            foilField.isEnabled = true
            countText.setText(defaultCount)

            actionButton.setOnClickListener {
                val newAvailableItem = getAvailableItemFromDialog()
                newAvailableItem?.let {
                    if (!viewModel.tryToAddAvailableItem(it, false)) availableIsExist(it)
                    else this.visible(false)
                }
            }
        }
    }

    private fun availableIsExist(newAvailableItem: AvailableCardEntity) {
        getString(R.string.snackbar_text).makeToast(requireContext())
        binding.availableDialog.actionButton.text = getString(R.string.snackbar_action)
        binding.availableDialog.actionButton.setOnClickListener {
            viewModel.tryToAddAvailableItem(newAvailableItem, true)
            binding.availableDialog.visible(false)
        }
    }


    private fun getAvailableItemFromDialog(): AvailableCardEntity? {
        binding.availableDialog.apply {
            return if (languageText.text.isNullOrBlank() || conditionText.text.isNullOrBlank()) {
                if (languageText.text.isNullOrBlank()) languageText.error =
                    getString(R.string.available_dialog_field_error)
                if (conditionText.text.isNullOrBlank()) conditionText.error =
                    getString(R.string.available_dialog_field_error)
                null
            } else {
                AvailableCardEntity(
                    language = languageText.text.toString(),
                    count = getCount(countText.text.toString()),
                    foiled = getFoiledStatus(foilText.text.toString()),
                    condition = conditionText.text.toString()
                )
            }
        }
    }

    private fun getCount(text: String): Int {
        return try {
            text.toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun getFoiledStatus(text: String): Boolean {
        return when (text) {
            getString(R.string.foil_yes) -> true
            else -> false
        }
    }

    private fun initClearListButton() {
        binding.toolbar.menu.findItem(R.id.favoritesClearList).setOnMenuItemClickListener {
            viewModel.removeAllFromAvailableList()
            true
        }
    }

    private fun initShareButton() {
        binding.toolbar.menu.findItem(R.id.favoritesShare).setOnMenuItemClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                ShareHelper.shareThis(
//                    requireContext(),
//                    getString(
//                        R.string.shopping_share_text,
//                        viewModel.generateTextFromShoppingList()
//                    ),
//                    resources.getString(R.string.favorites_share_title)
//                )
//            }
            true
        }
    }

}