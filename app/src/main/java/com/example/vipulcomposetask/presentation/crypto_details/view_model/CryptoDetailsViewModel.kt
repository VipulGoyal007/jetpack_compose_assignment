package com.example.vipulcomposetask.presentation.crypto_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.model.CryptoDetails
import com.example.vipulcomposetask.domain.usecase.GetCryptoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val getCryptoDetailsUseCase: GetCryptoDetailsUseCase
) : ViewModel() {

    private val _cryptoDataState = MutableStateFlow<List<CryptoDetails>>(listOf())
    val cryptoDataState get() = _cryptoDataState.asStateFlow()
    fun getCryptoData(cryptoData: CryptoData) {
        viewModelScope.launch(Dispatchers.IO) {
            getCryptoDetailsUseCase(cryptoData).collect { _cryptoDataState.emit(it) }
        }
    }
}