package com.example.vipulcomposetask.presentation.crypto_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vipulcomposetask.core.ui.theme.BlackTextDark
import com.example.vipulcomposetask.core.ui.theme.Green
import com.example.vipulcomposetask.core.ui.theme.Red
import com.example.vipulcomposetask.domain.model.CryptoData
import java.util.Locale

@Composable
fun CryptoListItem(crypto: CryptoData, onItemClick: (CryptoData) -> Unit) {
    val isPositive = crypto.price_change_percentage_24h >= 0
    val changeColor = if (isPositive) Red else Green
    val sign = if (isPositive) "+" else ""

    val items = listOf(
        Triple(crypto.name, TextAlign.Start, 1.2f),
        Triple(crypto.symbol.uppercase(), TextAlign.Center, 1f),
        Triple("$${String.format(Locale.US, "%.2f", crypto.current_price)}", TextAlign.Center, 1f),
        Triple(
            "$sign${String.format(Locale.US, "%.2f", crypto.price_change_percentage_24h)}%",
            TextAlign.End,
            1.2f
        )
    )

    val colors = listOf(null, null, Color.Gray, changeColor)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onItemClick(crypto) },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, (text, align, weight) ->
                ItemTextView(
                    title = text,
                    fontWeight = FontWeight.Bold.takeIf { index < 2 },
                    color = colors.getOrNull(index) ?: BlackTextDark,
                    textAlign = align,
                    maxLines = 2.takeIf { index == 0 } ?: 1,
                    modifier = Modifier.weight(weight)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCryptoListItem() {
    val sampleCrypto = CryptoData(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
        current_price = 62234.12,
        market_cap = 1234567890,
        market_cap_rank = 1,
        total_volume = 987654321.0,
        high_24h = 63000.0,
        low_24h = 61000.0,
        price_change_24h = -300.0,
        price_change_percentage_24h = -0.48,
        market_cap_change_24h = -15000000.0,
        market_cap_change_percentage_24h = -1.2,
        circulating_supply = 19000000.0,
        total_supply = 21000000.0,
        max_supply = 21000000.0,
        ath = 69000.0,
        ath_change_percentage = -9.8,
        ath_date = "2021-11-10T14:00:00Z",
        atl = 67.81,
        atl_change_percentage = 91500.0,
        atl_date = "2013-07-06T00:00:00Z",
        last_updated = "2025-07-07T10:00:00Z"
    )

    CryptoListItem(crypto = sampleCrypto, onItemClick = {})
}

