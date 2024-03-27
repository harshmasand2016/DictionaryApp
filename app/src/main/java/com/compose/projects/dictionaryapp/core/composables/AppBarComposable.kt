package com.compose.projects.dictionaryapp.core.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarComposable(
    titleText: String,
    titleNavigationIcon: ImageVector?,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = titleText)
        },
        navigationIcon = {
            if (titleNavigationIcon != null) {
                IconButton(onClick = { onNavigationIconClick() }) {
                    Icon(titleNavigationIcon, "")
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            scrolledContainerColor = Color.Transparent
        )
    )
}