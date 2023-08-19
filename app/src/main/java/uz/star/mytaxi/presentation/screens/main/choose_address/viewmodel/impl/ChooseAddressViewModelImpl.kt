package uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.star.mytaxi.domain.address.AddressesUseCase
import uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel.ChooseAddressViewModel
import uz.star.mytaxi.utils.extensions.formatLocationData
import javax.inject.Inject

/**
 * Created by Farhod Tohirov on 19-August-2023, 13:38
 **/

@HiltViewModel
class ChooseAddressViewModelImpl @Inject constructor(
    private val useCase: AddressesUseCase
) : ChooseAddressViewModel() {

    override val currentLocationName = MutableLiveData<String>()

    override fun currentLocationChanged(location: LatLng) {
        tryLoadData {
            onIOContext {
                val locationData = location.formatLocationData()
                val addressName = useCase.getAddressNameByLocation(locationData = locationData)
                onUIContext {
                    currentLocationName.value = addressName
                }
            }
        }
    }
}