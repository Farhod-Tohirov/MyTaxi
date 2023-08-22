package uz.star.mytaxi.domain.address

import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:17
 **/

interface AddressesUseCase {

    suspend fun searchSingleLocationAddress(locationData: LocationData): AddressData

    suspend fun getAddressesByName(locationName: String, locationData: LocationData): List<AddressData>

    suspend fun getFavouriteAddresses(): List<FavouriteAddressData>

    suspend fun getOrderTypes(): List<CarOrderTypeData>

    suspend fun getPointsBetweenLocations(from: LocationData, to: LocationData): List<LocationData>
}