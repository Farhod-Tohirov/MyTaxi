package uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 22-August-2023, 12:02
 **/

abstract class StopPointsViewModel : BaseViewModel() {

    abstract val stopPointsList: LiveData<List<AddressData>>

    abstract val notifyItemsChanged: LiveData<Int>

    abstract fun pointDeleteClick(addressData: AddressData)
}