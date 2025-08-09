package ca.cgagnier.wlednativeandroid.model

import android.graphics.Color
import ca.cgagnier.wlednativeandroid.R

class WiFiDevice(
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
    isBle: Boolean = false,
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
    override fun getDeviceUrl(): String {
        return "http://$address"
    }

    override fun getNetworkStrengthImage(): Int {
        if (!isOnline) {
            return R.drawable.twotone_signal_wifi_connected_no_internet_0_24
        }
        if (networkRssi >= -50) {
            return R.drawable.twotone_signal_wifi_4_bar_24
        }
        if (networkRssi >= -70) {
            return R.drawable.twotone_signal_wifi_3_bar_24
        }
        if (networkRssi >= -80) {
            return R.drawable.twotone_signal_wifi_2_bar_24
        }
        if (networkRssi >= -100) {
            return R.drawable.twotone_signal_wifi_1_bar_24
        }
        return R.drawable.twotone_signal_wifi_0_bar_24
    }

    override fun isAPMode(): Boolean {
        return address == DEFAULT_WLED_AP_IP
    }
}