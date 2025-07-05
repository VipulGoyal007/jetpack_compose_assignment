package com.example.vipulcomposetask.core
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CryptoList : Route

    @Serializable
    data class CryptoDetails(val selectedCrypto: String) : Route

}