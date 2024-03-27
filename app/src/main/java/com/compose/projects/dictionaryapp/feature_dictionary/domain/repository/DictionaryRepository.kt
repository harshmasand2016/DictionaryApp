package com.compose.projects.dictionaryapp.feature_dictionary.domain.repository

import com.compose.projects.dictionaryapp.feature_dictionary.data.remote.dto.WordInfoDto

interface DictionaryRepository {
    suspend fun getDetailsAboutWord(word: String) : List<WordInfoDto>
}