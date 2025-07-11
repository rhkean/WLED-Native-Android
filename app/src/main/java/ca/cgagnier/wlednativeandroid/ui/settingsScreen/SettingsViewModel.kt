package ca.cgagnier.wlednativeandroid.ui.settingsScreen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.cgagnier.wlednativeandroid.BlePermissions
import ca.cgagnier.wlednativeandroid.repository.ThemeSettings
import ca.cgagnier.wlednativeandroid.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: UserPreferencesRepository,
    val blePermissions: BlePermissions
) : ViewModel() {

    private val theme = preferencesRepository.themeMode
    private val autoDiscovery = preferencesRepository.autoDiscovery
    private val scanForBleDevices = preferencesRepository.scanForBleDevices
    private val showOfflineDevicesLast = preferencesRepository.showOfflineDevicesLast
    private val showHiddenDevices = preferencesRepository.showHiddenDevices
    private val arePermissionsDenied = preferencesRepository.arePermissionsDenied

    val settingsState = combine(
        autoDiscovery,
        showOfflineDevicesLast,
        showHiddenDevices,
        scanForBleDevices,
        arePermissionsDenied,
        theme,
    ) { values -> SettingsState(
            isAutoDiscoveryEnabled = values[0] as Boolean,
            showOfflineLast = values[1] as Boolean,
            showHiddenDevices = values[2] as Boolean,
            scanForBleDevices = values[3] as Boolean,
            arePermissionsDenied = values[4] as Boolean,
            theme = values[5] as ThemeSettings,
        )
    }.stateIn(viewModelScope, WhileSubscribed(5000), SettingsState())

    fun setAutoDiscover(enabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        preferencesRepository.updateAutoDiscovery(enabled)
    }
    fun setScanForBleDevices(enabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        preferencesRepository.updateScanForBleDevices(enabled)
    }
    fun setShowOfflineDevicesLast(enabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        preferencesRepository.updateShowOfflineDeviceLast(enabled)
    }
    fun setShowHiddenDevices(enabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        preferencesRepository.updateShowHiddenDevices(enabled)
    }
    fun setTheme(theme: ThemeSettings) = viewModelScope.launch(Dispatchers.IO) {
        preferencesRepository.updateThemeMode(theme)
    }
    fun setArePermissionsDenied(denied: Boolean) = viewModelScope.launch(Dispatchers.IO) {
      preferencesRepository.updateArePermissionsDenied(denied)
    }
}

@Stable
data class SettingsState(
    val isAutoDiscoveryEnabled : Boolean = true,
    val scanForBleDevices : Boolean = false,
    val showOfflineLast : Boolean = true,
    val showHiddenDevices : Boolean = false,
    val theme: ThemeSettings = ThemeSettings.UNRECOGNIZED,
    val arePermissionsDenied: Boolean = false,
)
