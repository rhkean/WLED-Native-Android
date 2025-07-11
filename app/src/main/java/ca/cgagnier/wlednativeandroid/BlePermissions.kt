package ca.cgagnier.wlednativeandroid

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import javax.inject.Singleton

@Singleton
class BlePermissions() {
    lateinit var permissions: List<String>
    lateinit var rationale: String
    lateinit var deniedExplanation: String
    lateinit var activity: Activity
    lateinit var onPermissionsDenied: () -> Unit

    // confirm bluetooth permissions before running the scan, bc user may
    // deny them from appInfo, which will not set the preferences to false
    fun verifyPermissions(): Boolean {
        val permissionsGranted = permissions.all { permission ->
                    activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
        if(!permissionsGranted) onPermissionsDenied()
        return permissionsGranted
    }

    fun shouldShowRationale(): Boolean {
        return permissions.all { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }
    }

    fun installActivityContext(
        activityContext: Activity,
        disableBleScan: () -> Unit
    ) {
        onPermissionsDenied = disableBleScan
        activity = activityContext
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
            rationale = activity.getString(R.string.location_permissions_rationale)
            deniedExplanation = activity.getString(R.string.location_permissions_denied)
        } else {
            permissions = listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
            rationale = activity.getString(R.string.bluetooth_permissions_rationale)
            deniedExplanation = activity.getString(R.string.location_permissions_denied)
        }

    }
}