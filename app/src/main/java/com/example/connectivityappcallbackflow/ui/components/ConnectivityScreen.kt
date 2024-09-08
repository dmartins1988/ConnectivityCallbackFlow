package com.example.connectivityappcallbackflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.connectivityappcallbackflow.R
import com.example.connectivityappcallbackflow.domain.model.NetworkStatus

@Composable
fun ConnectivityScreen(
    modifier: Modifier = Modifier,
    state: NetworkStatus
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        val isConnected = when (state) {
            NetworkStatus.Connected -> true
            NetworkStatus.Disconnected -> false
            NetworkStatus.Unknown -> false
        }

        val msg = if (isConnected) {
            stringResource(id = R.string.internet_online)
        } else {
            stringResource(id = R.string.internet_offline)
        }

        val backgroundColor = if (isConnected) {
            Color.Green
        } else {
            Color.LightGray
        }

        Text(
            text = msg,
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(4.dp),
            style = TextStyle(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}