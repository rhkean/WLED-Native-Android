package ca.cgagnier.wlednativeandroid.model

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ca.cgagnier.wlednativeandroid.R
import kotlinx.parcelize.IgnoredOnParcel

@Entity
open class Device(
    @PrimaryKey
    val address: String,
    val name: String = "",
    val isCustomName: Boolean = false,
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
) {
    @Ignore
    @IgnoredOnParcel
    var isSliding = false
    open fun getDeviceUrl(): String { return "" }
    open fun getNetworkStrengthImage(): Int {
        return R.drawable.twotone_signal_wifi_connected_no_internet_0_24
    }
    open fun isAPMode():Boolean { return false }
    open fun hasUpdateAvailable(): Boolean {
        return newUpdateVersionTagAvailable != ""
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

    // Boilerplate data object method implementations
    @Ignore
    private val _hashCode = toString().hashCode()

    override fun hashCode():Int { return _hashCode }

    override fun equals(other: Any?): Boolean {
        return other is Device && _hashCode == other.hashCode()
    }

    override fun toString(): String {
        val className = when(this) {
            is BleDevice -> "BleDevice"
            is WiFiDevice -> "WiFiDevice"
            else -> "Device"
        }
        return "$className(" +
                "address=$address," +
                "name=$name," +
                "isCustomName=$isCustomName," +
                "isHidden=$isHidden," +
                "macAddress=$macAddress," +
                "brightness=$brightness," +
                "color=$color," +
                "isPoweredOn=$isPoweredOn," +
                "isOnline=$isOnline," +
                "isRefreshing=$isRefreshing," +
                "networkBssid=$networkBssid," +
                "networkRssi=$networkRssi," +
                "networkSignal=$networkSignal," +
                "networkChannel=$networkChannel," +
                "isEthernet=$isEthernet," +
                "platformName=$platformName," +
                "version=$version," +
                "newUpdateVersionTagAvailable=$newUpdateVersionTagAvailable," +
                "skipUpdateTag=$skipUpdateTag," +
                "branch=$branch," +
                "brand=$brand," +
                "productName=$productName," +
                "release=$release," +
                "batteryPercentage=$batteryPercentage," +
                "hasBattery=$hasBattery," +
                "isBle=$isBle)"
    }
    open fun copy(
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
        return when (this) {
            is BleDevice -> BleDevice(
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
            ).also { it.peripheral = this.peripheral }
            is WiFiDevice -> WiFiDevice(
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
            else -> Device(
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
    }

    operator fun component1() = address
    operator fun component2() = name
    operator fun component3() = isCustomName
    operator fun component4() = isHidden
    operator fun component5() = macAddress
    operator fun component6() = brightness
    operator fun component7() = color
    operator fun component8() = isPoweredOn
    operator fun component9() = isOnline
    operator fun component10() = isRefreshing
    operator fun component11() = networkBssid
    operator fun component12() = networkRssi
    operator fun component13() = networkSignal
    operator fun component14() = networkChannel
    operator fun component15() = isEthernet
    operator fun component16() = platformName
    operator fun component17() = version
    operator fun component18() = newUpdateVersionTagAvailable
    operator fun component19() = skipUpdateTag
    operator fun component20() = branch
    operator fun component21() = brand
    operator fun component22() = productName
    operator fun component23() = release
    operator fun component24() = batteryPercentage
    operator fun component25() = hasBattery
    operator fun component26() = isBle

    companion object {
        const val UNKNOWN_VALUE = "__unknown__"
        const val DEFAULT_WLED_AP_IP = "4.3.2.1"

        fun getDefaultAPDevice(): WiFiDevice {
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

        fun getPreviewWiFiDevice(): WiFiDevice {
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
