package com.example.vipulcomposetask.di


import android.content.Context
import androidx.room.Room
import com.example.vipulcomposetask.BuildConfig
import com.example.vipulcomposetask.core.NetworkUtil
import com.example.vipulcomposetask.data.api.CryptoListApi
import com.example.vipulcomposetask.data.local.AppDatabase
import com.example.vipulcomposetask.data.local.dao.CryptoDao
import com.example.vipulcomposetask.data.repository.CryptoRepositoryImpl
import com.example.vipulcomposetask.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoListApi(retrofit: Retrofit): CryptoListApi {
        return retrofit.create(CryptoListApi::class.java)
    }
    @Provides
    @Singleton
    fun cryptoListRepository(api: CryptoListApi,dao: CryptoDao,networkUtil: NetworkUtil): CryptoRepository {
        return CryptoRepositoryImpl(api,dao,networkUtil)
    }

    @Provides
    @Singleton
    fun providePortfolioDao(db: AppDatabase): CryptoDao = db.cryptoDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.DB_NAME
        ).build()
    }
}