package uz.star.mytaxi.presentation.screens.main.choose_address.search_address

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.databinding.ScreenSearchAddressBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreenDialog
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel.SearchAddressViewModel
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel.impl.SearchAddressViewModelImpl
import uz.star.mytaxi.utils.extensions.hide
import uz.star.mytaxi.utils.extensions.show

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:58
 **/

@AndroidEntryPoint
class SearchAddressScreen : BaseScreenDialog<ScreenSearchAddressBinding>(R.layout.screen_search_address, ScreenSearchAddressBinding::bind) {

    override val viewModel: SearchAddressViewModel by viewModels<SearchAddressViewModelImpl>()

    override fun loadViews() {
        binding.currentLocationSection.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.currentLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_close, 0)
                binding.currentLocationMapButton.show()
            } else {
                binding.currentLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_red, 0, 0, 0)
                binding.currentLocationMapButton.hide()
            }
        }

        binding.selectedLocationSection.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.selectedLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_close, 0)
                binding.selectedLocationMapButton.show()
            } else {
                binding.selectedLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_blue, 0, 0, 0)
                binding.selectedLocationMapButton.hide()
            }
        }

        binding.selectedLocationMapButton.setOnClickListener { }
        binding.currentLocationMapButton.setOnClickListener { }
    }
}