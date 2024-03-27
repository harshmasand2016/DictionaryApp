package com.compose.projects.dictionaryapp.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun ProgressBar(modifier: Modifier, color: Color = MaterialTheme.colorScheme.primary) {
    Box(modifier = modifier){
        CircularProgressIndicator(modifier = modifier,
            color = color)
    }
}