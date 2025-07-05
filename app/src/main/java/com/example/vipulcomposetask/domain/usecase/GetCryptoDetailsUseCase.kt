package com.example.vipulcomposetask.domain.usecase

import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.model.CryptoDetails
import com.example.vipulcomposetask.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCryptoDetailsUseCase @Inject constructor(
    private val cryptoListRepository: CryptoRepository
) {
    suspend operator fun invoke(cryptoData: CryptoData): Flow<List<CryptoDetails>> = flow {
        val res = cryptoListRepository.getCryptoDetails(cryptoData)
        emit(res)
    }
}

