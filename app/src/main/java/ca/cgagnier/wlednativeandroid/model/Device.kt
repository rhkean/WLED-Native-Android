package ca.cgagnier.wlednativeandroid.model

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ca.cgagnier.wlednativeandroid.R
import kotlinx.parcelize.IgnoredOnParcel


@Entity(
    tableName = "Device",
    primaryKeys = ["address"],
)
open class Device(
    open val address: String,
    open val name: String,
    open val isCustomName: Boolean,
    open val isHidden: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val macAddress: String,
    open val brightness: Int,
    open val color: Int,
    open val isPoweredOn: Boolean,
    open val isOnline: Boolean,
    open val isRefreshing: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val networkBssid: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val networkRssi: Int,
    @ColumnInfo(defaultValue = "0")
    open val networkSignal: Int,
    @ColumnInfo(defaultValue = "0")
    open val networkChannel: Int,
    @ColumnInfo(defaultValue = "0")
    open val isEthernet: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val platformName: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val version: String,
    @ColumnInfo(defaultValue = "")
    open val newUpdateVersionTagAvailable: String,
    @ColumnInfo(defaultValue = "")
    open val skipUpdateTag: String,
    @ColumnInfo(defaultValue = "UNKNOWN")
    open val branch: Branch,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val brand: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val productName: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    open val release: String,
    @ColumnInfo(defaultValue = "0.0")
    open val batteryPercentage: Double,
    @ColumnInfo(defaultValue = "0")
    open val hasBattery: Boolean,
    @ColumnInfo(defaultValue = "0")
    open val isBle: Boolean
) {
    @Ignore
    @IgnoredOnParcel
    var isSliding = false

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

    companion object {
        const val UNKNOWN_VALUE = "__unknown__"
        const val DEFAULT_WLED_AP_IP = "4.3.2.1"

        fun getDefaultAPDevice(): Device {
            return WiFiDevice(
                address = DEFAULT_WLED_AP_IP,
                name = "WLED AP Mode",
                isCustomName = true,
                isHidden = false,
                isOnline = true,
                networkRssi = 1,
                macAddress = UNKNOWN_VALUE
            )
        }

        fun getPreviewWiFiDevice(): Device {
            return WiFiDevice(
                "10.0.0.1",
                "Preview Device",
                isCustomName = false,
                isHidden = false,
                macAddress = "00:00:00:00:00:00"
            )
        }
    }
}

interface IDevice {
    abstract fun isAPMode(): Boolean
    abstract fun getDeviceUrl(): String
    abstract fun getNetworkStrengthImage(): Int
}