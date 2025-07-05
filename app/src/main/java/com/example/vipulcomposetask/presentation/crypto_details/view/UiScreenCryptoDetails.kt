package com.example.vipulcomposetask.presentation.crypto_details.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vipulcomposetask.core.ui.components.CustomToolbar
import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.model.CryptoDetails
import com.example.vipulcomposetask.presentation.crypto_details.components.InfoRow
import com.example.vipulcomposetask.presentation.crypto_details.view_model.CryptoDetailsViewModel

@Composable
fun UiScreenCryptoDetails(
    cryptoData: CryptoData,
    viewModel: CryptoDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getCryptoData(cryptoData)
    }
    val cryptoDataState by viewModel.cryptoDataState.collectAsStateWithLifecycle()
    UiCryptoDetails(
        cryptoDataState = cryptoDataState,
        crypto = cryptoData
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiCryptoDetails(
    cryptoDataState: List<CryptoDetails>,
    crypto: CryptoData, modifier: Modifier = Modifier
) {

    Scaffold(topBar = {
        CustomToolbar(
            title = "${crypto.name} (${crypto.symbol.uppercase()})",
            isBackArrow = true
        )
    }) { padding ->
        val backgroundColor = MaterialTheme.colorScheme.background
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(horizontal = 16.dp)
                .padding(top = padding.calculateTopPadding())
        ) {
            itemsIndexed(cryptoDataState) { index, item ->
                item.run {  InfoRow(label = label, value = value) }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Last Updated: ${
                        crypto.last_updated.replace("T", " ").replace("Z", "")
                    }",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

