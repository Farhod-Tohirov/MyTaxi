package uz.star.mytaxi.presentation.screens.main.choose_address.search_address

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.databinding.ScreenSearchAddressBinding
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel.SearchAddressViewModel
import uz.star.mytaxi.presentation.screens.main.choose_address.search_address.viewmodel.impl.SearchAddressViewModelImpl

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:58
 **/

@AndroidEntryPoint
class SearchAddressScreen : BaseScreen<ScreenSearchAddressBinding>(R.layout.screen_search_address, ScreenSearchAddressBinding::bind) {

    override val viewModel: SearchAddressViewModel by viewModels<SearchAddressViewModelImpl>()

    override fun loadViews() {

    }

}