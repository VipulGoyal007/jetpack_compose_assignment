package com.example.vipulcomposetask.data.api

import com.example.vipulcomposetask.data.model.Coin
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoListApi {

    @GET("v3/coins/markets")
    suspend fun getCryptoList(
        @Query("vs_currency") vsCurrency: String?=Constants.VS_CURRENCY,
        @Query("per_page") perPage: Int? = Constants.PER_PAGE,
        @Query("page") page: Int? = Constants.PAGE,
        ): List<Coin>
}
