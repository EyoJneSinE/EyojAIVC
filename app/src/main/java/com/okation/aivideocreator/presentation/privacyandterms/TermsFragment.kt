package com.okation.aivideocreator.presentation.privacyandterms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentTermsBinding
import com.okation.aivideocreator.util.Constants

class TermsFragment : Fragment() {

    lateinit var binding: FragmentTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsBinding.inflate(layoutInflater, container, false)
        browseTerms()
        return binding.root
    }

    private fun browseTerms() {
        val webView: WebView = binding.webView
        webView.isVisible = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(Constants.BROWSE_TERMS)
    }
}