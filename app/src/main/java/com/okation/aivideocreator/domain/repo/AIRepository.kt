package com.okation.aivideocreator.domain.repo

import com.okation.aivideocreator.data.remote.jobtokendb.GetVoice
import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho

interface AIRepository {

    suspend fun getJobTokenForVoice(postRequestVoiceWho: PostRequestVoiceWho): JobTokenDB

    suspend fun getVoicePathFromAI(jobToken: String): GetVoice
}