package ca.cgagnier.wlednativeandroid.repository

import androidx.annotation.WorkerThread
import ca.cgagnier.wlednativeandroid.model.BleDevice
import ca.cgagnier.wlednativeandroid.model.Device
import ca.cgagnier.wlednativeandroid.model.WiFiDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceRepository @Inject constructor(private val deviceDao: DeviceDao) {
    val allDevices: Flow<List<Device>> =
        deviceDao
            .getAlphabetizedDevices()
            .mapDeviceListFlow()
    val allDevicesOfflineLast: Flow<List<Device>> =
        deviceDao
            .getAlphabetizedDevicesOfflineLast()
            .mapDeviceListFlow()

    @WorkerThread
    fun getAllDevices(): List<Device> {
        return deviceDao
            .getAllDevices()
            .mapDeviceList()
    }

    @WorkerThread
    fun findLiveDeviceByAddress(address: String): Flow<Device?> {
        return deviceDao.findLiveDeviceByAddress(address).mapNullableDeviceFlow()
    }

    @WorkerThread
    suspend fun findDeviceByMacAddress(address: String): Device? {
        return deviceDao.findDeviceByMacAddress(address)?.mapDevice()
    }

    @WorkerThread
    suspend fun insert(device: Device) {
        deviceDao.insert(device)
    }

    @WorkerThread
    suspend fun update(device: Device) {
        deviceDao.update(device)
    }

    @WorkerThread
    suspend fun delete(device: Device) {
        deviceDao.delete(device)
    }

    fun contains(device: Device): Boolean {
        return deviceDao.count(device.address) > 0
    }

    suspend fun hasHiddenDevices(): Boolean {
        return deviceDao.countHiddenDevices() > 0
    }
}

fun Flow<List<Device>>.mapDeviceListFlow(): Flow<List<Device>> {
    return this.map { list ->
        list.mapDeviceList()
    }
}
fun List<Device>.mapDeviceList(): List<Device> {
    return this.map {device ->
        device.mapDevice()
    }
}
fun Device.mapDevice(): Device {
    return if(this.isBle)
        BleDevice(this)
    else
        WiFiDevice(this)
}
fun Flow<Device?>.mapNullableDeviceFlow(): Flow<Device?> {
    return this.map { device ->
        device?.mapDevice()
    }
}