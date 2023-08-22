package uz.star.mytaxi.presentation.screens.main.favourite_address

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.databinding.ScreenSelectedAddressBinding
import uz.star.mytaxi.presentation.adapters.seach_address.SelectedAddressAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreenDialog
import uz.star.mytaxi.presentation.screens.main.favourite_address.viewmodel.FavouriteAddressViewModel
import uz.star.mytaxi.presentation.screens.main.favourite_address.viewmodel.impl.FavouriteAddressViewModelImpl
import uz.star.mytaxi.utils.helpers.Const

/**
 * Created by Farhod Tohirov on 21-August-2023, 17:56
 **/

@AndroidEntryPoint
class FavouriteAddressScreen : BaseScreenDialog<ScreenSelectedAddressBinding>(R.layout.screen_selected_address, ScreenSelectedAddressBinding::bind) {

    override val viewModel: FavouriteAddressViewModel by viewModels<FavouriteAddressViewModelImpl>()

    override val recyclerViewIdList = listOf(R.id.resultList)

    private val selectedAddressAdapter = SelectedAddressAdapter()

    override fun loadViews() {
        binding.resultList.adapter = selectedAddressAdapter

        selectedAddressAdapter.setOnAddressClickListener {
            setFragmentResult(Const.chooseFromFavouriteAddress, result = bundleOf(Const.chooseFromFavouriteAddress to it))
            closeScreen()
        }
    }

    override fun loadObservers() {
        viewModel.favouriteAddressList.observe(this, selectedAddressListObserver)
    }

    private val selectedAddressListObserver = Observer<List<FavouriteAddressData>> {
        selectedAddressAdapter.submitList(it)
    }
}