package com.okation.aivideocreator.data.remote

import com.okation.aivideocreator.data.remote.jobtokendb.GetVoice
import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AIAPI {

    //https://api.fakeyou.com/tts/inference
    //Header Accept, application/json
    //Header Content-Type, application/json
    //query uuid_idempotency_token needed uuid
    //query tts_model_token this one is choosen voice token
    //query inference_text send text for voice
    //get https://api.fakeyou.com/tts/job/jinf_hzsfsm8gc20aa0trhqc8qvy22wa

    @POST("inference")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getJobTokenForVoice(
        @Body postRequestVoiceWho: PostRequestVoiceWho
    ): JobTokenDB

    @GET("job/{inference_job_token}")
    suspend fun getVoicePathFromAI(
        @Path("inference_job_token") jobToken: String
    ): GetVoice
}