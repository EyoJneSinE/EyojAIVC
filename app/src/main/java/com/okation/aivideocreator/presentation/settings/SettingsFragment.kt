package com.okation.aivideocreator.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentSettingsBinding
import com.okation.aivideocreator.presentation.payment.PaymentViewModel
import com.okation.aivideocreator.util.Constants.APP_PACKAGE_NAME
import com.okation.aivideocreator.util.Constants.RECIPIENT_EMAIL
import com.okation.aivideocreator.util.Constants.SET_PACKAGE
import com.okation.aivideocreator.util.Constants.SUBJECT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    private val viewModel: PaymentViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            viewModel.isPremium.observe(viewLifecycleOwner) { isPremium ->
                premiumTextView.isVisible = isPremium
            }

            settingsBackButton.setOnClickListener { navController.popBackStack() }

            shareAppTextView.setOnClickListener { share() }

            rateUsTextView.setOnClickListener { rate() }

            contactUsTextView.setOnClickListener { sendEmail() }

            termsOfServiceTextView.setOnClickListener {
                browseTerms()
                binding.premiumTextView.isVisible = false
            }

            privacyPolicyTextView.setOnClickListener {
                browsePrivacy()
                binding.premiumTextView.isVisible = false
            }
        }
    }

    private fun browsePrivacy(){
        navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToPrivacyFragment())
    }

    private fun browseTerms(){
        navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToTermsFragment())
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$RECIPIENT_EMAIL")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT)
        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)))
        } catch (_: android.content.ActivityNotFoundException) { }
    }

    private fun rate() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                getString(R.string.play_google_com_store_apps, APP_PACKAGE_NAME))
            setPackage(SET_PACKAGE)
        }
        startActivity(intent)
    }

    private fun share() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = getString(R.string.text_plain)
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.check_out_this_app, APP_PACKAGE_NAME)
        )
        val chooser = Intent.createChooser(shareIntent, getString(R.string.share_app))
        startActivity(chooser)
    }
}
