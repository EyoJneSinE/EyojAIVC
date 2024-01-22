package com.okation.aivideocreator.presentation.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentOnBoardingScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingScreenFragment : Fragment() {

    lateinit var binding: FragmentOnBoardingScreenBinding
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingScreenBinding.inflate(layoutInflater, container, false)
        binding.onBoardingScreenContinueButton.setOnClickListener {
            val action = OnBoardingScreenFragmentDirections.actionOnBoardingScreenFragmentToPaymentScreenFragment()
            navController.navigate(action)
        }
        return binding.root
    }
}
