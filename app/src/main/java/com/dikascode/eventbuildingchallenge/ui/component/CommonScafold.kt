package com.dikascode.eventbuildingchallenge.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScaffold(
    title: String,
    subtitle: String,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    placeholderText: String,
    topMargin: Dp = 0.dp,
    bottomBarContent: @Composable () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.padding(top = topMargin),
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier)
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                        )
                        Text(
                            text = placeholderText,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                actions = actions,
            )
        },
        bottomBar = bottomBarContent
    ) { paddingValues ->
        content(paddingValues)
    }
}

