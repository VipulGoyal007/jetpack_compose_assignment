package com.example.vipulcomposetask.presentation.crypto_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vipulcomposetask.core.Resource
import com.example.vipulcomposetask.domain.model.CryptoData
import com.example.vipulcomposetask.domain.usecase.GetCryptoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val getCryptoListUseCase: GetCryptoListUseCase
) : ViewModel(){

    private val _cryptoListState = MutableStateFlow<Resource<List<CryptoData>>>(Resource.Loading())
    val cryptoListState get() = _cryptoListState.asStateFlow()

    init {
        loadCryptoList()
    }

    private fun loadCryptoList() {
        viewModelScope.launch(Dispatchers.IO) {
        getCryptoListUseCase().onStart {
            _cryptoListState.emit(Resource.Loading())
        }.catch {
            _cryptoListState.emit(Resource.Error(message =it.message.orEmpty()))
        }.collect {
            if(it.isEmpty()){
                _cryptoListState.emit(Resource.Error(message = "No Data Found"))

            }else{
                _cryptoListState.emit(Resource.Success(it))

            }}
        }

    }

}