package com.example.vipulcomposetask

import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.repository.CryptoRepository
import com.example.vipulcomposetask.domain.usecase.GetCryptoListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class GetCryptoListUseCaseUnitTest {
    private lateinit var repository: CryptoRepository
    private lateinit var useCase: GetCryptoListUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetCryptoListUseCase(repository)
    }

    @Test
    fun `invoke returns crypto list from repository`() = runTest {
        // Arrange
        val mockCryptoList = listOf(
            CryptoData(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                image = "https://example.com/btc.png",
                current_price = 30000.0,
                market_cap = 500000000000,
                market_cap_rank = 1,
                total_volume = 35000000000.0,
                high_24h = 30500.0,
                low_24h = 29500.0,
                price_change_24h = -100.0,
                price_change_percentage_24h = -0.33,
                market_cap_change_24h = -1500000000.0,
                market_cap_change_percentage_24h = -0.3,
                circulating_supply = 19000000.0,
                total_supply = 21000000.0,
                max_supply = 21000000.0,
                ath = 69000.0,
                ath_change_percentage = -56.52,
                ath_date = "2021-11-10T00:00:00Z",
                atl = 67.81,
                atl_change_percentage = 44163.2,
                atl_date = "2013-07-06T00:00:00Z",
                last_updated = "2025-07-05T00:00:00Z"
            )
        )

        `when`(repository.getCryptoList()).thenReturn(mockCryptoList)

        // Act
        val result = useCase.invoke().first()

        // Assert
        assertEquals(mockCryptoList, result)
        verify(repository).getCryptoList()
    }

    @Test
    fun `invoke emits empty list when repository returns empty`() = runTest {
        // Arrange
        `when`(repository.getCryptoList()).thenReturn(emptyList())

        // Act
        val result = useCase.invoke().first()

        // Assert
        assertEquals(emptyList<CryptoData>(), result)
        verify(repository).getCryptoList()
    }
}