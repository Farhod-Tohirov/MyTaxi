package uz.star.mytaxi.data.repository.search_address.impl

import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.data.entities.address.SelectedAddressData
import uz.star.mytaxi.data.remote.AddressesApi
import uz.star.mytaxi.data.repository.search_address.SearchAddressRepository
import uz.star.mytaxi.utils.helpers.network.mapToDomain
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:35
 **/

class SearchAddressRepositoryImpl @Inject constructor(
    private val addressApi: AddressesApi
) : SearchAddressRepository {

    override suspend fun searchSingleLocationAddress(locationData: LocationData): AddressData =
        addressApi.searchSingleLocation(lat = locationData.lat, long = locationData.long).data.data.mapToDomain()

    override suspend fun searchAddressByName(name: String, locationData: LocationData, limit: Int): List<AddressData> =
        addressApi.searchAddressByName(lat = locationData.lat, lng = locationData.long, limit = limit, name = name).data.candidates.map { it.mapToDomain() }

    override suspend fun getSelectedAddresses(): List<SelectedAddressData> {
        return listOf(
            SelectedAddressData(0, "Гостиница Россия", "улица  Мирабад 29, Ташкент ", R.drawable.ic_home, LocationData(0.0, 0.0)),
            SelectedAddressData(1, "Гостиница Россия", "улица  Мирабад 29, Ташкент ", R.drawable.ic_suitcase, LocationData(0.0, 0.0)),
            SelectedAddressData(2, "Гостиница Россия", "улица  Мирабад 29, Ташкент ", R.drawable.ic_bookmark_grey, LocationData(0.0, 0.0)),
        )
    }
}