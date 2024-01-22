package com.okation.aivideocreator.presentation.home.adapter

import com.okation.aivideocreator.presentation.home.model.VoiceWho

interface HomeChooseGenerateAdapterListener {
    fun onItemClicked(item: Boolean)
    fun onGenerateButtonClicked(item: VoiceWho)
    fun onItemPosition(position: Int)
}