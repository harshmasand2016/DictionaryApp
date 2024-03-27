package com.compose.projects.dictionaryapp.feature_dictionary.data.remote.dto

import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.Phonetic

data class PhoneticDto(
    val audio: String,
    val text: String
){
    fun toPhonetic() : Phonetic{
        return Phonetic(
            audio, text
        )
    }
}