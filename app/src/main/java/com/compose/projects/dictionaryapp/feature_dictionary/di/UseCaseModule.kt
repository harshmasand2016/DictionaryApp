package com.compose.projects.dictionaryapp.feature_dictionary.di

import com.compose.projects.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.compose.projects.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.compose.projects.dictionaryapp.feature_dictionary.domain.repository.DictionaryRepository
import com.compose.projects.dictionaryapp.feature_dictionary.domain.usecases.GetWordsInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetWordInfoUseCase(repository: DictionaryRepository, wordInfoDao: WordInfoDao) : GetWordsInfoUseCase{
        return GetWordsInfoUseCase(repository, wordInfoDao)
    }
}