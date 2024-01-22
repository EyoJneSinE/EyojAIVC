package com.okation.aivideocreator.presentation.home

import android.view.View
import com.okation.aivideocreator.databinding.FragmentHomeGeneratedListBinding
import com.okation.aivideocreator.presentation.home.viewmodel.HomeGenerateViewModel
import javax.inject.Inject

class UpdateHomeGeneratedListUI @Inject constructor() {

    fun updateHomeGeneratedListUI(
        binding: FragmentHomeGeneratedListBinding,
        homeGenerateViewModel: HomeGenerateViewModel
    ) {
        with(binding) {
            if (homeGenerateViewModel.voiceWhoList.value.isEmpty()) {
                homeGeneratedAddButton.visibility = View.GONE
                homeGeneratedRecyclerView.visibility = View.GONE
                homeGeneratedSettingsButton.visibility = View.VISIBLE
                homeGenerateButton.visibility = View.VISIBLE
                homeGenerateTextView.visibility = View.VISIBLE
                homeStartHereTextView.visibility = View.VISIBLE
                homeGeneratedArrowImageView.visibility = View.VISIBLE
            } else {
                homeGeneratedAddButton.visibility = View.VISIBLE
                homeGeneratedRecyclerView.visibility = View.VISIBLE
                homeGeneratedSettingsButton.visibility = View.VISIBLE
                homeGenerateButton.visibility = View.GONE
                homeGenerateTextView.visibility = View.GONE
                homeStartHereTextView.visibility = View.GONE
                homeGeneratedArrowImageView.visibility = View.GONE
            }
        }
    }
}