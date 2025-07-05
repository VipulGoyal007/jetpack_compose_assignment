package com.example.vipulcomposetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.vipulcomposetask.core.Route
import com.example.vipulcomposetask.core.ui.theme.AppTheme
import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.presentation.crypto_details.view.UiScreenCryptoDetails
import com.example.vipulcomposetask.presentation.crypto_list.view.UiScreenCryptoList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

val LocalNavController =
    compositionLocalOf<NavHostController> { error("CompositionLocal LocalNavController not present") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) {
                ComposeApp()
            }
        }
    }
}

@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        Box {
            NavHost(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                startDestination = Route.CryptoList
            ) {
                composable<Route.CryptoList> {
                    UiScreenCryptoList()
                }
                composable<Route.CryptoDetails> {
                    val route: Route.CryptoDetails = it.toRoute()
                    val cryptoData =
                        route.selectedCrypto.let { Json.decodeFromString<CryptoData>(it) }
                    UiScreenCryptoDetails(cryptoData)
                }

            }

        }
    }
}

