package com.okation.aivideocreator.presentation.payment

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentPaymentScreenBinding
import com.okation.aivideocreator.presentation.MainActivity
import com.okation.aivideocreator.util.Constants.IS_PREMIUM
import com.okation.aivideocreator.util.Constants.PREMIUM
import com.okation.aivideocreator.util.Constants.SMALL
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.purchaseWith
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentScreenFragment : Fragment() {

    lateinit var binding: FragmentPaymentScreenBinding
    private val navController: NavController by lazy {
        findNavController()
    }
    private lateinit var revPackage: Package
    private val viewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentScreenBinding.inflate(layoutInflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            val privacySpannableString = SpannableString(getString(R.string.privacy_policy))
            privacySpannableString.setSpan(UnderlineSpan(), 0, privacySpannableString.length, 0)
            tvPrivacy.text = privacySpannableString

            val restoreSpannableString = SpannableString(getString(R.string.restore_purchase))
            restoreSpannableString.setSpan(UnderlineSpan(), 0, restoreSpannableString.length, 0)
            tvRestore.text = restoreSpannableString

            val termsSpannableString = SpannableString(getString(R.string.terms_of_use))
            termsSpannableString.setSpan(UnderlineSpan(), 0, termsSpannableString.length, 0)
            tvTerms.text = termsSpannableString

            setButtonEnabled(false)

            paymentScreenCloseButton.setOnClickListener {
                val action = PaymentScreenFragmentDirections.actionPaymentScreenFragmentToHomeGeneratedListFragment()
                navController.navigate(action)
            }

            Purchases.sharedInstance.getOfferingsWith({
            }) { offerings ->
                offerings.current?.availablePackages?.forEach {
                    println(it)
                }
                offerings.current?.getPackage(SMALL)?.also {
                    premiumPriceTextView.text = it.product.price.formatted
                }
                Purchases.sharedInstance.getOfferingsWith({
                }) { offerings ->
                    paymentScreenLifeTimeButton.setOnClickListener {
                        paymentScreenLifeTimeButton.isChecked = true
                        paymentScreenContinueButton.run {
                            isClickable = true
                            isActivated = true
                            isEnabled = true
                        }
                        offerings.current?.getPackage(SMALL)?.also {
                            revPackage = it
                        }
                    }
                }
                paymentScreenContinueButton.setOnClickListener {
                    setButtonEnabled(false)
                    Purchases.sharedInstance.purchaseWith(
                        PurchaseParams.Builder(requireActivity(), revPackage).build(),
                        onError = { error, _ ->
                            Log.e("errorCode: ${error.code}", error.message)
                            setButtonEnabled(true)
                        },
                        onSuccess = { _, _ ->
                            viewModel.setPremiumStatus(true)
                            val sharedPreferences = requireContext().getSharedPreferences(PREMIUM, Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean(IS_PREMIUM, true)
                            editor.apply()
                            findNavController().navigate(R.id.action_paymentScreenFragment_to_homeGeneratedListFragment)
                        }
                    )
                }
            }
            tvPrivacy.setOnClickListener {
                browsePrivacy()
            }

            tvTerms.setOnClickListener {
                browseTerms()
            }
        }
    }

    private fun setButtonEnabled(enabled: Boolean) {
        binding.paymentScreenContinueButton.apply {
            isClickable = enabled
            isActivated = enabled
            isEnabled = enabled
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setFullscreenFlags()
    }

    private fun browsePrivacy() {
        navController.navigate(PaymentScreenFragmentDirections.actionPaymentScreenFragmentToPrivacyFragment())
    }

    private fun browseTerms() {
        navController.navigate(PaymentScreenFragmentDirections.actionPaymentScreenFragmentToTermsFragment())
    }
}
