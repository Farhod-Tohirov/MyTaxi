package uz.star.mytaxi.data.repository.remote.search_address.impl

import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.data.entities.address.PolylineData
import uz.star.mytaxi.data.remote.AddressesApi
import uz.star.mytaxi.data.repository.remote.search_address.SearchAddressRepository
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
        addressApi.searchAddressByName(lat = locationData.lat, lng = locationData.long, limit = limit, name = name).data.candidates?.map { it.mapToDomain() } ?: emptyList()

    override suspend fun getPointsBetweenLocations(from: LocationData, to: LocationData): PolylineData =
        addressApi.getRouteData(points = "${from.lat},${from.long}|${to.lat},${to.long}").data.mapToDomain()
}