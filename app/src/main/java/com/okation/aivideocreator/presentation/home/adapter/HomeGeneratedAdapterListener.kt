package com.okation.aivideocreator.presentation.home.adapter

import com.okation.aivideocreator.data.local.entity.VoiceWhoModel

interface HomeGeneratedAdapterListener {
    fun onItemClicked(item: VoiceWhoModel)
}