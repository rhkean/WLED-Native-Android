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

@Entity
@Parcelize
data class Device(
    @PrimaryKey
    val address: String,
    val name: String,
    val isCustomName: Boolean,
    val isHidden: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val macAddress: String,
    val brightness: Int = 0,
    val color: Int = Color.WHITE,
    val isPoweredOn: Boolean = false,
    val isOnline: Boolean = false,
    val isRefreshing: Boolean = false,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val networkBssid: String = UNKNOWN_VALUE,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val networkRssi: Int = -101,
    @ColumnInfo(defaultValue = "0")
    val networkSignal: Int = 0,
    @ColumnInfo(defaultValue = "0")
    val networkChannel: Int = 0,
    @ColumnInfo(defaultValue = "0")
    val isEthernet: Boolean = false,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val platformName: String = UNKNOWN_VALUE,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val version: String = UNKNOWN_VALUE,
    @ColumnInfo(defaultValue = "")
    val newUpdateVersionTagAvailable: String = "",
    @ColumnInfo(defaultValue = "")
    val skipUpdateTag: String = "",
    @ColumnInfo(defaultValue = "UNKNOWN")
    val branch: Branch = Branch.UNKNOWN,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val brand: String = UNKNOWN_VALUE,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val productName: String = UNKNOWN_VALUE,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val release: String = UNKNOWN_VALUE,
    @ColumnInfo(defaultValue = "0.0")
    val batteryPercentage: Double = 0.0,
    @ColumnInfo(defaultValue = "0")
    val hasBattery: Boolean = false,
    @ColumnInfo(defaultValue = "0")
    val isBle: Boolean = false,
): Parcelable {
    @Ignore
    @IgnoredOnParcel
    var isSliding = false

    fun getDeviceUrl(): String {
        return "http://$address"
    }

    fun getNetworkStrengthImage(): Int {
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

    fun getBatteryPercentageImage(): Int {
        return when {
            batteryPercentage <= 10 -> R.drawable.baseline_battery_0_bar_24
            batteryPercentage <= 20 -> R.drawable.baseline_battery_1_bar_24
            batteryPercentage <= 40 -> R.drawable.baseline_battery_2_bar_24
            batteryPercentage <= 60 -> R.drawable.baseline_battery_3_bar_24
            batteryPercentage <= 70 -> R.drawable.baseline_battery_4_bar_24
            batteryPercentage <= 80 -> R.drawable.baseline_battery_5_bar_24
            batteryPercentage <= 90 -> R.drawable.baseline_battery_6_bar_24
            else -> R.drawable.baseline_battery_full_24
        }
    }

    fun hasUpdateAvailable(): Boolean {
        return newUpdateVersionTagAvailable != ""
    }

    fun isAPMode(): Boolean {
        return address == DEFAULT_WLED_AP_IP
    }

    companion object {
        const val UNKNOWN_VALUE = "__unknown__"
        const val DEFAULT_WLED_AP_IP = "4.3.2.1"

        fun getDefaultAPDevice(): Device {
            return Device(
                address = DEFAULT_WLED_AP_IP,
                name = "WLED AP Mode",
                isCustomName = true,
                isHidden = false,
                isOnline = true,
                networkRssi = 1,
                macAddress = UNKNOWN_VALUE
            )
        }

        fun getPreviewDevice(): Device {
            return Device(
                "10.0.0.1",
                "Preview Device",
                isCustomName = false,
                isHidden = false,
                macAddress = "00:00:00:00:00:00"
            )
        }
    }
}