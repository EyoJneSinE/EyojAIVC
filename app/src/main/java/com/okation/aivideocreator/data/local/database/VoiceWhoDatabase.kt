package com.okation.aivideocreator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okation.aivideocreator.data.local.dao.VoiceWhoDao
import com.okation.aivideocreator.data.local.entity.VoiceWhoModel

@Database(
    entities = [VoiceWhoModel::class],
    version = 1,
    exportSchema = true
)
abstract class VoiceWhoDatabase : RoomDatabase() {
    abstract fun getVoiceWhoDao(): VoiceWhoDao
}