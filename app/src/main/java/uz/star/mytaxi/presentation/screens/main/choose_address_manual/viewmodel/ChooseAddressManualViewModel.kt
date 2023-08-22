package uz.star.mytaxi.presentation.screens.main.choose_address_manual.viewmodel

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 22-August-2023, 10:23
 **/

abstract class ChooseAddressManualViewModel : BaseViewModel() {

    abstract val selectedAddress: AddressData?

    abstract val selectedLocationName: LiveData<String>

    abstract val selectAddressConfirmed: LiveData<AddressData>

    abstract fun selectedLocationChanged(point: LatLng)

    abstract fun confirmButtonClick()
}