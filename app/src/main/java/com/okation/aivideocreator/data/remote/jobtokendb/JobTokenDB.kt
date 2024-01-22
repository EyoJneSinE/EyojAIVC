package com.okation.aivideocreator.data.remote.jobtokendb

import com.google.gson.annotations.SerializedName

data class JobTokenDB(
    @SerializedName("inference_job_token")
    val inferenceJobToken: String,
    @SerializedName("inference_job_token_type")
    val inferenceJobTokenType: String,
    val success: Boolean
)