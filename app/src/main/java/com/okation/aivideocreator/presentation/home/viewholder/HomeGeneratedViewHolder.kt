package com.okation.aivideocreator.presentation.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.R
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import com.okation.aivideocreator.databinding.HomeGeneratedListRecyclerRowBinding
import com.okation.aivideocreator.presentation.home.adapter.HomeGeneratedAdapterListener
import com.okation.aivideocreator.util.load

class HomeGeneratedViewHolder(
    private val binding: HomeGeneratedListRecyclerRowBinding,
    private val homeGeneratedAdapterListener: HomeGeneratedAdapterListener
    ): RecyclerView.ViewHolder(binding.root) {

    fun bindHomeGeneratedRecyclerRow(item: VoiceWhoModel) = with(binding) {
        homeGeneratedIdTextView.text = binding.root.context.getString(R.string.home_generated_list_id, item.voiceWhoId)
        homeGeneratedContentsTextView.text = item.voiceWhoWroteText
        homeGeneratedTitleTextView.text = item.voiceWhoName
        homeGeneratedImageView.load(item.voiceWhoImage)
        homeGeneratedCardView.setOnClickListener {
            homeGeneratedAdapterListener.onItemClicked(item)
        }
    }
}