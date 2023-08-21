package uz.star.mytaxi.presentation.screens.main.choose_address.selected_address

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.SelectedAddressData
import uz.star.mytaxi.databinding.ScreenSelectedAddressBinding
import uz.star.mytaxi.presentation.adapters.seach_address.SelectedAddressAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreenDialog
import uz.star.mytaxi.presentation.screens.main.choose_address.selected_address.viewmodel.SelectedAddressViewModel
import uz.star.mytaxi.presentation.screens.main.choose_address.selected_address.viewmodel.impl.SelectedAddressViewModelImpl
import uz.star.mytaxi.utils.extensions.setBackStackLiveData
import uz.star.mytaxi.utils.helpers.Const

/**
 * Created by Farhod Tohirov on 21-August-2023, 17:56
 **/

@AndroidEntryPoint
class SelectedAddressScreen : BaseScreenDialog<ScreenSelectedAddressBinding>(R.layout.screen_selected_address, ScreenSelectedAddressBinding::bind) {

    override val viewModel: SelectedAddressViewModel by viewModels<SelectedAddressViewModelImpl>()

    override val recyclerViewIdList = listOf(R.id.resultList)

    private val selectedAddressAdapter = SelectedAddressAdapter()

    override fun loadViews() {
        binding.resultList.adapter = selectedAddressAdapter

        selectedAddressAdapter.setOnAddressClickListener {
            setBackStackLiveData(
                title = SelectedAddressScreen::class.java.name,
                bundle = bundleOf(Const.selectedAddress to it)
            )
            closeScreen()
        }
    }

    override fun loadObservers() {
        viewModel.selectedAddressList.observe(this, selectedAddressListObserver)
    }

    private val selectedAddressListObserver = Observer<List<SelectedAddressData>> {
        selectedAddressAdapter.submitList(it)
    }
}