package com.compose.projects.dictionaryapp.feature_dictionary.domain.usecases

import com.compose.projects.dictionaryapp.core.util.Resource
import com.compose.projects.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.compose.projects.dictionaryapp.feature_dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordsInfoUseCase @Inject constructor(
    private val repository: DictionaryRepository,
    private val dao: WordInfoDao
) {
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow { }
        }
        return flow {
            emit(Resource.Loading())
            /**
             * Here we are trying to implement single source of truth i.e.
             * First we are checking the data from DB, if stored then we are getting the data and displaying it
             * if not then we are getting it from the api and then storing it in db and then showing it from DB
             */
            val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            try {
                val remoteWordInfo = repository.getDetailsAboutWord(word)
                dao.deleteWordInfos(wordInfos.map { it.word })
                dao.insertWordInfos(remoteWordInfo.map { it.toWordInfoEntity() })
                val newWordInfo = dao.getWordInfos(word).map { it.toWordInfo() }
                emit(Resource.Success(newWordInfo))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString(), data = wordInfos))
            }
        }
    }
}



