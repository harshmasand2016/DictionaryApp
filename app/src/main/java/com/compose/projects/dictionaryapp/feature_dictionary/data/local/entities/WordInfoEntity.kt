package com.compose.projects.dictionaryapp.feature_dictionary.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.compose.projects.dictionaryapp.core.util.Converters
import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.Phonetic
import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    @TypeConverters(Converters::class)
    val meanings: List<Meaning>,
    val phonetic: String,
    @TypeConverters(Converters::class)
    val phonetics: List<Phonetic>,
    val word: String,
    @PrimaryKey
    val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            phonetic = phonetic,
            phonetics = phonetics,
            word = word
        )
    }
}