package com.okation.aivideocreator.domain.ai_usecase

import com.okation.aivideocreator.data.remote.jobtokendb.GetVoice
import com.okation.aivideocreator.domain.repo.AIRepository
import com.okation.aivideocreator.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetVoiceWithTokenUseCase @Inject constructor(private val aiRepository: AIRepository) {

    fun executeGetJobTokenFromAPI(
        jobToken: String
    ): Flow<Resource<GetVoice>> = flow {
        try {
            emit(Resource.Loading())
            val aiJobTokenFromAPI = aiRepository.getVoicePathFromAI(jobToken)
            if (aiJobTokenFromAPI.success) {
                emit(Resource.Success(aiJobTokenFromAPI))
            } else {
                emit(Resource.Error(message = "Voice Generate Not Success"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Internet connection error!!!"))
        }
    }
}