package com.example.connectivityappcallbackflow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectivityappcallbackflow.domain.manager.NetworkConnectivityService
import com.example.connectivityappcallbackflow.domain.model.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    private val networkConnectivityService: NetworkConnectivityService
) : ViewModel() {

    val networkState = networkConnectivityService.networkStatus
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NetworkStatus.Unknown
        )
}