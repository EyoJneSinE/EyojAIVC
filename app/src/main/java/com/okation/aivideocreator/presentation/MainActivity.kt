package com.okation.aivideocreator.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.ActivityMainBinding
import com.okation.aivideocreator.presentation.payment.PaymentViewModel
import com.okation.aivideocreator.util.Constants.GOOGLE_API_KEY
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: PaymentViewModel by viewModels()
    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializePremiumStatus(applicationContext)

        viewModel.isPremium.observe(this) { isPremium ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (isPremium == true && destination.id == R.id.onBoardingScreenFragment) {
                    navController.navigate(R.id.action_onBoardingScreenFragment_to_homeGeneratedListFragment)
                }
            }
        }
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder(this, GOOGLE_API_KEY).build())
    }

    fun setFullscreenFlags() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
    }
    fun changeFullScreenFlags(isFullSize : Boolean){
        if (isFullSize)   window.addFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )else window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
}

/*if (destination.id == R.id.splashScreenFragment || destination.id == R.id.onBoardingScreenFragment) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        val window: Window = window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.statusBarColor = resources.getColor(R.color.white_transparent)
                        window.navigationBarColor = resources.getColor(R.color.black)
                        window.navigationBarDividerColor = resources.getColor(R.color.white_transparent)
                    }
                }*/