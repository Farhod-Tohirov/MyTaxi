package uz.star.mytaxi.data.entities.address

import com.google.gson.annotations.SerializedName
import uz.star.mytaxi.utils.helpers.network.DataMapper


/**
 * Created by Farhod Tohirov on 22-August-2023, 15:47
 **/

data class PolylineData(
    val polyline: String,
    val distance: Int
)

data class PolylineResponseData(
    @SerializedName("eta")
    var eta: Int? = 0,
    @SerializedName("eta_minute")
    var etaMinute: Int? = 0,
    @SerializedName("distance")
    var distance: Int? = 0,
    @SerializedName("polyline")
    var polyline: String? = ""
) : DataMapper<PolylineResponseData, PolylineData> {
    override fun PolylineResponseData.mapToDomain(): PolylineData =
        PolylineData(polyline = polyline ?: "", distance = distance ?: 0)
}