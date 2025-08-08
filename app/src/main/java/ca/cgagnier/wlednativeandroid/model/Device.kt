package ca.cgagnier.wlednativeandroid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import ca.cgagnier.wlednativeandroid.R
import kotlinx.parcelize.IgnoredOnParcel


@Entity(
    tableName = "Device",
    primaryKeys = ["address"],
)
open class Device(
    val address: String,
    val name: String,
    val isCustomName: Boolean,
    val isHidden: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val macAddress: String,
    val brightness: Int,
    val color: Int,
    val isPoweredOn: Boolean,
    val isOnline: Boolean,
    val isRefreshing: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val networkBssid: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val networkRssi: Int,
    @ColumnInfo(defaultValue = "0")
    val networkSignal: Int,
    @ColumnInfo(defaultValue = "0")
    val networkChannel: Int,
    @ColumnInfo(defaultValue = "0")
    val isEthernet: Boolean,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val platformName: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val version: String,
    @ColumnInfo(defaultValue = "")
    val newUpdateVersionTagAvailable: String,
    @ColumnInfo(defaultValue = "")
    val skipUpdateTag: String,
    @ColumnInfo(defaultValue = "UNKNOWN")
    val branch: Branch,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val brand: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val productName: String,
    @ColumnInfo(defaultValue = UNKNOWN_VALUE)
    val release: String,
    @ColumnInfo(defaultValue = "0.0")
    val batteryPercentage: Double,
    @ColumnInfo(defaultValue = "0")
    val hasBattery: Boolean,
    @ColumnInfo(defaultValue = "0")
    val isBle: Boolean
) {
    @Ignore
    @IgnoredOnParcel
    var isSliding = false

    open fun getDeviceUrl(): String { return "" }

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

    open fun getNetworkStrengthImage(): Int {
        return R.drawable.twotone_signal_wifi_connected_no_internet_0_24
    }

    open fun isAPMode():Boolean { return false }
    fun hasUpdateAvailable(): Boolean {
        return newUpdateVersionTagAvailable != ""
    }

    fun copy(
        address: String = this.address,
        name: String = this.name,
        isCustomName: Boolean = this.isCustomName,
        isHidden: Boolean = this.isHidden,
        macAddress: String = this.macAddress,
        brightness: Int = this.brightness,
        color: Int = this.color,
        isPoweredOn: Boolean = this.isPoweredOn,
        isOnline: Boolean = this.isOnline,
        isRefreshing: Boolean = this.isRefreshing,
        networkBssid: String = this.networkBssid,
        networkRssi: Int = this.networkRssi,
        networkSignal: Int = this.networkSignal,
        networkChannel: Int = this.networkChannel,
        isEthernet: Boolean = this.isEthernet,
        platformName: String = this.platformName,
        version: String = this.version,
        newUpdateVersionTagAvailable: String = this.newUpdateVersionTagAvailable,
        skipUpdateTag: String = this.skipUpdateTag,
        branch: Branch = this.branch,
        brand: String = this.brand,
        productName: String = this.productName,
        release: String = this.release,
        batteryPercentage: Double = this.batteryPercentage,
        hasBattery: Boolean = this.hasBattery,
        isBle: Boolean = this.isBle
    ): Device {
        if(this is BleDevice)
            return BleDevice(
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
                isBle,
                peripheral = this.peripheral
            )
        else
            return WiFiDevice(
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
            )

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
