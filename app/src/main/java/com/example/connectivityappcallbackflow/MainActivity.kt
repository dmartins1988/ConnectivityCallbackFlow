package com.example.connectivityappcallbackflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.connectivityappcallbackflow.presentation.ConnectivityViewModel
import com.example.connectivityappcallbackflow.ui.components.ConnectivityScreen
import com.example.connectivityappcallbackflow.ui.theme.ConnectivityAppCallbackFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectivityAppCallbackFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = viewModel.networkState.collectAsStateWithLifecycle()

                    ConnectivityScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state.value
                    )
                }
            }
        }
    }
}
