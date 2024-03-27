package com.compose.projects.dictionaryapp.feature_dictionary.di

import com.compose.projects.dictionaryapp.core.constants.Constants
import com.compose.projects.dictionaryapp.core.util.GsonParser
import com.compose.projects.dictionaryapp.core.util.JsonParser
import com.compose.projects.dictionaryapp.feature_dictionary.data.remote.DictionaryApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideJsonParser(gson: Gson): JsonParser = GsonParser(gson)


    @Provides
    @Singleton
    fun provideDictionaryApiService(): DictionaryApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
}