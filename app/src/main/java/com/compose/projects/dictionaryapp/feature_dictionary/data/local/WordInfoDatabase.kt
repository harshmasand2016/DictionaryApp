package com.compose.projects.dictionaryapp.feature_dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.projects.dictionaryapp.core.util.Converters
import com.compose.projects.dictionaryapp.feature_dictionary.data.local.entities.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
@TypeConverters(value = [Converters::class])
abstract class WordInfoDatabase : RoomDatabase() {
    abstract val wordInfoDao: WordInfoDao
}