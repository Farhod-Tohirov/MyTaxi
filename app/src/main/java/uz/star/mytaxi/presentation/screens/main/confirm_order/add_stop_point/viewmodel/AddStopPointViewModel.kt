package uz.star.mytaxi.presentation.screens.main.confirm_order.add_stop_point.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 22-August-2023, 11:35
 **/

abstract class AddStopPointViewModel : BaseViewModel() {

    abstract val foundAddressesList: LiveData<List<AddressData>>

    abstract val shimmerList: LiveData<List<Char>>

    abstract fun searchLocationByName(name: String)
}