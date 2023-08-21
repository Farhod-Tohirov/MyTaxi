package uz.star.mytaxi.presentation.screens.main.choose_address.viewmodel

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 19-August-2023, 13:38
 **/

abstract class ChooseAddressViewModel : BaseViewModel() {

    abstract val currentLocationName: LiveData<String>

    abstract val currentLocation: AddressData?

    abstract val navigateConfirmOrderScreen: LiveData<AddressData>

    abstract fun currentLocationChanged(location: LatLng)

    abstract fun selectedLocationChanged(addressData: AddressData?)
}