package uz.star.mytaxi.utils.extensions

import android.location.Location
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import uz.star.mytaxi.data.entities.address.LocationData

/**
 * Created by Farhod Tohirov on 23-Jun-2021
 **/

fun GoogleMap.navigate(point: LatLng, zoom: Float = 16f) {
    this.animateCamera(CameraUpdateFactory.newLatLngZoom(point, zoom))
}

fun GoogleMap.zoomIn() {
    this.animateCamera(CameraUpdateFactory.zoomIn())
}

fun GoogleMap.zoomOut() {
    this.animateCamera(CameraUpdateFactory.zoomOut())
}

fun LocationResult.getLatLang(): LatLng? {
    val location = this.locations.lastOrNull() ?: return null
    val lat = location.latitude
    val long = location.longitude
    return LatLng(lat, long)
}

fun Location.getLatLong(): LatLng {
    val lat = this.latitude
    val long = this.longitude
    return LatLng(lat, long)
}

fun LatLng.formatLocationData(): LocationData =
    LocationData(lat = latitude, long = longitude)