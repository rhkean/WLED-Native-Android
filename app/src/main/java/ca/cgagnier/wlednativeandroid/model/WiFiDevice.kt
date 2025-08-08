package ca.cgagnier.wlednativeandroid.model

import android.graphics.Color
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ca.cgagnier.wlednativeandroid.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class WiFiDevice(
    override val address: String,
    override val name: String,
    override val isCustomName: Boolean,
    override val isHidden: Boolean,
    override val macAddress: String,
    override val brightness: Int = 0,
    override val color: Int = Color.WHITE,
    override val isPoweredOn: Boolean = false,
    override val isOnline: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val networkBssid: String = UNKNOWN_VALUE,
    override val networkRssi: Int = -101,
    override val networkSignal: Int = 0,
    override val networkChannel: Int = 0,
    override val isEthernet: Boolean = false,
    override val platformName: String = UNKNOWN_VALUE,
    override val version: String = UNKNOWN_VALUE,
    override val newUpdateVersionTagAvailable: String = "",
    override val skipUpdateTag: String = "",
    override val branch: Branch = Branch.UNKNOWN,
    override val brand: String = UNKNOWN_VALUE,
    override val productName: String = UNKNOWN_VALUE,
    override val release: String = UNKNOWN_VALUE,
    override val batteryPercentage: Double = 0.0,
    override val hasBattery: Boolean = false,
    override val isBle: Boolean = false,
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
), IDevice, Parcelable {

    override fun getDeviceUrl(): String {
        return "http://$address"
    }

    override fun getNetworkStrengthImage(): Int {
        if (!isOnline) {
            if(isBle) return R.drawable.twotone_signal_ble_no_connection
            return R.drawable.twotone_signal_wifi_connected_no_internet_0_24
        }
        if (networkRssi >= -50) {
            if(isBle) return R.drawable.twotone_signal_ble_4_bar
            return R.drawable.twotone_signal_wifi_4_bar_24
        }
        if (networkRssi >= -70) {
            if(isBle) return R.drawable.twotone_signal_ble_3_bar
            return R.drawable.twotone_signal_wifi_3_bar_24
        }
        if (networkRssi >= -80) {
            if(isBle) return R.drawable.twotone_signal_ble_2_bar
            return R.drawable.twotone_signal_wifi_2_bar_24
        }
        if (networkRssi >= -100) {
            if(isBle) return R.drawable.twotone_signal_ble_1_bar
            return R.drawable.twotone_signal_wifi_1_bar_24
        }
        if(isBle) return R.drawable.twotone_signal_ble_0_bar
        return R.drawable.twotone_signal_wifi_0_bar_24
    }

    override fun isAPMode(): Boolean {
        return address == DEFAULT_WLED_AP_IP
    }
}