package com.compose.projects.dictionaryapp.feature_dictionary.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.projects.dictionaryapp.R
import com.compose.projects.dictionaryapp.core.composables.AppBarComposable
import com.compose.projects.dictionaryapp.core.composables.ProgressBar
import com.compose.projects.dictionaryapp.core.util.Dimens
import com.compose.projects.dictionaryapp.feature_dictionary.domain.model.WordInfo


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DictionaryScreen(dictionaryDetailsViewModel: DictionaryDetailsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            AppBarComposable(titleText = "Dictionary App", titleNavigationIcon = null) {
                //Do Nothing
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                val focusManager = LocalFocusManager.current
                Column(modifier = Modifier.padding(start = Dimens.dp16, end = Dimens.dp16)) {
                    SearchBar(dictionaryDetailsViewModel, focusManager)
                    DictionaryContent(dictionaryDetailsViewModel)
                }
                if (dictionaryDetailsViewModel.state.value.isLoading) {
                    ProgressBar(modifier = Modifier.align(Alignment.Center))
                }
                if(dictionaryDetailsViewModel.state.value.error.isNotEmpty()) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    keyboardController?.hide()
                    Text(text = "Not Found!!", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun DictionaryContent(dictionaryDetailsViewModel: DictionaryDetailsViewModel) {
    val state = dictionaryDetailsViewModel.state
    Spacer(modifier = Modifier.height(Dimens.dp20))

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.value.wordInfo.size) { i ->
            val wordInfo = state.value.wordInfo[i]
            if (i > 0) {
                Spacer(modifier = Modifier.height(Dimens.dp8))
            }
            DictionaryContentItem(wordInfo) {
                for (phonetics in it.phonetics) {
                    if (phonetics.audio.isNotEmpty()) {
                        dictionaryDetailsViewModel.playAudio(phonetics.audio)
                        break
                    }
                }
            }

            if (i < state.value.wordInfo.size - 1) {
                HorizontalDivider()
            }
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            // Perform any necessary cleanup here
            dictionaryDetailsViewModel.stopAudio()
        }
    }


}

@Composable
fun DictionaryContentItem(wordInfo: WordInfo, onPlay: (WordInfo) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()
    Column(modifier = Modifier.padding(start = Dimens.dp12)) {
        Text(
            text = wordInfo.word,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = Dimens.sp16)
        )
        Spacer(modifier = Modifier.height(Dimens.dp8))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = wordInfo.phonetic,
                style = TextStyle(fontWeight = FontWeight.Light, fontSize = Dimens.sp12)
            )
            Spacer(modifier = Modifier.width(Dimens.dp16))
            for (phonetics in wordInfo.phonetics) {
                if (phonetics.audio.isNotEmpty()) {
                    Icon(painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onPlay(wordInfo) })
                    break
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.dp8))
        wordInfo.meanings.forEach { meaning ->
            Text(
                text = meaning.partOfSpeech,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = Dimens.sp14)
            )
            Spacer(modifier = Modifier.height(Dimens.dp8))
            meaning.definitions.forEach{ definition ->
                Text(
                    text = "• " + definition.definition,
                    style = TextStyle(fontWeight = FontWeight.Light, fontSize = Dimens.sp14)
                )
                Spacer(modifier = Modifier.height(Dimens.dp8))
                definition.example?.let {
                    Text(
                        modifier = Modifier.padding(start = Dimens.dp8),
                        text = "Example - " + definition.example,
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = Dimens.sp12)
                    )
                    Spacer(modifier = Modifier.height(Dimens.dp8))
                }

            }
        }
    }

    Spacer(modifier = Modifier.height(Dimens.dp8))
}


@Composable
fun SearchBar(dictionaryDetailsViewModel: DictionaryDetailsViewModel, focusManager: FocusManager) {
    var queryState by remember {
        mutableStateOf("")
    }
    Spacer(modifier = Modifier.height(Dimens.dp20))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.dp48)
            .shadow(elevation = Dimens.dp3, shape = RoundedCornerShape(Dimens.dp24))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(Dimens.dp24)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = Dimens.dp24),
            value = queryState,
            onValueChange = {
                queryState = it
                dictionaryDetailsViewModel.onSearch(queryState)
            },
            enabled = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = Dimens.sp16,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = { innerTextField ->
                if (queryState.isEmpty()) {
                    Text(
                        text = "Search a word",
                        color = Color.Gray.copy(alpha = 0.5f),
                        fontSize = Dimens.sp16,
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                dictionaryDetailsViewModel.onSearch(queryState)
                focusManager.clearFocus()
            }),
            singleLine = true

        )
        Box(
            modifier = Modifier
                .weight(1f)
                .size(Dimens.dp40)
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable {
                    if (queryState.isNotEmpty()) {
                        queryState = ""
                        dictionaryDetailsViewModel.onTextFieldClear()
                        focusManager.clearFocus()
                    }
                },
        ) {
            if (queryState.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.dp10),
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.dp10),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DictionaryScreenPreview() {
    DictionaryScreen()
}
