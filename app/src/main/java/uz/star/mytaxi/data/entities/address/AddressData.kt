package uz.star.mytaxi.data.entities.address

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import uz.star.mytaxi.utils.helpers.network.DataMapper
import uz.star.mytaxi.utils.helpers.network.mapToDomain


/**
 * Created by Farhod Tohirov on 21-August-2023, 14:42
 **/

@Parcelize
data class AddressData(
    val addressId: Int?,
    val streetPoiId: Int,
    val addressName: String,
    val formattedAddressName: String,
    var location: LocationData?
) : Parcelable

@Parcelize
data class AddressDataList(
    val list: List<AddressData>
) : Parcelable

data class AddressListResponseData(
    @SerializedName("candidates")
    val candidates: List<AddressResponseData>?
)

data class AddressResponseData(
    @SerializedName("address")
    var address: String?,
    @SerializedName("formatted_address")
    var formattedAddress: String?,
    @SerializedName("address_id")
    var addressId: Int?,
    @SerializedName("street_poi_id")
    var streetPoiId: Int?,
    @SerializedName("distance")
    var distance: String?,
    @SerializedName("location")
    var location: LocationResponseData?,
    @SerializedName("lang")
    var lang: String?
) : DataMapper<AddressResponseData, AddressData> {
    override fun AddressResponseData.mapToDomain(): AddressData =
        AddressData(
            addressId = addressId,
            streetPoiId = streetPoiId ?: 0,
            addressName = address ?: formattedAddress ?: "",
            formattedAddressName = formattedAddress ?: "",
            location = location?.mapToDomain() ?: LocationData(0.0, 0.0)
        )
}

data class SingleAddressObjectResponseData(
    @SerializedName("object")
    val data: SingleAddressResponseData
)

data class SingleAddressResponseData(
    @SerializedName("address")
    var address: String?,
    @SerializedName("street")
    var street: String?,
    @SerializedName("district")
    var district: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("region")
    var region: String?,
    @SerializedName("country")
    var country: String?,
) : DataMapper<SingleAddressResponseData, AddressData> {
    override fun SingleAddressResponseData.mapToDomain(): AddressData =
        AddressData(
            addressId = 0,
            streetPoiId = 0,
            addressName = "$street $address",
            formattedAddressName = "$district $city, $region, $country",
            location = null
        )
}