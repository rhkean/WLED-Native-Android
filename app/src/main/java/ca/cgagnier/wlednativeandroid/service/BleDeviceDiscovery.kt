package ca.cgagnier.wlednativeandroid.service

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import ca.cgagnier.wlednativeandroid.model.Device
import kotlin.getValue
import android.bluetooth.le.ScanSettings
import android.annotation.SuppressLint
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanFilter
import android.util.Log
import android.os.ParcelUuid
///////////////////////////////////

//import com.punchthrough.blestarterappandroid.ble.ConnectionEventListener
//import com.punchthrough.blestarterappandroid.ble.ConnectionManager
//import com.punchthrough.blestarterappandroid.databinding.ActivityMainBinding

private val WLED_BLE_SERVICE_UUID = "01FA0001-46C9-4507-84BB-F2BE3F24C47A"

@SuppressLint("MissingPermission") // App's role to ensure permissions are available
class BleDeviceDiscovery (
    val context: Context,
    val onDeviceDiscovered: (Device) -> Unit
) {
    private var isScanning = false
        set(value) {
            field = value
        }
    private val scanResults = mutableListOf<ScanResult>()

    val scanFilter = ScanFilter.Builder().setServiceUuid(
        ParcelUuid.fromString(WLED_BLE_SERVICE_UUID)
    ).build()

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
        .build()

    // If we're getting a scan result, we already have the relevant permission(s)
    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if(indexQuery != -1) { // A scan result already exists with the same address
                scanResults[indexQuery] = result
            } else {
                val deviceAddress = result.device.address
                val deviceName = result.device.name
                Log.i(TAG, "Found BLE device! Name: ${deviceName ?: "Unnamed"}, address: $deviceAddress")

                onDeviceDiscovered(
                    Device(
                        deviceAddress,
                        deviceName,
                        isCustomName = false,
                        isHidden = false,
                        macAddress = Device.UNKNOWN_VALUE
                    )
                )
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e(TAG, "onScanFailed: code $errorCode")
        }
    }

    private fun initialize() {

    }

    fun start() {
        initialize()
        val scanFilters = listOf(scanFilter)
        bleScanner.startScan(scanFilters, scanSettings, scanCallback)
        isScanning = true
    }

    fun stop() {
        if(!isScanning) return
        bleScanner.stopScan(scanCallback)
        isScanning = false
    }

    companion object {
        private const val TAG = "BLE_DISCOVERY"
    }
}