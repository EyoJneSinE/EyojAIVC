package com.okation.aivideocreator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "voice_who_model")
data class VoiceWhoModel(
    val voiceWhoName: String,
    val voiceWhoImage: Int,
    val voiceWhoWroteText: String,
    val voiceWhoJobToken: String,
    val voiceWhoMediaPath: String,
    @PrimaryKey(autoGenerate = true)
    val voiceWhoId: Int = 0
): Serializable
