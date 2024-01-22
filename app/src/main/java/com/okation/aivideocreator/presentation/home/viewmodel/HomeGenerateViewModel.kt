package com.okation.aivideocreator.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.data.local.dao.VoiceWhoDao
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho
import com.okation.aivideocreator.domain.ai_usecase.GetJobTokenUseCase
import com.okation.aivideocreator.presentation.home.state.JobTokenState
import com.okation.aivideocreator.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGenerateViewModel @Inject constructor(
    private val getJobTokenUseCase: GetJobTokenUseCase,
    private val voiceWhoDao: VoiceWhoDao
): ViewModel() {

    private val _stateJobToken = MutableStateFlow<JobTokenState>(JobTokenState())
    val stateJobToken : StateFlow<JobTokenState> = _stateJobToken

    private val _voiceWhoList = MutableStateFlow<List<VoiceWhoModel>>(emptyList())
    val voiceWhoList: StateFlow<List<VoiceWhoModel>>
        get() = _voiceWhoList

    private var jobToken : Job? = null

    init {
        getVoiceWhoList()
    }

    fun getJobTokenWithPostRequest(postRequestVoiceWho: PostRequestVoiceWho) {
        jobToken?.cancel()
        jobToken = getJobTokenUseCase.executeGetJobTokenFromAPI(postRequestVoiceWho).onEach {
            when (it) {
                is Resource.Success -> {
                    _stateJobToken.value = JobTokenState(jobTokenDB = it.data)
                }

                is Resource.Error -> {
                    _stateJobToken.value = JobTokenState(error = it.message ?: "")
                }

                is Resource.Loading -> {
                    _stateJobToken.value = JobTokenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun insertVoiceWho(voiceWhoModel: VoiceWhoModel) {
        viewModelScope.launch {
            voiceWhoDao.insertVoiceWho(voiceWhoModel)
        }
    }

    private fun getVoiceWhoList() {
        viewModelScope.launch {
            val voiceWhoList = voiceWhoDao.getAllVoices().firstOrNull() ?: emptyList()
            _voiceWhoList.value = voiceWhoList
        }
    }
}