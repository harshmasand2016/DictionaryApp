package com.compose.projects.dictionaryapp.feature_dictionary.di

import com.compose.projects.dictionaryapp.feature_dictionary.data.remote.DictionaryApiService
import com.compose.projects.dictionaryapp.feature_dictionary.data.repository.DictionaryRepositoryImpl
import com.compose.projects.dictionaryapp.feature_dictionary.domain.repository.DictionaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDictionaryRepository(api: DictionaryApiService) : DictionaryRepository = DictionaryRepositoryImpl(api)
}