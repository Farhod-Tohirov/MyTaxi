package uz.star.mytaxi.presentation.screens.main.choose_address.selected_address.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.data.entities.address.SelectedAddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 21-August-2023, 18:07
 **/

abstract class SelectedAddressViewModel : BaseViewModel() {

    abstract val selectedAddressList: LiveData<List<SelectedAddressData>>

    abstract fun getSelectedAddresses()
}