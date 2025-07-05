package com.example.vipulcomposetask.domain.repository

import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.model.CryptoDetails


interface CryptoRepository {
    suspend fun getCryptoList(): List<CryptoData>
    suspend fun getCryptoDetails(cryptoData: CryptoData): List<CryptoDetails>

}