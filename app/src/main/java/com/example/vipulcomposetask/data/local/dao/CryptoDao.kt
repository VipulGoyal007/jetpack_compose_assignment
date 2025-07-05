package com.example.vipulcomposetask.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.vipulcomposetask.data.local.Constants
import com.example.vipulcomposetask.data.local.entity.CryptoEntity


@Dao
interface CryptoDao {
    @Upsert
    suspend fun insertCryptoListToDb(holdings: List<CryptoEntity>)

    @Query("SELECT * FROM ${Constants.CRYPTO_TABLE_NAME}")
    fun getCryptoListFromDb():List<CryptoEntity>
}