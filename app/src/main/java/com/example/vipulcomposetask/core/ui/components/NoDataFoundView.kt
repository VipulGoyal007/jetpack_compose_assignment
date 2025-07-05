package com.example.vipulcomposetask.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.vipulcomposetask.R

@Composable
fun NoDataFoundView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(id = R.string.no_data_found),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoDataFoundViewPreview() {
    MaterialTheme {
        Surface {
            NoDataFoundView()
        }
    }
}

