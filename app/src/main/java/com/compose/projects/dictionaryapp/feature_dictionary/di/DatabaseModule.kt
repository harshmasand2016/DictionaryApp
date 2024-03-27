package com.compose.projects.dictionaryapp.feature_dictionary.di

import android.content.Context
import androidx.room.Room
import com.compose.projects.dictionaryapp.core.util.Converters
import com.compose.projects.dictionaryapp.core.util.GsonParser
import com.compose.projects.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.compose.projects.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesWordInfoDatabase(@ApplicationContext appContext: Context) : WordInfoDatabase {
        return Room.databaseBuilder(
            appContext,
            WordInfoDatabase::class.java,"word_db"
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
    }


    @Provides
    fun providesWordInfoDao(wordInfoDatabase: WordInfoDatabase) : WordInfoDao = wordInfoDatabase.wordInfoDao

}