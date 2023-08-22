package uz.star.mytaxi.presentation.screens.main

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.databinding.ScreenMainBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreenLocation
import uz.star.mytaxi.presentation.screens.main.viewmodel.MainViewModel
import uz.star.mytaxi.presentation.screens.main.viewmodel.impl.MainViewModelImpl
import uz.star.mytaxi.utils.extensions.*
import uz.star.mytaxi.utils.helpers.Const
import uz.star.mytaxi.utils.helpers.SingleBlock

/**
 * Created by Farhod Tohirov on 19-August-2023, 09:51
 **/

@AndroidEntryPoint
class MainScreen : BaseScreenLocation<ScreenMainBinding>(R.layout.screen_main, ScreenMainBinding::bind) {

    override val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    private lateinit var googleMap: GoogleMap

    private var cameraCenterChangeListener: SingleBlock<LatLng>? = null

    override fun loadViews() {
        changeStatusBarAppearance(isLight = false)
        changeStatusBarColor(R.color.black_text_color)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReadyCallback)
    }

    private val onMapReadyCallback = OnMapReadyCallback {
        googleMap = it

        googleMap.setOnCameraMoveStartedListener(cameraMoveStartedListener)
        googleMap.setOnCameraIdleListener(cameraMoveIdleListener)

        googleMap.navigate(LatLng(Const.TASHKENT_LAT, Const.TASHKENT_LONG), 11f)

        initLocation()
    }

    override val locationCallback = object : LocationCallback() {
        @SuppressLint("MissingPermission")
        override fun onLocationResult(locationResult: LocationResult) {
            val currentLocation = locationResult.getLatLang()
            navigateUserLocation(currentLocation)
            stopLocationUpdates()

            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = false
        }
    }

    fun navigateUserLocation(point: LatLng? = null) {
        if (!this::googleMap.isInitialized) return
        if (point == null) {
            checkPermissionAndGetLastLocation {
                googleMap.navigate(it.getLatLong())
            }
        } else {
            googleMap.navigate(point)
        }
    }

    fun setOnCameraIdleListener(f: SingleBlock<LatLng>) {
        cameraCenterChangeListener = f
    }

    private val cameraMoveStartedListener = OnCameraMoveStartedListener { reason ->

    }

    private val cameraMoveIdleListener = OnCameraIdleListener {
        val center = googleMap.cameraPosition.target
        if (center.latitude > 0 && center.longitude > 0)
            cameraCenterChangeListener?.invoke(center)
    }

    fun getMapCenterPosition(): LatLng = googleMap.cameraPosition.target

    private var polyLine: Polyline? = null
    fun drawPolyline(lineOptions: PolylineOptions) {
        polyLine = googleMap.addPolyline(lineOptions)
    }

    private var marker = mutableListOf<Marker>()
    fun addMarker(markerOptions: MarkerOptions) {
        marker.add(googleMap.addMarker(markerOptions) ?: return)
    }

    fun clearMap() {
        polyLine?.remove()
        marker.forEach { it.remove() }
    }

    override fun onDestroyScreenUI() {
        googleMap.setOnCameraMoveStartedListener(null)
        googleMap.setOnCameraIdleListener(null)
        cameraCenterChangeListener = null
    }
}