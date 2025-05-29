package ca.cgagnier.wlednativeandroid.repository

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import java.lang.System

class UserPreferencesRepository(private val dataStore: DataStore<UserPreferences>) {

    val themeMode get() = dataStore.data.map { it.theme }
    val autoDiscovery get() = dataStore.data.map { it.automaticDiscovery }
    val bleDiscovery get() = dataStore.data.map { it.bleDiscovery }
    val showOfflineDevicesLast get() = dataStore.data.map { it.showOfflineLast }
    val showHiddenDevices get() = dataStore.data.map { it.showHiddenDevices }
    val lastUpdateCheckDate get() = dataStore.data.map { it.lastUpdateCheckDate }

    suspend fun updateThemeMode(themeSettings: ThemeSettings) {
        Log.d(TAG, "updateThemeMode")
        dataStore.updateData {
            it.toBuilder()
                .setTheme(themeSettings)
                .setDateLastWritten(System.currentTimeMillis())
                .build()
        }
    }

    suspend fun updateAutoDiscovery(autoDiscover: Boolean) {
        Log.d(TAG, "updateAutoDiscovery")
        dataStore.updateData {
            it.toBuilder()
                .setAutomaticDiscovery(autoDiscover)
                .setDateLastWritten(System.currentTimeMillis())
                .build()
        }
    }

    suspend fun updateShowOfflineDeviceLast(showOfflineDeviceLast: Boolean) {
        Log.d(TAG, "updateShowOfflineDeviceLast")
        dataStore.updateData {
            it.toBuilder()
                .setShowOfflineLast(showOfflineDeviceLast)
                .setDateLastWritten(System.currentTimeMillis())
                .build()
        }
    }

    suspend fun updateShowHiddenDevices(showHiddenDevices: Boolean) {
        Log.d(TAG, "updateShowHiddenDevices")
        dataStore.updateData {
            it.toBuilder()
                .setShowHiddenDevices(showHiddenDevices)
                .setDateLastWritten(System.currentTimeMillis())
                .build()
        }
    }

    suspend fun updateLastUpdateCheckDate(lastUpdateCheckDate: Long) {
        Log.d(TAG, "updateLastUpdateCheckDate")
        dataStore.updateData {
            it.toBuilder()
                .setLastUpdateCheckDate(lastUpdateCheckDate)
                .setDateLastWritten(System.currentTimeMillis())
                .build()
        }
    }

    suspend fun updateBleDiscovery(bleDiscovery: Boolean) {
        Log.d(TAG, "updateBleDiscovery")
        dataStore.updateData {
            it.toBuilder()
                .setBleDiscovery(bleDiscovery)
                .setDateLastWritten(java.lang.System.currentTimeMillis())
                .build()
        }
    }

    companion object {
        private const val TAG: String = "UserPreferencesRepo"
    }
}