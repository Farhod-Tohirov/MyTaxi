package uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.ChooseAddressViewModel
import uz.star.mytaxi.utils.extensions.formatLocationData
import uz.star.mytaxi.utils.helpers.AddressNotFoundException
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 19-August-2023, 13:38
 **/

@HiltViewModel
class ChooseAddressViewModelImpl @Inject constructor(
    private val useCase: AddressesUseCase
) : ChooseAddressViewModel() {

    override val currentLocationName = MutableLiveData<String>()

    override val selectedLocationName = MutableLiveData<String>()

    override val navigateConfirmOrderScreen = MutableLiveData<AddressData>()

    override var currentLocation: AddressData? = null

    override fun currentLocationChanged(location: LatLng) {
        tryLoadData {
            onIOContext {
                val locationData = location.formatLocationData()
                val address = useCase.searchSingleLocationAddress(locationData = locationData)
                currentLocation = address
                onUIContext {
                    currentLocationName.value = address.addressName
                }
            }
        }
    }

    override fun selectedLocationChanged(addressData: AddressData?) {
        tryLoadData(shouldShowLoader = false) {
            if (currentLocation == null)
                throw Exception("Choose current location first")

            selectedLocationName.value = addressData?.addressName

            navigateConfirmOrderScreen.value = addressData ?: throw AddressNotFoundException()
        }
    }
}