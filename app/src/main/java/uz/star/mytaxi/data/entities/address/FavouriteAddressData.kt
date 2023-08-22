package uz.star.mytaxi.data.entities.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uz.star.mytaxi.utils.helpers.network.DataMapper

/**
 * Created by Farhod Tohirov on 21-August-2023, 18:09
 **/

@Parcelize
data class FavouriteAddressData(
    val id: Int,
    val addressName: String,
    val formattedAddressName: String,
    val icon: Int,
    val locationData: LocationData
) : Parcelable, DataMapper<FavouriteAddressData, AddressData> {
    override fun FavouriteAddressData.mapToDomain(): AddressData =
        AddressData(
            addressId = id,
            streetPoiId = 0,
            addressName = addressName,
            formattedAddressName = formattedAddressName,
            location = locationData
        )
}