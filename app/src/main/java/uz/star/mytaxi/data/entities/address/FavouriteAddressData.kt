package uz.star.mytaxi.data.entities.address

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import uz.star.mytaxi.utils.helpers.network.DataEntityConverter
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
) : Parcelable, DataMapper<FavouriteAddressData, AddressData>, DataEntityConverter<FavouriteAddressData, FavouriteAddressEntityData> {
    override fun FavouriteAddressData.mapToDomain(): AddressData =
        AddressData(
            addressId = id,
            streetPoiId = 0,
            addressName = addressName,
            formattedAddressName = formattedAddressName,
            location = locationData
        )

    override fun FavouriteAddressData.mapToEntity(): FavouriteAddressEntityData =
        FavouriteAddressEntityData(
            id = id,
            addressName = addressName,
            formattedAddressName = formattedAddressName,
            icon = icon,
            latitude = locationData.lat,
            longitude = locationData.long
        )
}

@Entity
data class FavouriteAddressEntityData(
    @PrimaryKey
    val id: Int,
    val addressName: String,
    val formattedAddressName: String,
    val icon: Int,
    val latitude: Double,
    val longitude: Double
) : DataMapper<FavouriteAddressEntityData, FavouriteAddressData> {
    override fun FavouriteAddressEntityData.mapToDomain(): FavouriteAddressData =
        FavouriteAddressData(
            id = id,
            addressName = addressName,
            formattedAddressName = formattedAddressName,
            icon = icon,
            locationData = LocationData(lat = latitude, long = longitude)
        )
}