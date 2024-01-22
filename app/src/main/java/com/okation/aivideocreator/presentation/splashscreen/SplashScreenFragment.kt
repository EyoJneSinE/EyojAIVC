package com.okation.aivideocreator.presentation.splashscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    lateinit var binding: FragmentSplashScreenBinding
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            splashScreenProgressBar.max = 1000
            val currentProgress = 1000
            val objectAnimator = ObjectAnimator.ofInt(splashScreenProgressBar, "progress", currentProgress)
                .setDuration(4000)
            objectAnimator.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingScreenFragment()
                    navController.navigate(action)
                }
            })
            objectAnimator.start()
        }
    }
}
