package com.okation.aivideocreator.presentation.home.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.SelectableVoiceRecyclerRowBinding
import com.okation.aivideocreator.presentation.home.adapter.HomeChooseGenerateAdapterListener
import com.okation.aivideocreator.presentation.home.model.VoiceWho
import com.okation.aivideocreator.util.load

class HomeChooseGenerateViewHolder(
    private val binding: SelectableVoiceRecyclerRowBinding,
    private val homeChooseGenerateAdapterListener: HomeChooseGenerateAdapterListener
): RecyclerView.ViewHolder(binding.root) {
    private var isSelected = false

    fun bindHomeHomeChooseGenerateRecyclerRow(item: VoiceWho, selected: Boolean) {
        with(binding) {
            isSelected = selected
            updateView()
            selectableVoiceTextView.text = item.voiceWhoName
            selectableVoiceImageView.load(item.voiceWhoImage)
            selectableItemLayout.setOnClickListener {
                if (!isSelected) {
                    /*isSelected = !isSelected*/
                    homeChooseGenerateAdapterListener.onItemClicked(true)
                    homeChooseGenerateAdapterListener.onGenerateButtonClicked(item)
                    homeChooseGenerateAdapterListener.onItemPosition(bindingAdapterPosition)
                    isSelected = true
                    updateView()
                }
            }
        }
    }

    private fun updateView() {
        with(binding) {
            if (isSelected) {
                selectableVoiceImageView.foreground = binding.root.context.getDrawable(R.drawable.circle_background)
                isSelected = true
            } else {
                selectableVoiceImageView.foreground = null
                isSelected = false
            }
        }
    }
}
