package com.okation.aivideocreator.presentation.song.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.domain.ai_usecase.GetVoiceWithTokenUseCase
import com.okation.aivideocreator.presentation.song.state.GetVoiceState
import com.okation.aivideocreator.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SongPlayingViewModel @Inject constructor(
    private val getVoiceWithTokenUseCase: GetVoiceWithTokenUseCase
): ViewModel() {

    private val _stateGetVoice = MutableStateFlow<GetVoiceState>(GetVoiceState())
    val stateGetVoice : StateFlow<GetVoiceState> = _stateGetVoice

    private var jobVoice : Job? = null

    fun getVoiceWithRequest(jobToken: String) {
        jobVoice?.cancel()
        jobVoice = getVoiceWithTokenUseCase.executeGetJobTokenFromAPI(jobToken).onEach {
            when (it) {
                is Resource.Success -> {
                    _stateGetVoice.value = GetVoiceState(getVoiceDB = it.data)
                }

                is Resource.Error -> {
                    _stateGetVoice.value = GetVoiceState(error = it.message ?: "")
                }

                is Resource.Loading -> {
                    _stateGetVoice.value = GetVoiceState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
