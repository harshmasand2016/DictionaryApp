package com.compose.projects.dictionaryapp.feature_dictionary.data.remote

import com.compose.projects.dictionaryapp.feature_dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("api/v2/entries/en/{word}")
    suspend fun getDetailsByWord(@Path("word") word: String) : List<WordInfoDto>
}