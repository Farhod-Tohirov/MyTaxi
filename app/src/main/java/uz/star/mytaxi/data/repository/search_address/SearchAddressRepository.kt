package uz.star.mytaxi.data.repository.search_address

import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.data.entities.address.LocationData

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:34
 **/

interface SearchAddressRepository {

    suspend fun searchSingleLocationAddress(locationData: LocationData): AddressData

    suspend fun searchAddressByName(name: String, locationData: LocationData, limit: Int): List<AddressData>

    suspend fun getFavouriteAddressList(): List<FavouriteAddressData>
}