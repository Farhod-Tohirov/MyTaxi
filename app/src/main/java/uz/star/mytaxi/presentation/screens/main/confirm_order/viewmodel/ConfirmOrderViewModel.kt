package uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

abstract class ConfirmOrderViewModel : BaseViewModel() {

    abstract val orderTypesList: LiveData<List<CarOrderTypeData>>

    abstract fun getOrderTypes()

}