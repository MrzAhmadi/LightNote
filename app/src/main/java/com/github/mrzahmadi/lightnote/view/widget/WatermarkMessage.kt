package com.github.mrzahmadi.lightnote.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mrzahmadi.lightnote.ui.theme.lightGrayColor
import com.github.mrzahmadi.lightnote.ui.theme.windowBackgroundColor

@Composable
fun WatermarkMessage(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = windowBackgroundColor())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = modifier
                .width(76.dp)
                .height(76.dp),
            imageVector = icon,
            tint = lightGrayColor(),
            contentDescription = text
        )
        Spacer(modifier = modifier.height(32.dp))
        Text(
            modifier = modifier.fillMaxWidth(),
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = lightGrayColor(),
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun WatermarkMessagePreview() {
    WatermarkMessage(
        icon = Icons.Filled.Face,
        text = "The Nice Description"
    )
}