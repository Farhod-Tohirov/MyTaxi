package uz.star.mytaxi.data.repository.remote.search_address

import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.data.entities.address.PolylineData

/**
 * Created by Farhod Tohirov on 21-August-2023, 14:34
 **/

interface SearchAddressRepository {

    suspend fun searchSingleLocationAddress(locationData: LocationData): AddressData

    suspend fun searchAddressByName(name: String, locationData: LocationData, limit: Int): List<AddressData>

    suspend fun getPointsBetweenLocations(from: LocationData, to: LocationData): PolylineData
}