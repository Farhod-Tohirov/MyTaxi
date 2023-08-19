package uz.star.mytaxi.domain.address

import uz.star.mytaxi.data.entities.address.LocationData

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:17
 **/

interface AddressesUseCase {
    suspend fun getAddressNameByLocation(locationData: LocationData): String
}