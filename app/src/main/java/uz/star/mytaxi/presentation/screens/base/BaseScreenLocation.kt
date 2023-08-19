package uz.star.mytaxi.presentation.screens.base

import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresPermission
import androidx.viewbinding.ViewBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import uz.star.mytaxi.utils.extensions.checkPermissions
import uz.star.mytaxi.utils.extensions.showLog

/**
 * Created by Farhod Tohirov on 19-August-2023, 10:18
 **/

abstract class BaseScreenLocation<T : ViewBinding>(@LayoutRes layout: Int, vbFactory: (View) -> T) : BaseScreen<T>(layout, vbFactory) {

    abstract val locationCallback: LocationCallback

    /**
     * fields for getting user location.
     */
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(requireContext()) }

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10_000).apply {
        setMinUpdateDistanceMeters(5_000f)
        setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        setWaitForAccurateLocation(true)
    }.build()

    private val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    private val client: SettingsClient by lazy { LocationServices.getSettingsClient(requireContext()) }

    /**
     * Use a boolean, isRequestedOnGps, to track whether location updates are currently turned on.
     * In the activity's onResume() method, check whether location updates are currently active, and activate them if not:
     */
    private var isRequestedOnGps = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateValuesFromBundle(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    /**
     * requireActivity().checkPermissions checks given permissions. After granted all permissions works given block.
     */
    protected fun initLocation() {
        requireActivity().checkPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            try {
                checkLocation()
            } catch (_: Exception) {

            }
        }
    }

    @RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    /**
     * Use this method after granting permissions.
     */
    private fun checkLocation() {
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            isRequestedOnGps = false
            startLocationUpdates()
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    isRequestedOnGps = true
                    exception.startResolutionForResult(
                        requireActivity(),
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    showLog(sendEx.message.toString())
                }
            }
            showLog(exception.message.toString())
        }
    }

    @RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    /**
     * Use this method after granting permissions. For checking location on resume.
     */
    private fun checkWithoutFailure() {
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            isRequestedOnGps = false
            startLocationUpdates()
        }
    }

    @RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    /**
     * Use this method after granting permissions.
     */
    protected open fun startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (_: Exception) {
        }
    }

    @SuppressLint("MissingPermission")
    /**
     * isRequestedOnGps - given true value only in method checkLocation() and this method called after granting permissions.
     */
    override fun onResume() {
        super.onResume()
        try {
            if (isRequestedOnGps) checkWithoutFailure()
        } catch (_: Exception) {
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            stopLocationUpdates()
        } catch (_: Exception) {

        }
    }

    protected fun stopLocationUpdates() {
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        } catch (_: Exception) {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, isRequestedOnGps)
        super.onSaveInstanceState(outState)
    }

    private fun updateValuesFromBundle(savedInstanceState: Bundle?) {
        savedInstanceState ?: return
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            isRequestedOnGps = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY, false)
        }
    }

    companion object {
        const val REQUEST_CHECK_SETTINGS: Int = 99
        const val REQUESTING_LOCATION_UPDATES_KEY: String = "isRequestedOnGps"
    }
}