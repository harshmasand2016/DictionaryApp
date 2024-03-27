package com.compose.projects.dictionaryapp.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.projects.dictionaryapp.feature_dictionary.data.local.entities.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(words: List<WordInfoEntity>)

    @Query("DELETE FROM WordInfoEntity WHERE word IN(:words)")
    suspend fun deleteWordInfos(words: List<String>)

    @Query("SELECT * FROM WordInfoEntity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfos(word: String) : List<WordInfoEntity>
}