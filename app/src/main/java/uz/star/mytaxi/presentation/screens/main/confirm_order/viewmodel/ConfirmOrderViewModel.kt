package uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

abstract class ConfirmOrderViewModel : BaseViewModel() {

    abstract val currentAddressData: LiveData<AddressData>

    abstract val selectedAddressesData: LiveData<List<AddressData>>

    abstract val orderTypesList: LiveData<List<CarOrderTypeData>>

    abstract val pathBetweenLocations: LiveData<List<LatLng>>

    abstract fun loadChosenAddresses()

    abstract fun getOrderTypes()

    abstract fun loadLocationPoints()

    abstract fun carOrderTypeSelected(cardOrderTypeData: CarOrderTypeData)

    abstract fun addStopPoint(addressData: AddressData)

    abstract fun stopPointRemoved(removedId: Int)
}