package uz.star.mytaxi.data.entities.address

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import uz.star.mytaxi.utils.helpers.network.DataMapper

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:19
 **/

@Parcelize
data class LocationData(
    val lat: Double,
    val long: Double
) : Parcelable


data class LocationResponseData(
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lng")
    var lng: Double?
) : DataMapper<LocationResponseData, LocationData> {
    override fun LocationResponseData.mapToDomain(): LocationData =
        LocationData(lat = lat ?: 0.0, long = lng ?: 0.0)
}