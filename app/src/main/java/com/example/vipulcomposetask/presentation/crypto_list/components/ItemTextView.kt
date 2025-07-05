package com.example.vipulcomposetask.presentation.crypto_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vipulcomposetask.core.ui.theme.BlackTextDark

@Composable
fun ItemTextView(title: String,fontWeight: FontWeight? = null, textAlign: TextAlign? = null,maxLines: Int = 1, modifier: Modifier,
                 color: Color = BlackTextDark
) {
    Text(
        text = title,
        fontWeight = fontWeight?:FontWeight.Medium,
        fontSize = 13.sp,
        textAlign = textAlign ?: TextAlign.Center,
        modifier = modifier,
        maxLines = maxLines,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun ItemTextViewPreview() {
    MaterialTheme {
        Surface {
            ItemTextView(
                title = "Bitcoin",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

