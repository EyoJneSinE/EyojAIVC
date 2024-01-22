package com.okation.aivideocreator.data.repo

import com.okation.aivideocreator.data.remote.AIAPI
import com.okation.aivideocreator.data.remote.jobtokendb.GetVoice
import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho
import com.okation.aivideocreator.domain.repo.AIRepository
import javax.inject.Inject

class AIRepositoryImplementation @Inject constructor(private val aiApi: AIAPI): AIRepository {
    override suspend fun getJobTokenForVoice(
        postRequestVoiceWho: PostRequestVoiceWho
    ): JobTokenDB = aiApi.getJobTokenForVoice(postRequestVoiceWho)

    override suspend fun getVoicePathFromAI(jobToken: String): GetVoice = aiApi.getVoicePathFromAI(jobToken)
}