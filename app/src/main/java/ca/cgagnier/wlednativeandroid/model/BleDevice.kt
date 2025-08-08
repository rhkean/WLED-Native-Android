package ca.cgagnier.wlednativeandroid.model

import android.graphics.Color
import ca.cgagnier.wlednativeandroid.R
import no.nordicsemi.kotlin.ble.client.android.Peripheral

class BleDevice(
    address: String,
    name: String,
    isCustomName: Boolean,
    isHidden: Boolean,
    macAddress: String,
    brightness: Int = 0,
    color: Int = Color.WHITE,
    isPoweredOn: Boolean = false,
    isOnline: Boolean = false,
    isRefreshing: Boolean = false,
    networkBssid: String = UNKNOWN_VALUE,
    networkRssi: Int = -101,
    networkSignal: Int = 0,
    networkChannel: Int = 0,
    isEthernet: Boolean = false,
    platformName: String = UNKNOWN_VALUE,
    version: String = UNKNOWN_VALUE,
    newUpdateVersionTagAvailable: String = "",
    skipUpdateTag: String = "",
    branch: Branch = Branch.UNKNOWN,
    brand: String = UNKNOWN_VALUE,
    productName: String = UNKNOWN_VALUE,
    release: String = UNKNOWN_VALUE,
    batteryPercentage: Double = 0.0,
    hasBattery: Boolean = false,
    isBle: Boolean = false,
    val peripheral: Peripheral
): Device(
    address,
    name,
    isCustomName,
    isHidden,
    macAddress,
    brightness,
    color,
    isPoweredOn,
    isOnline,
    isRefreshing,
    networkBssid,
    networkRssi,
    networkSignal,
    networkChannel,
    isEthernet,
    platformName,
    version,
    newUpdateVersionTagAvailable,
    skipUpdateTag,
    branch,
    brand,
    productName,
    release,
    batteryPercentage,
    hasBattery,
    isBle
) {

    override fun getDeviceUrl(): String {
        return "$address"
    }

    override fun getNetworkStrengthImage(): Int {
        if (!isOnline) {
            return R.drawable.twotone_signal_ble_no_connection
        }
        if (networkRssi >= -50) {
            return R.drawable.twotone_signal_ble_4_bar
        }
        if (networkRssi >= -70) {
            return R.drawable.twotone_signal_ble_3_bar
        }
        if (networkRssi >= -80) {
            return R.drawable.twotone_signal_ble_2_bar
        }
        if (networkRssi >= -100) {
            return R.drawable.twotone_signal_ble_1_bar
        }
        return R.drawable.twotone_signal_ble_0_bar
    }
}