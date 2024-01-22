package com.okation.aivideocreator.presentation.home.model

import android.content.Context
import com.okation.aivideocreator.R
import javax.inject.Inject

class VoiceWhoModelProvider @Inject constructor() {

    fun getVoiceWhoList(context: Context): List<VoiceWho> {
        val voiceWhoList = mutableListOf<VoiceWho>()
        val elon = VoiceWho(R.drawable.elon_mask_pic,
            context.getString(R.string.elon), context.getString(R.string.elon_token))
        val donald = VoiceWho(R.drawable.donald_trump_pic,
            context.getString(R.string.donald), context.getString(R.string.donald_token))
        val johnny = VoiceWho(R.drawable.johnny_depp_pic,
            context.getString(R.string.johnny), context.getString(R.string.johnny_token))
        val obama = VoiceWho(R.drawable.obama_pic,
            context.getString(R.string.obama), context.getString(R.string.obama_token))
        val tylor = VoiceWho(R.drawable.taylor_swift_pic,
            context.getString(R.string.tylor_swift), context.getString(R.string.tylor_swift_token))
        val benedict = VoiceWho(R.drawable.elon_mask_pic,
            context.getString(R.string.benedict_cumberbatch),
            context.getString(R.string.benedict_cumberbatch_token))
        val will = VoiceWho(R.drawable.elon_mask_pic,
            context.getString(R.string.will_smith), context.getString(R.string.will_smith_token))
        val johnHurt = VoiceWho(R.drawable.elon_mask_pic,
            context.getString(R.string.john_hurt), context.getString(R.string.john_hurt_token))
        val robertDownerJr = VoiceWho(R.drawable.elon_mask_pic,
            context.getString(R.string.robert_downer_jr),
            context.getString(R.string.robert_downer_jr_token))
        val robertDeNiro = VoiceWho(R.drawable.elon_mask_pic,
            context.getString(R.string.robert_deniro),
            context.getString(R.string.robert_deniro_token))
        voiceWhoList.add(elon)
        voiceWhoList.add(donald)
        voiceWhoList.add(johnny)
        voiceWhoList.add(obama)
        voiceWhoList.add(tylor)
        voiceWhoList.add(benedict)
        voiceWhoList.add(will)
        voiceWhoList.add(johnHurt)
        voiceWhoList.add(robertDownerJr)
        voiceWhoList.add(robertDeNiro)
        return voiceWhoList
    }
}