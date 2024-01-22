package com.okation.aivideocreator.data.di

import android.content.Context
import androidx.room.Room
import com.okation.aivideocreator.data.local.database.VoiceWhoDatabase
import com.okation.aivideocreator.data.remote.AIAPI
import com.okation.aivideocreator.data.repo.AIRepositoryImplementation
import com.okation.aivideocreator.domain.repo.AIRepository
import com.okation.aivideocreator.util.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideAIAPI(gson: Gson): AIAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(AIAPI::class.java)

    @Singleton
    @Provides
    fun provideAIRepository(aiApi: AIAPI) : AIRepository = AIRepositoryImplementation(aiApi)

    @Singleton
    @Provides
    fun provideVoiceWhoDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, VoiceWhoDatabase::class.java, "VoiceWhoDB").build()

    @Singleton
    @Provides
    fun provideVoiceWhoDao(
        database: VoiceWhoDatabase
    ) = database.getVoiceWhoDao()
}