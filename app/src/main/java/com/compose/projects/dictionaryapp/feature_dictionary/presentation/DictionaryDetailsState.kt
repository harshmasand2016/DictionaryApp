package com.compose.projects.dictionaryapp.feature_dictionary.presentation

import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.WordInfo

data class DictionaryDetailsState(
    val isLoading : Boolean = false,
    val wordInfo: List<WordInfo> = emptyList(),
    val error: String = ""
)
