package com.compose.projects.dictionaryapp.feature_dictionary.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.projects.dictionaryapp.core.util.Resource
import com.compose.projects.dictionaryapp.feature_dictionary.domain.usecases.GetWordsInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DictionaryDetailsViewModel @Inject constructor(
    private val getWordsInfoUseCase: GetWordsInfoUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DictionaryDetailsState())
    val state: State<DictionaryDetailsState> = _state

    private var _searchQuery = mutableStateOf("")
    var searchQuery: State<String> = _searchQuery
    private var searchJob: Job? = null


    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getDetails(_searchQuery.value)
        }
    }

    fun onTextFieldClear() {
        _searchQuery.value = ""
        _state.value = DictionaryDetailsState()

    }


    private fun getDetails(word: String) {
        getWordsInfoUseCase(word).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = DictionaryDetailsState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _state.value = DictionaryDetailsState(
                        wordInfo = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = DictionaryDetailsState(
                        error = result.message ?: "Unknown Error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(url: String) {
        stopAudio()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                prepareAsync() // Use prepareAsync to not block the UI thread
                setOnPreparedListener { start() }
            }
        } else {
            mediaPlayer?.start() // Resume playback if mediaPlayer was paused
        }
    }

    fun pauseAudio() {
        mediaPlayer?.pause()
    }

    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release() // Clean up MediaPlayer to avoid memory leaks
        mediaPlayer = null

    }
}
