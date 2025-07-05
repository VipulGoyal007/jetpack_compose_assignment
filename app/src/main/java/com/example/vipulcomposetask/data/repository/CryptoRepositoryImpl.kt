package com.example.vipulcomposetask.data.repository

import com.example.vipulcomposetask.core.NetworkUtil
import com.example.vipulcomposetask.data.api.CryptoListApi
import com.example.vipulcomposetask.data.local.dao.CryptoDao
import com.example.vipulcomposetask.data.local.entity.CryptoEntity
import com.example.vipulcomposetask.data.local.entity.mapToCryptoData
import com.example.vipulcomposetask.data.model.Coin
import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.model.CryptoDetails
import com.example.vipulcomposetask.domain.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val cryptoListApi: CryptoListApi,
    private val cryptoDao: CryptoDao,
    private val networkUtil: NetworkUtil
) : CryptoRepository {

    override suspend fun getCryptoList(): List<CryptoData> {
        if (!networkUtil.getConnectivityStatus()) {
            return getCryptoListFromDb()
        }
        return try {
            val apiResponce = cryptoListApi.getCryptoList()
            /*Upsert data into local db*/
            cryptoDao.insertCryptoListToDb(mapToCryptoEntityDbRequest(apiResponce))
            return mapToCryptoEntity(apiResponce)
        } catch (e: Exception) {
            getCryptoListFromDb()
        }
    }

    private suspend fun getCryptoListFromDb(): List<CryptoData> = withContext(Dispatchers.IO) {
        cryptoDao.getCryptoListFromDb().map { it.mapToCryptoData() }
    }

    private suspend fun mapToCryptoEntity(list: List<Coin>): List<CryptoData> =
        withContext(Dispatchers.IO) {
            list.map { item ->
                with(item) { mapToCoinEntity() }
            }
        }

    private suspend fun mapToCryptoEntityDbRequest(list: List<Coin>): List<CryptoEntity> =
        withContext(Dispatchers.IO) {
            list.map { item ->
                with(item) { mapToCoinEntityDb() }
            }
        }

    override suspend fun getCryptoDetails(cryptoData: CryptoData): List<CryptoDetails> {
        return with(cryptoData) {
            buildList {
                add(CryptoDetails("Current Price", "$${String.format(Locale.US, "%.2f", current_price)}"))
                add(CryptoDetails("Market Cap", "$${formatLargeNumber(market_cap)}"))
                add(CryptoDetails("24h Volume", "$${formatLargeNumber(total_volume.toLong())}"))
                add(CryptoDetails("Circulating Supply", formatLargeNumber(circulating_supply.toLong())))
                add(CryptoDetails("Total Supply", formatLargeNumber(total_supply.toLong())))
                max_supply?.let { add(CryptoDetails("Max Supply", formatLargeNumber(it.toLong()))) }
                add(CryptoDetails("24h Change", "${String.format(Locale.US, "%.2f", price_change_percentage_24h)}%"))
                add(CryptoDetails("ATH", "$${ath} (${String.format(Locale.US, "%.2f", ath_change_percentage)}%)"))
                add(CryptoDetails("ATL", "$${atl} (${String.format(Locale.US, "%.2f", atl_change_percentage)}%)"))
            }
        }
    }

    private fun formatLargeNumber(value: Long): String {
        return when {
            value >= 1_000_000_000 -> String.format(Locale.US, "%.2fB", value / 1_000_000_000.0)
            value >= 1_000_000 -> String.format(Locale.US, "%.2fM", value / 1_000_000.0)
            value >= 1_000 -> String.format(Locale.US, "%.2fK", value / 1_000.0)
            else -> value.toString()
        }
    }
}
