package com.example.connectivityappcallbackflow.domain.manager

import com.example.connectivityappcallbackflow.domain.model.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}