package com.okation.aivideocreator.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.databinding.SelectableVoiceRecyclerRowBinding
import com.okation.aivideocreator.presentation.home.model.VoiceWho
import com.okation.aivideocreator.presentation.home.viewholder.HomeChooseGenerateViewHolder

class HomeChooseGenerateAdapter(
    private var voiceWhoList: List<VoiceWho>,
    private val homeChooseGenerateAdapterListener: HomeChooseGenerateAdapterListener,
): RecyclerView.Adapter<HomeChooseGenerateViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChooseGenerateViewHolder {
        val binding = SelectableVoiceRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeChooseGenerateViewHolder(binding, homeChooseGenerateAdapterListener)
    }

    override fun getItemCount(): Int = voiceWhoList.size

    override fun onBindViewHolder(holder: HomeChooseGenerateViewHolder, position: Int) = with(holder) {
        val item = voiceWhoList[position]
        val isSelected = position == selectedPosition
        bindHomeHomeChooseGenerateRecyclerRow(item, isSelected)
    }

    fun updateSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }
}