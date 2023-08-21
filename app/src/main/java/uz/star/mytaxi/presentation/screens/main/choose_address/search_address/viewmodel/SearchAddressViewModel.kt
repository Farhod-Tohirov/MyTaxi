package uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel

import androidx.lifecycle.LiveData
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.presentation.screens.base.BaseViewModel

/**
 * Created by Farhod Tohirov on 19-August-2023, 15:37
 **/

abstract class SearchAddressViewModel : BaseViewModel() {

    abstract val foundAddressesList: LiveData<List<AddressData>>

    abstract val shimmerList: LiveData<List<Char>>

    abstract fun searchLocationByName(name: String)
}