package com.example.vipulcomposetask.domain.usecase

import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCryptoListUseCase @Inject constructor(
    private val cryptoListRepository: CryptoRepository
) {
    suspend operator fun invoke(): Flow<List<CryptoData>> = flow {
        val res = cryptoListRepository.getCryptoList()
        emit(res)
    }
}

