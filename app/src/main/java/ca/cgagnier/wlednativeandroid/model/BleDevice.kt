package ca.cgagnier.wlednativeandroid.model

import android.graphics.Color
import ca.cgagnier.wlednativeandroid.R
import no.nordicsemi.kotlin.ble.client.android.Peripheral

class BleDevice(
    address: String,
    name: String = "",
    isCustomName: Boolean = false,
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
    isBle: Boolean = true,
): Device(
    address = address,
    name = name,
    isCustomName = isCustomName,
    isHidden = isHidden,
    macAddress = macAddress,
    brightness = brightness,
    color = color,
    isPoweredOn = isPoweredOn,
    isOnline = isOnline,
    isRefreshing = isRefreshing,
    networkBssid = networkBssid,
    networkRssi = networkRssi,
    networkSignal = networkSignal,
    networkChannel = networkChannel,
    isEthernet = isEthernet,
    platformName = platformName,
    version = version,
    newUpdateVersionTagAvailable = newUpdateVersionTagAvailable,
    skipUpdateTag = skipUpdateTag,
    branch = branch,
    brand = brand,
    productName = productName,
    release = release,
    batteryPercentage = batteryPercentage,
    hasBattery = hasBattery,
    isBle = isBle
) {
    constructor(device: Device): this(
        address = device.address,
        name = device.name,
        isCustomName = device.isCustomName,
        isHidden = device.isHidden,
        macAddress = device.macAddress,
        brightness = device.brightness,
        color = device.color,
        isPoweredOn = device.isPoweredOn,
        isOnline = device.isOnline,
        isRefreshing = device.isRefreshing,
        networkBssid = device.networkBssid,
        networkRssi = device.networkRssi,
        networkSignal = device.networkSignal,
        networkChannel = device.networkChannel,
        isEthernet = device.isEthernet,
        platformName = device.platformName,
        version = device.version,
        newUpdateVersionTagAvailable = device.newUpdateVersionTagAvailable,
        skipUpdateTag = device.skipUpdateTag,
        branch = device.branch,
        brand = device.brand,
        productName = device.productName,
        release = device.release,
        batteryPercentage = device.batteryPercentage,
        hasBattery = device.hasBattery,
        isBle = device.isBle
    )
    var peripheral: Peripheral? = null

    override fun getDeviceUrl(): String { return address }

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

    operator fun component27() = peripheral
}