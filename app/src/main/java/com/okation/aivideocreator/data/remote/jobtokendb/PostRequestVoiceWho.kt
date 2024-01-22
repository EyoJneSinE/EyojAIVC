package com.okation.aivideocreator.data.remote.jobtokendb

import com.google.gson.annotations.SerializedName

data class PostRequestVoiceWho(
    @SerializedName("uuid_idempotency_token")
    val postRequestUUID: String,
    @SerializedName("tts_model_token")
    val postRequestVoiceWhoModelToken: String,
    @SerializedName("inference_text")
    val postRequestVoiceWhoWroteText: String
)
