package ca.cgagnier.wlednativeandroid.service

import android.util.Log
import ca.cgagnier.wlednativeandroid.BlePermissions
import ca.cgagnier.wlednativeandroid.model.BleDevice
import ca.cgagnier.wlednativeandroid.model.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.nordicsemi.kotlin.ble.client.android.CentralManager
import no.nordicsemi.kotlin.ble.client.android.ConnectionPriority
import no.nordicsemi.kotlin.ble.client.android.Peripheral
import no.nordicsemi.kotlin.ble.client.distinctByPeripheral
import no.nordicsemi.kotlin.ble.core.ConnectionState
import no.nordicsemi.kotlin.ble.core.WriteType
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val WLED_BLE_SERVICE_UUID = "01FA0001-46C9-4507-84BB-F2BE3F24C47A"

class BleDeviceDiscovery(
    private val centralManager: CentralManager,
    private val onDeviceDiscovered: (Device) -> Unit,
    private val blePermissions: BlePermissions,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    val state = centralManager.state

    private val _peripherals: MutableStateFlow<List<Peripheral>> = MutableStateFlow(emptyList())
    val peripherals = _peripherals.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning = _isScanning.asStateFlow()

    private var connectionScopeMap = mutableMapOf<Peripheral, CoroutineScope>()

    private var scanningJob: Job? = null

    @OptIn(ExperimentalUuidApi::class)
    fun start() {
        if(!blePermissions.verifyPermissions()) return
        scanningJob = centralManager
            .scan(30.seconds) {
                ServiceUuid(Uuid.parse(WLED_BLE_SERVICE_UUID))
            }
            .onStart {
                _isScanning.update { true }
            }
            .distinctByPeripheral()
            .map {
                BleDevice(
                    address = it.peripheral.address,
                    name = it.peripheral.name ?: it.peripheral.address,
                    isCustomName = false,
                    isHidden = false,
                    macAddress = it.peripheral.address,
                    isBle = true,
                    networkRssi = it.rssi,
                    //peripheral = it.peripheral
                ).apply { this.peripheral = it.peripheral}
            }
            .filter { _peripherals.value.contains(it.peripheral) }
            .onEach { device ->
                Log.i(TAG, "Found new device: ${device.name} (${device.address})")
                _peripherals.update { peripherals.value + device.peripheral!! }
            }
            .onEach { device ->
                // Track state of each peripheral.
                // Note, that the states are observed using view model scope, even when the
                // device isn't connected.
                observePeripheralState(device.peripheral!!, scope)
                // Track bond state of each peripheral.
                observeBondState(device.peripheral!!, scope)
                onDeviceDiscovered(device)
            }
            .catch { exception ->
                Log.e(TAG, "Scan failed: $exception")
            }
            .onCompletion {
                _isScanning.update { false }
            }
            .launchIn(scope)
    }

    fun stop() {
        scanningJob?.cancel()
    }

    private suspend fun initiateConnection(peripheral: Peripheral) {
        try {
            // Request MTU
            peripheral.requestHighestValueLength()

            // Check maximum write length
            val writeType = WriteType.WITHOUT_RESPONSE
            val length = peripheral.maximumWriteValueLength(writeType)
            Log.i(TAG, "Maximum write length for $writeType: $length")

            // Read RSSI
            val rssi = peripheral.readRssi()
            Log.i(TAG, "RSSI: $rssi dBm")

            // Read PHY
            val phyInUse = peripheral.readPhy()
            Log.i(TAG, "PHY in use: $phyInUse")

            // Request connection priority
            val newConnectionParameters = peripheral.requestConnectionPriority(ConnectionPriority.HIGH)
            Log.i(TAG, "Connection priority changed to HIGH")
            Log.i(TAG, "New connection parameters: $newConnectionParameters")
        } catch (e: Exception) {
            Log.e(TAG, "OMG!", e)
        }
    }

    private fun observePeripheralState(peripheral: Peripheral, scope: CoroutineScope) {
        peripheral
            .state
            .buffer()
            .onEach {
                Log.i(TAG, "State of $peripheral: $it")

                // Each time a connection changes, handle the new state
                when (it) {
                    is ConnectionState.Connected -> {
                        connectionScopeMap[peripheral]?.launch {
                            initiateConnection(peripheral)
                        }
                    }

                    is ConnectionState.Disconnected -> {
                        // Just for testing, wait with cancelling the scope to get all the logs.
                        delay(500)
                        // Cancel connection scope, so that previously launched jobs are cancelled.
                        connectionScopeMap.remove(peripheral)?.cancel()
                    }

                    else -> { /* Ignore */ }
                }
            }
            .onCompletion {
                Log.d(TAG, "State collection for $peripheral completed")
            }
            .launchIn(scope)
    }

    private fun observeBondState(peripheral: Peripheral, scope: CoroutineScope) {
        peripheral
            .bondState
            .onEach {
                Log.i(TAG, "Bond state of ${peripheral}: $it")
            }
            .onCompletion {
                Log.d(TAG, "Bond state collection for ${peripheral} completed")
            }
            .launchIn(scope)
    }

    companion object {
        private const val TAG = "BLE_DISCOVERY"
    }
}
