package com.okation.aivideocreator.presentation.home.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.okation.aivideocreator.R
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import com.okation.aivideocreator.data.remote.jobtokendb.JobTokenDB
import com.okation.aivideocreator.data.remote.jobtokendb.PostRequestVoiceWho
import com.okation.aivideocreator.databinding.FragmentHomeChooseGenerateTypeBinding
import com.okation.aivideocreator.presentation.home.UpdateHomeGeneratedListUI
import com.okation.aivideocreator.presentation.home.adapter.HomeChooseGenerateAdapter
import com.okation.aivideocreator.presentation.home.adapter.HomeChooseGenerateAdapterListener
import com.okation.aivideocreator.presentation.home.model.VoiceWho
import com.okation.aivideocreator.presentation.home.model.VoiceWhoModelProvider
import com.okation.aivideocreator.presentation.home.viewmodel.HomeGenerateViewModel
import com.okation.aivideocreator.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class HomeChooseGenerateTypeFragment : Fragment(), HomeChooseGenerateAdapterListener {

    lateinit var binding: FragmentHomeChooseGenerateTypeBinding

    @Inject
    lateinit var voiceWhoModelProvider: VoiceWhoModelProvider
    private var voiceWhoList = mutableListOf<VoiceWho>()
    private var isSelectedVoiceWho = false
    private val viewModel: HomeGenerateViewModel by viewModels()


    private val homeChooseGenerateAdapter: HomeChooseGenerateAdapter by lazy {
        HomeChooseGenerateAdapter(voiceWhoList, this@HomeChooseGenerateTypeFragment)
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeChooseGenerateTypeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            homeSelectableVoiceRV.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
            setHomeRecyclerView()
            getVoiceWhoData()
            homeInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        val remainingCharacters = (250 - it.length)
                        textView8.text = "$remainingCharacters"
                    }

                }
            })
            homeChooseGenerateBackButton.setOnClickListener {
                val action = HomeChooseGenerateTypeFragmentDirections.actionHomeChooseGenerateTypeFragmentToHomeGeneratedListFragment()
                navController.navigate(action)
            }
        }
    }

    private fun setHomeRecyclerView() {
        binding.homeSelectableVoiceRV.run {
            hasFixedSize()
            adapter = homeChooseGenerateAdapter
        }
    }

    private fun getVoiceWhoData() {
        launchAndRepeatWithViewLifecycle {
            launch {
                val a = voiceWhoModelProvider.getVoiceWhoList(requireContext())
                voiceWhoList.addAll(a)
            }
        }
    }

    private fun navigateToHomeGeneratedListFragment(item: VoiceWho) {
        val wroteText = binding.homeInputEditText.text
        val uuid = UUID.randomUUID()
        if (wroteText.isNotEmpty() && wroteText.isNotBlank()) {
            val action = HomeChooseGenerateTypeFragmentDirections
                .actionHomeChooseGenerateTypeFragmentToSongPlayingFragment(
                    item.voiceWhoImage,
                    item.voiceWhoName,
                    uuid.toString(),
                    wroteText.toString(),
                    item.voiceWhoToken,
                    ""
                )
            navController.navigate(action)
        } else {
            Toast.makeText(requireContext(), getString(R.string.you_need_the_write_something), Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClicked(item: Boolean) {
        isSelectedVoiceWho = item
    }

    override fun onGenerateButtonClicked(item: VoiceWho) {
        binding.homeChooseGenerateButton.setOnClickListener {
            launchAndRepeatWithViewLifecycle {
                launch { navigateToHomeGeneratedListFragment(item) }
            }
        }
    }

    override fun onItemPosition(position: Int) {
        homeChooseGenerateAdapter.updateSelectedPosition(position)
    }
}
