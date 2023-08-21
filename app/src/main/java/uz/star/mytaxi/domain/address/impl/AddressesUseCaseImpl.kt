package uz.star.mytaxi.domain.address.impl

import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.LocationData
import uz.star.mytaxi.data.entities.address.SelectedAddressData
import uz.star.mytaxi.data.repository.search_address.SearchAddressRepository
import uz.star.mytaxi.domain.address.AddressesUseCase
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

    override suspend fun getSelectedAddresses(): List<SelectedAddressData> =
        remoteRepository.getSelectedAddresses()
}