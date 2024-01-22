package com.okation.aivideocreator.presentation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import com.okation.aivideocreator.databinding.FragmentHomeGeneratedListBinding
import com.okation.aivideocreator.presentation.home.UpdateHomeGeneratedListUI
import com.okation.aivideocreator.presentation.home.adapter.HomeGeneratedAdapter
import com.okation.aivideocreator.presentation.home.adapter.HomeGeneratedAdapterListener
import com.okation.aivideocreator.presentation.home.viewmodel.HomeGenerateViewModel
import com.okation.aivideocreator.presentation.song.viewmodel.SongPlayingViewModel
import com.okation.aivideocreator.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeGeneratedListFragment : Fragment(), HomeGeneratedAdapterListener {

    lateinit var binding: FragmentHomeGeneratedListBinding
    private lateinit var homeGenerateViewModel: HomeGenerateViewModel
    private lateinit var songPlayingViewModel: SongPlayingViewModel
    @Inject
    lateinit var updateHomeGeneratedListUI: UpdateHomeGeneratedListUI

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : HomeGenerateViewModel by viewModels()
        val tempViewModel2 : SongPlayingViewModel by viewModels()
        homeGenerateViewModel = tempViewModel
        songPlayingViewModel = tempViewModel2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeGeneratedListBinding.inflate(layoutInflater, container, false)
        with(binding) {
            homeGeneratedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            launchAndRepeatWithViewLifecycle {
                launch {
                    updateHomeGeneratedListUI.updateHomeGeneratedListUI(binding, homeGenerateViewModel)
                    homeGenerateViewModel.voiceWhoList.collect {voiceWho->
                        updateHomeGeneratedListUI.updateHomeGeneratedListUI(binding, homeGenerateViewModel)
                        binding.homeGeneratedRecyclerView.run {
                            hasFixedSize()
                            adapter = HomeGeneratedAdapter(voiceWho, this@HomeGeneratedListFragment)
                        }
                    }
                }
            }
            homeGeneratedAddButton.setOnClickListener {
                val action = HomeGeneratedListFragmentDirections.actionHomeGeneratedListFragmentToHomeChooseGenerateTypeFragment()
                navController.navigate(action)
            }
            homeGeneratedSettingsButton.setOnClickListener {
                val action = HomeGeneratedListFragmentDirections.actionHomeGeneratedListFragmentToSettingsFragment()
                navController.navigate(action)
            }
            homeGenerateButton.setOnClickListener {
                val action = HomeGeneratedListFragmentDirections.actionHomeGeneratedListFragmentToHomeChooseGenerateTypeFragment()
                navController.navigate(action)
            }
        }
        return binding.root
    }

    override fun onItemClicked(item: VoiceWhoModel) {
        launchAndRepeatWithViewLifecycle {
            launch {
                songPlayingViewModel.getVoiceWithRequest(item.voiceWhoJobToken)
                val action = HomeGeneratedListFragmentDirections.actionHomeGeneratedListFragmentToSongPlayingFragment(item.voiceWhoImage, item.voiceWhoName, "", item.voiceWhoWroteText, "", item.voiceWhoMediaPath)
                navController.navigate(action)
            }
        }
    }
}