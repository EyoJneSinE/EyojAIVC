package com.okation.aivideocreator.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import com.okation.aivideocreator.databinding.HomeGeneratedListRecyclerRowBinding
import com.okation.aivideocreator.presentation.home.viewholder.HomeGeneratedViewHolder

class HomeGeneratedAdapter(
    private var voiceWhoModelList: List<VoiceWhoModel>,
    private val homeGeneratedAdapterListener: HomeGeneratedAdapterListener
): RecyclerView.Adapter<HomeGeneratedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGeneratedViewHolder {
        val binding = HomeGeneratedListRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeGeneratedViewHolder(binding, homeGeneratedAdapterListener)
    }

    override fun getItemCount(): Int = voiceWhoModelList.size

    override fun onBindViewHolder(holder: HomeGeneratedViewHolder, position: Int) = with(holder) {
        val item = voiceWhoModelList[position]
        bindHomeGeneratedRecyclerRow(item)
    }
}