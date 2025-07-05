package com.example.vipulcomposetask.presentation.crypto_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TitleTextView(title: String, textAlign: TextAlign? = null, modifier: Modifier) {
    Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        textAlign = textAlign ?: TextAlign.Center,
        modifier = modifier,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Preview(showBackground = true)
@Composable
fun TitleTextViewPreview() {
    MaterialTheme {
        Surface {
            TitleTextView(
                title = "Market Cap",
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
        }
    }
}

