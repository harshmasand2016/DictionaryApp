package com.compose.projects.dictionaryapp.feature_dictionary.data.remote.dto

import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<String>,
    val definition: String,
    val synonyms: List<String>,
    val example: String?
) {
    fun toDefinition(): Definition {
        return Definition(
            antonyms = antonyms,
            definition = definition,
            synonyms = synonyms,
            example = example
        )
    }
}