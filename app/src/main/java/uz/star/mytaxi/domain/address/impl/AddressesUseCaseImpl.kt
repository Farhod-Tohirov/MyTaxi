package uz.star.mytaxi.domain.address.impl

import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.data.repository.search_address.SearchAddressRepository
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.utils.helpers.FakeData
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:17
 **/

class AddressesUseCaseImpl @Inject constructor(
    private val remoteRepository: SearchAddressRepository
) : AddressesUseCase {

    override suspend fun searchSingleLocationAddress(locationData: LocationData): AddressData {
        val address = remoteRepository.searchSingleLocationAddress(locationData = locationData)
        address.location = locationData
        return address
    }

    override suspend fun getAddressesByName(locationName: String, locationData: LocationData): List<AddressData> =
        remoteRepository.searchAddressByName(name = locationName, locationData = locationData, limit = 20)

    override suspend fun getFavouriteAddresses(): List<FavouriteAddressData> =
        remoteRepository.getFavouriteAddressList()

    override suspend fun getOrderTypes(): List<CarOrderTypeData> = FakeData.carOrderTypesList

    override suspend fun getPointsBetweenLocations(from: LocationData, to: LocationData): List<LocationData> {
        val polyLineData = remoteRepository.getPointsBetweenLocations(from = from, to = to)
        return decodePolyline(polyLineData.polyline)
    }

    private fun decodePolyline(encoded: String): List<LocationData> {
        val poly = ArrayList<LocationData>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LocationData((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }
}