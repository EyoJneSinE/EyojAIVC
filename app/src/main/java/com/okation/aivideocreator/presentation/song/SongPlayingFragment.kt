package com.okation.aivideocreator.presentation.song

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.okation.aivideocreator.R
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho
import com.okation.aivideocreator.databinding.FragmentSongPlayingBinding
import com.okation.aivideocreator.presentation.home.viewmodel.HomeGenerateViewModel
import com.okation.aivideocreator.presentation.song.viewmodel.SongPlayingViewModel
import com.okation.aivideocreator.util.Constants.COMPLETE_SUCCESS
import com.okation.aivideocreator.util.Constants.MEDIA_BASE_URL
import com.okation.aivideocreator.util.launchAndRepeatWithViewLifecycle
import com.okation.aivideocreator.util.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SongPlayingFragment : Fragment(), Player.Listener {

    private lateinit var binding: FragmentSongPlayingBinding
    private val args: SongPlayingFragmentArgs by navArgs()
    private val songPlayingViewModel: SongPlayingViewModel by activityViewModels()
    private val homeGenerateViewModel: HomeGenerateViewModel by activityViewModels()
    private val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }
    private val navController: NavController by lazy { findNavController() }
    @Inject
    lateinit var updateUI: UpdateUI
    var isSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongPlayingBinding.inflate(layoutInflater)
        launchAndRepeatWithViewLifecycle {
            launch {
                updateUI.updateVisibilityUI(binding, args.voiceWhoMediaPath)
            }
        }
        getJobTokenData()
        getVoiceData()
        updateSeekBar()
        binding.songPlayingBackButton.setOnClickListener {
            val action = SongPlayingFragmentDirections.actionSongPlayingFragmentToHomeGeneratedListFragment()
            navController.navigate(action)
        }
        binding.songPlayingShareButton.setOnClickListener {

            /*if (isSelected) {
                binding.songPlayingShareButton.background = binding.root.context.getDrawable(R.drawable.btn_gradient_active)
                isSelected = true
            } else {
                binding.songPlayingShareButton.background = binding.root.context.getDrawable(R.drawable.selector_gradient_example)
                isSelected = false
            }*/
            /*val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = getString(R.string.audio)
            shareIntent.putExtra(Intent.EXTRA_STREAM, audioUri)
            val chooser = Intent.createChooser(shareIntent, getString(R.string.share_your_voice))
            startActivity(chooser)*/
        }
        return binding.root
    }

    private fun getVoiceData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                if (args.voiceWhoMediaPath.isNotEmpty()) {
                    val mediaPath = args.voiceWhoMediaPath
                    whichPartWorksDecide(mediaPath)
                } else {
                    songPlayingViewModel.stateGetVoice.collect { state ->
                        if (state.getVoiceDB?.state?.status == COMPLETE_SUCCESS) {
                            val mediaPath = state.getVoiceDB.state.maybe_public_bucket_wav_audio_path
                            whichPartWorksDecide(mediaPath)
                        } else {
                            homeGenerateViewModel.stateJobToken.value.jobTokenDB?.inferenceJobToken?.let { jobToken ->
                                songPlayingViewModel.getVoiceWithRequest(jobToken)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun whichPartWorksDecide(mediaPath: String) {
        with(binding) {
            val mediaItem = MediaItem.fromUri(MEDIA_BASE_URL + mediaPath)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            stylePLayer.player = exoPlayer
            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        updateSeekBar()
                    } else if (state == Player.STATE_ENDED) {
                        exoPlayer.seekTo(0)
                        exoPlayer.pause()
                        songPlayingPlayButton.setOnClickListener {
                            if (exoPlayer.isPlaying) {
                                exoPlayer.pause()
                            } else {
                                exoPlayer.play()
                            }
                        }
                    }
                }
            })
            songPlayingPosterImageView.load(args.voiceWhoPoster)
            songPlayingNameTextView.text = args.voiceWhoName
            songPlayingPlayButton.setOnClickListener {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            }
            songPlayingPlayForwardButton.setOnClickListener {
                val currentPosition = exoPlayer.currentPosition
                exoPlayer.seekTo(currentPosition + 15000)
            }
            songPlayingPlayBackButton.setOnClickListener {
                val currentPosition = exoPlayer.currentPosition
                exoPlayer.seekTo(currentPosition - 15000)
            }
            stylePLayer.visibility = View.GONE
            updateUI.updateVisibilityUI(binding, mediaPath)
        }
    }

    private fun updateSeekBar() {
        val coroutineScope  = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            val duration = exoPlayer.duration
            binding.songPlayingSeekBarSlider.max = duration.toInt()
            val voiceDuration = updateUI.stringForTime(duration)
            binding.songPlayingDurationTimeTextView.text = voiceDuration
            while (isActive) {
                val currentPosition = exoPlayer.currentPosition
                binding.songPlayingSeekBarSlider.progress = currentPosition.toInt()
                val remainingTimeFormatted = updateUI.stringForTime(currentPosition)
                binding.songPlayingCurrentTimeTextView.text = remainingTimeFormatted
                delay(100)
            }
        }
    }

    private fun getJobTokenData() {
        val uuid = args.voiceWhoUUID
        val voiceWhoToken = args.voiceWhoToken
        val wroteText = args.voiceWhoWroteText
        val voiceWhoName = args.voiceWhoName
        val voiceWhoImage = args.voiceWhoPoster
        val postRequestVoiceWho = PostRequestVoiceWho(
            uuid,
            voiceWhoToken,
            wroteText
        )
        homeGenerateViewModel.getJobTokenWithPostRequest(postRequestVoiceWho)
        launchAndRepeatWithViewLifecycle {
            launch {
                try {
                    var jobToken: JobTokenDB? = null
                    repeat(30) {
                        delay(50)
                        val newState = homeGenerateViewModel.stateJobToken.value
                        if (newState.jobTokenDB != null) {
                            jobToken = newState.jobTokenDB
                            return@repeat
                        }
                    }
                    if (jobToken != null) {
                        jobToken?.inferenceJobToken?.let { inferenceJobToken ->
                            songPlayingViewModel.getVoiceWithRequest(inferenceJobToken)
                            songPlayingViewModel.stateGetVoice.collect { state ->
                                if (state.getVoiceDB?.state?.status == "complete_success") {
                                    val voiceAudioPath = state.getVoiceDB.state.maybe_public_bucket_wav_audio_path
                                    val voiceWho = VoiceWhoModel(
                                        voiceWhoName,
                                        voiceWhoImage,
                                        wroteText,
                                        inferenceJobToken,
                                        voiceAudioPath
                                    )
                                    homeGenerateViewModel.insertVoiceWho(voiceWho)
                                }
                            }
                        }
                    } else {
                        Log.e("Error", "Job token not received within the specified time")
                    }

                } catch (e: Exception) {
                    Log.e("Error", "An error occurred", e)
                }
            }
        }
    }
}
