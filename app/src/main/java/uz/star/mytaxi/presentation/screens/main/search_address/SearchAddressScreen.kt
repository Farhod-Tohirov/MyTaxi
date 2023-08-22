package uz.star.mytaxi.presentation.screens.main.search_address

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.FavouriteAddressData
import uz.star.mytaxi.databinding.ScreenSearchAddressBinding
import uz.star.mytaxi.presentation.adapters.seach_address.SearchAddressAdapter
import uz.star.mytaxi.presentation.adapters.seach_address.SearchAddressShimmerAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreenDialog
import uz.star.mytaxi.presentation.screens.main.search_address.viewmodel.SearchAddressViewModel
import uz.star.mytaxi.presentation.screens.main.search_address.viewmodel.impl.SearchAddressViewModelImpl
import uz.star.mytaxi.utils.extensions.*
import uz.star.mytaxi.utils.helpers.Const
import uz.star.mytaxi.utils.helpers.network.mapToDomain
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Farhod Tohirov on 19-August-2023, 14:58
 **/

@AndroidEntryPoint
class SearchAddressScreen : BaseScreenDialog<ScreenSearchAddressBinding>(R.layout.screen_search_address, ScreenSearchAddressBinding::bind) {

    override val viewModel: SearchAddressViewModel by viewModels<SearchAddressViewModelImpl>()

    override val recyclerViewIdList = listOf(R.id.resultList)

    private val args: SearchAddressScreenArgs by navArgs()

    private val searchAddressAdapter = SearchAddressAdapter()
    private val searchAddressShimmerAdapter = SearchAddressShimmerAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return makeBottomSheetFullScreenDialog()
    }

    @OptIn(FlowPreview::class)
    override fun loadViews() {
        binding.currentLocationSection.hint = args.selectedAddressName

        binding.resultList.adapter = ConcatAdapter(
            searchAddressAdapter,
            searchAddressShimmerAdapter
        )

        /*binding.currentLocationSection.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.currentLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_close, 0)
                binding.currentLocationMapButton.show()
            } else {
                binding.currentLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_red, 0, 0, 0)
                binding.currentLocationMapButton.hide()
            }
        }*/

        binding.selectedLocationSection.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.selectedLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_close, 0)
                binding.selectedLocationMapButton.show()
            } else {
                binding.selectedLocationSection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_blue, 0, 0, 0)
                binding.selectedLocationMapButton.hide()
            }
        }

        binding.selectedLocationSection
            .textChanges()
            .debounce(1.seconds)
            .onEach {
                viewModel.searchLocationByName(name = it.toString())
            }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        binding.currentLocationMapButton.setOnClickListener { }

        binding.selectedLocationMapButton.setOnClickListener {
            safeNavigate(SearchAddressScreenDirections.actionSearchAddressScreenToChooseAddressManualScreen())
        }

        setFragmentResultListener(Const.manualSelectedAddress) { _, bundle ->
            clearFragmentResultListener(Const.manualSelectedAddress)
            val manualSelectedAddress = bundle.parcelable<AddressData>(Const.manualSelectedAddress)
            manualSelectedAddress?.let {
                setBackStackDataAndClose(addressData = manualSelectedAddress)
            }
        }

        binding.bookmark.setOnClickListener { safeNavigate(SearchAddressScreenDirections.actionSearchAddressScreenToSelectedAddressScreen()) }

        setFragmentResultListener(Const.chooseFromFavouriteAddress) { _, bundle ->
            clearFragmentResultListener(Const.chooseFromFavouriteAddress)
            val favouriteAddressData = bundle.parcelable<FavouriteAddressData>(Const.chooseFromFavouriteAddress)
            favouriteAddressData?.let {
                setBackStackDataAndClose(addressData = favouriteAddressData.mapToDomain())
            }
        }

        searchAddressAdapter.setOnAddressClickListener {
            setBackStackDataAndClose(addressData = it)
        }
    }

    private fun setBackStackDataAndClose(addressData: AddressData) {
        setFragmentResult(Const.selectedAddress, result = bundleOf(Const.selectedAddress to addressData))
        closeScreen()
    }

    override fun loadObservers() {
        viewModel.foundAddressesList.observe(this, foundAddressesListObserver)
        viewModel.shimmerList.observe(this, shimmerListObserver)
    }

    private val shimmerListObserver = Observer<List<Char>> {
        searchAddressShimmerAdapter.submitList(it)
    }

    private val foundAddressesListObserver = Observer<List<AddressData>> {
        searchAddressAdapter.submitList(it)
    }
}