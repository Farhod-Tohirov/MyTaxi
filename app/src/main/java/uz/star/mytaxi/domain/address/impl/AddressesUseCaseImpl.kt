package uz.star.mytaxi.domain.address.impl

import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.domain.address.AddressesUseCase
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:17
 **/

class AddressesUseCaseImpl @Inject constructor(

) : AddressesUseCase {

    override suspend fun getAddressNameByLocation(locationData: LocationData): String = "$locationData"

}