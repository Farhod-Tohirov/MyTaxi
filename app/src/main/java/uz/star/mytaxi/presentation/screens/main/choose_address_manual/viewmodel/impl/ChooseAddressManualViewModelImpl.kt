package uz.star.mytaxi.presentation.screens.main.choose_address_manual.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.choose_address_manual.viewmodel.ChooseAddressManualViewModel
import uz.star.mytaxi.utils.extensions.formatLocationData
import uz.star.mytaxi.utils.helpers.AddressNotFoundException
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 22-August-2023, 10:24
 **/

@HiltViewModel
class ChooseAddressManualViewModelImpl @Inject constructor(
    private val addressesUseCase: AddressesUseCase
) : ChooseAddressManualViewModel() {

    override var selectedAddress: AddressData? = null
    override val selectedLocationName = MutableLiveData<String>()
    override val selectAddressConfirmed = MutableLiveData<AddressData>()

    override fun selectedLocationChanged(point: LatLng) {
        tryLoadData {
            onIOContext {
                val address = addressesUseCase.searchSingleLocationAddress(locationData = point.formatLocationData())
                selectedAddress = address
                onUIContext {
                    selectedLocationName.value = address.addressName
                }
            }
        }
    }

    override fun confirmButtonClick() {
        tryLoadData(shouldShowLoader = false) {
            if (selectedAddress == null)
                throw AddressNotFoundException()
            selectAddressConfirmed.value = selectedAddress
        }
    }
}