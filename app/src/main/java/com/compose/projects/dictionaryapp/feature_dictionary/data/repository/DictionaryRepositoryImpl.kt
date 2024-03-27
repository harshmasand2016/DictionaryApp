package com.compose.projects.dictionaryapp.feature_dictionary.data.repository

import com.compose.projects.dictionaryapp.feature_dictionary.data.remote.DictionaryApiService
import com.compose.projects.dictionaryapp.feature_dictionary.data.remote.dto.WordInfoDto
import com.compose.projects.dictionaryapp.feature_dictionary.domain.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val api: DictionaryApiService) : DictionaryRepository{

    override suspend fun getDetailsAboutWord(word: String): List<WordInfoDto> {
        return api.getDetailsByWord(word)
    }

}