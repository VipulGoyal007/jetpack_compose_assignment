package com.example.vipulcomposetask.presentation.crypto_list.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vipulcomposetask.LocalNavController
import com.example.vipulcomposetask.R
import com.example.vipulcomposetask.core.NetworkUtil
import com.example.vipulcomposetask.core.Resource
import com.example.vipulcomposetask.core.Route
import com.example.vipulcomposetask.core.ui.components.CustomToolbar
import com.example.vipulcomposetask.core.ui.components.NoDataFoundView
import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.presentation.crypto_list.components.CryptoListItem
import com.example.vipulcomposetask.presentation.crypto_list.components.TitleTextView
import com.example.vipulcomposetask.presentation.crypto_list.view_model.CryptoListViewModel
import kotlinx.serialization.json.Json

@Composable
fun UiScreenCryptoList(
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    val cryptoListState by viewModel.cryptoListState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    UiCryptoList(
        cryptoListState = cryptoListState,
    ) {
        navController.navigate(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiCryptoList(
    cryptoListState: Resource<List<CryptoData>>,
    toNavigate: (Route) -> Unit
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        CustomToolbar(
            title = "Crypto List",
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (cryptoListState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                    )
                }

                is Resource.Success -> {
                    if (cryptoListState.data.isNotEmpty()) {
                        val backgroundColor = MaterialTheme.colorScheme.background
                        val headers = listOf(
                            Triple(R.string.name, TextAlign.Start, 1.2f),
                            Triple(R.string.symbol, TextAlign.Center, 1f),
                            Triple(R.string.price, TextAlign.Center, 1f),
                            Triple(R.string.change_percentage, TextAlign.End, 1.2f)
                        )
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                headers.forEach { (titleRes, align, weight) ->
                                    TitleTextView(
                                        title = stringResource(id = titleRes),
                                        textAlign = align,
                                        modifier = Modifier.weight(weight)
                                    )
                                }
                            }

                            androidx.compose.material.Divider(
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                itemsIndexed(cryptoListState.data) { index, item ->
                                    CryptoListItem(
                                        crypto = item,
                                    ) { selectedCrypto ->
                                        val json = Json.encodeToString(
                                            CryptoData.serializer(),
                                            selectedCrypto
                                        )
                                        toNavigate(Route.CryptoDetails(json))
                                    }
                                }
                            }
                        }
                    } else {
                        NoDataFoundView()
                    }
                }

                is Resource.Error -> {
                    NoDataFoundView()
                    if (!NetworkUtil(context).getConnectivityStatus()) {
                        Toast.makeText(
                            context,
                            stringResource(id = R.string.please_check_your_internet_connection),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}



