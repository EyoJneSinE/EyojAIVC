package com.okation.aivideocreator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceWhoDao {

    @Insert
    suspend fun insertVoiceWho(voiceWhoModel: VoiceWhoModel)

    @Query("SELECT * FROM voice_who_model")
    fun getAllVoices(): Flow<List<VoiceWhoModel>>
}