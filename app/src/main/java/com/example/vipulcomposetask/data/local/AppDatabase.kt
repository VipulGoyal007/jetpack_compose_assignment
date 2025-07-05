package com.example.vipulcomposetask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vipulcomposetask.data.local.dao.CryptoDao
import com.example.vipulcomposetask.data.local.entity.CryptoEntity


@Database(entities = [CryptoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}