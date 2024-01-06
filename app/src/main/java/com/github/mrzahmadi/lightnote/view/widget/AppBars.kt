package com.github.mrzahmadi.lightnote.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mrzahmadi.lightnote.ui.theme.appBarColor
import com.github.mrzahmadi.lightnote.ui.theme.appBarDividerColor
import com.github.mrzahmadi.lightnote.ui.theme.primaryDarkColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Title"
) {
    Column(modifier) {
        TopAppBar(
            title = { Text(text = title) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = appBarColor(),
                titleContentColor = primaryDarkColor()
            )
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(appBarDividerColor())
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Title",
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(modifier) {
        TopAppBar(
            title = { Text(text = title) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = appBarColor(),
                titleContentColor = primaryDarkColor()
            ),
            navigationIcon = {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = primaryDarkColor()
                    )
                }
            }
        )
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(appBarDividerColor())
        )
    }
}

@Composable
fun BaseTopAppBarWithBack(
    modifier: Modifier = Modifier,
    title: String = "Title",
    onClick: () -> Unit
) {
    BaseTopAppBar(
        modifier,
        title,
        Icons.Filled.ArrowBack,
        onClick
    )
}

@Preview
@Composable
fun BaseTopAppBarPreview() {
    BaseTopAppBar()
}

@Preview
@Composable
fun BaseTopAppBarWithIcon() {
    BaseTopAppBar(
        icon = Icons.Filled.ArrowBack,
        onClick = {}
    )
}
