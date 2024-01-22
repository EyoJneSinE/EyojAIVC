package com.okation.aivideocreator.presentation.song.state

import com.okation.aivideocreator.data.remote.jobtokendb.GetVoice

data class GetVoiceState(
    val isLoading : Boolean = false,
    val getVoiceDB: GetVoice? = null,
    val error : String = "",
)
