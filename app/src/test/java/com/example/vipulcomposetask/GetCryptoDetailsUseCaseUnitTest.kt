package com.example.vipulcomposetask

import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.model.CryptoDetails
import com.example.vipulcomposetask.domain.repository.CryptoRepository
import com.example.vipulcomposetask.domain.usecase.GetCryptoDetailsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class GetCryptoDetailsUseCaseUnitTest {

    private lateinit var repository: CryptoRepository
    private lateinit var useCase: GetCryptoDetailsUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetCryptoDetailsUseCase(repository)
    }

    @Test
    fun `invoke returns correct crypto details list`() = runTest {
        // Given
        val cryptoData = CryptoData(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "https://example.com/btc.png",
            current_price = 50000.0,
            market_cap = 1000000000L,
            market_cap_rank = 1,
            total_volume = 500000000.0,
            high_24h = 51000.0,
            low_24h = 49000.0,
            price_change_24h = -1000.0,
            price_change_percentage_24h = -2.0,
            market_cap_change_24h = -20000000.0,
            market_cap_change_percentage_24h = -1.5,
            circulating_supply = 18000000.0,
            total_supply = 21000000.0,
            max_supply = 21000000.0,
            ath = 60000.0,
            ath_change_percentage = -16.67,
            ath_date = "2021-11-10T12:00:00Z",
            atl = 100.0,
            atl_change_percentage = 49000.0,
            atl_date = "2013-07-06T12:00:00Z",
            last_updated = "2024-07-06T12:00:00Z"
        )

        val expectedDetails = listOf(
            CryptoDetails("Current Price", "$50000.00"),
            CryptoDetails("Market Cap", "$1.00B"),
            CryptoDetails("24h Volume", "$500.00M"),
            CryptoDetails("Circulating Supply", "18.00M"),
            CryptoDetails("Total Supply", "21.00M"),
            CryptoDetails("Max Supply", "21.00M"),
            CryptoDetails("24h Change", "-2.00%"),
            CryptoDetails("ATH", "$60000.0 (-16.67%)"),
            CryptoDetails("ATL", "$100.0 (49000.00%)")
        )

        `when`(repository.getCryptoDetails(cryptoData)).thenReturn(expectedDetails)

        // When
        val result = useCase.invoke(cryptoData).first()

        // Then
        assertEquals(expectedDetails, result)
        verify(repository).getCryptoDetails(cryptoData)
    }

}