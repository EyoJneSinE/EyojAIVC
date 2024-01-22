package com.okation.aivideocreator.presentation.song

import android.content.Context
import android.view.View
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentSongPlayingBinding
import com.okation.aivideocreator.presentation.song.viewmodel.SongPlayingViewModel
import com.okation.aivideocreator.util.Constants.COMPLETE_SUCCESS
import java.util.Formatter
import java.util.Locale
import javax.inject.Inject

class UpdateUI @Inject constructor() {

    suspend fun updateVisibilityUI(
        binding: FragmentSongPlayingBinding,
        songPlayingViewModel: SongPlayingViewModel
    ) {
        songPlayingViewModel.stateGetVoice.collect { state ->
            with(binding) {
                if (state.getVoiceDB?.state?.status == COMPLETE_SUCCESS) {
                    songGenerationLayout.visibility = View.GONE
                    songPlayingLayout.visibility = View.VISIBLE
                    stylePLayer.visibility = View.GONE
                } else {
                    songGenerationLayout.visibility = View.VISIBLE
                    songPlayingLayout.visibility = View.GONE
                    stylePLayer.visibility = View.GONE
                }
            }
        }
    }
    fun updateVisibilityUI(
        binding: FragmentSongPlayingBinding,
        voiceWhoMediaPath: String
    ) {
        with(binding) {
            if (voiceWhoMediaPath.isNotEmpty()) {
                songGenerationLayout.visibility = View.GONE
                songPlayingLayout.visibility = View.VISIBLE
                stylePLayer.visibility = View.GONE
            } else {
                songGenerationLayout.visibility = View.VISIBLE
                songPlayingLayout.visibility = View.GONE
                stylePLayer.visibility = View.GONE
            }
        }
    }
    fun stringForTime(timeMs: Long): String? {
        val mFormatBuilder = StringBuilder()
        val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }
}