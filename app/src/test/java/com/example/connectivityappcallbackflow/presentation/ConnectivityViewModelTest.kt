package com.example.connectivityappcallbackflow.presentation

import app.cash.turbine.test
import com.example.connectivityappcallbackflow.domain.manager.NetworkConnectivityService
import com.example.connectivityappcallbackflow.domain.model.NetworkStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConnectivityViewModelTest {

    private lateinit var networkConnectivityService: NetworkConnectivityService
    private lateinit var viewModel: ConnectivityViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        networkConnectivityService = mockk(relaxed = true)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `networkState should emit initial value as Unknown`() = testScope.runTest {
        // Given
        val networkStateFlow = MutableStateFlow(NetworkStatus.Unknown)
        coEvery { networkConnectivityService.networkStatus } returns networkStateFlow

        // When
        viewModel = ConnectivityViewModel(networkConnectivityService)

        // Then
        viewModel.networkState.test {
            assertEquals(NetworkStatus.Unknown, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `networkState should emit Disconnected when the network becomes available`() =
        testScope.runTest {
            // Given
            val networkStateFlow = MutableStateFlow<NetworkStatus>(NetworkStatus.Unknown)
            coEvery { networkConnectivityService.networkStatus } returns networkStateFlow

            // When
            viewModel = ConnectivityViewModel(networkConnectivityService)

            // Then
            viewModel.networkState.test {
                assertEquals(NetworkStatus.Unknown, awaitItem()) // Assert initial state

                // Simulate network availability
                networkStateFlow.value = NetworkStatus.Disconnected
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(NetworkStatus.Disconnected, awaitItem()) // Assert new state
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `networkState should emit Connected when the network becomes available`() =
        testScope.runTest {
            // Given
            val networkStateFlow = MutableStateFlow<NetworkStatus>(NetworkStatus.Unknown)
            coEvery { networkConnectivityService.networkStatus } returns networkStateFlow

            // When
            viewModel = ConnectivityViewModel(networkConnectivityService)

            // Then
            viewModel.networkState.test {
                assertEquals(NetworkStatus.Unknown, awaitItem()) // Assert initial state

                // Simulate network availability
                networkStateFlow.value = NetworkStatus.Connected
                testDispatcher.scheduler.advanceUntilIdle()
                assertEquals(NetworkStatus.Connected, awaitItem()) // Assert new state
                cancelAndIgnoreRemainingEvents()
            }
        }
}