package com.okation.aivideocreator.domain.ai_usecase

import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho
import com.okation.aivideocreator.domain.repo.AIRepository
import com.okation.aivideocreator.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetJobTokenUseCase @Inject constructor(private val aiRepository: AIRepository) {

    fun executeGetJobTokenFromAPI(
        postRequestVoiceWho: PostRequestVoiceWho
    ): Flow<Resource<JobTokenDB>> = flow {
        try {
            emit(Resource.Loading())
            val aiJobTokenFromAPI = aiRepository.getJobTokenForVoice(postRequestVoiceWho)
            if (aiJobTokenFromAPI.inferenceJobToken.isNotEmpty()) {
                emit(Resource.Success(aiJobTokenFromAPI))
            } else {
                emit(Resource.Error(message = "Job Token's Not Found"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }
}