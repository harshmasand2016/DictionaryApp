package com.compose.projects.dictionaryapp.feature_dictionary.data.remote.dto

import com.compose.projects.dictionaryapp.feature_dictionary.data.local.entities.WordInfoEntity
import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            phonetics = phonetics.map { it.toPhonetic() },
            word = word
        )
    }
}