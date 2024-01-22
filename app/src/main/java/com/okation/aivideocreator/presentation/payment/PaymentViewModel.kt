package com.okation.aivideocreator.presentation.payment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.okation.aivideocreator.util.Constants.IS_PREMIUM
import com.okation.aivideocreator.util.Constants.PREMIUM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel : ViewModel() {

    private val _isPremium = MutableLiveData<Boolean>()
    val isPremium: LiveData<Boolean>
        get() = _isPremium

    fun setPremiumStatus(isPremium: Boolean) {
        _isPremium.value = isPremium
    }

    fun initializePremiumStatus(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREMIUM, Context.MODE_PRIVATE)
        val isPremium = sharedPreferences.getBoolean(IS_PREMIUM, false)
        _isPremium.value = isPremium
    }
}
