package uz.star.mytaxi.presentation.screens.main.confirm_order

import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.data.entities.address.AddressDataList
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.databinding.ScreenConfirmOrderBinding
import uz.star.mytaxi.presentation.adapters.confirm_order.CarOrderTypesAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.MainScreen
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.ConfirmOrderViewModel
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.impl.ConfirmOrderViewModelImpl
import uz.star.mytaxi.utils.extensions.bitmapDescriptorFromVector
import uz.star.mytaxi.utils.extensions.dpFloat
import uz.star.mytaxi.utils.extensions.parcelable
import uz.star.mytaxi.utils.helpers.Const

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

@AndroidEntryPoint
class ConfirmOrderScreen : BaseScreen<ScreenConfirmOrderBinding>(R.layout.screen_confirm_order, ScreenConfirmOrderBinding::bind) {

    override val viewModel: ConfirmOrderViewModel by viewModels<ConfirmOrderViewModelImpl>()

    override val recyclerViewIdList = listOf(R.id.orderTypeList)

    private val parentScreen: MainScreen by lazy { requireParentFragment().requireParentFragment() as MainScreen }

    private val carOrderTypesAdapter = CarOrderTypesAdapter()

    override fun loadViews() {
        binding.backButton.setOnClickListener { closeScreen() }
        binding.payment.setOnClickListener {}
        binding.schedule.setOnClickListener { }
        binding.wishes.setOnClickListener { }

        binding.orderTypeList.adapter = carOrderTypesAdapter

        carOrderTypesAdapter.setOnAddressClickListener(viewModel::carOrderTypeSelected)

        binding.addAddressButton.setOnClickListener {
            safeNavigate(ConfirmOrderScreenDirections.actionConfirmOrderScreenToAddStopPointScreen(viewModel.currentAddressData.value?.location))
        }

        setFragmentResultListener(Const.selectedAddress) { _, bundle ->
            val selectedAddressData = bundle.parcelable<AddressData>(Const.selectedAddress) ?: return@setFragmentResultListener
            viewModel.addStopPoint(selectedAddressData)
        }

        binding.selectedAddress.setOnClickListener {
            safeNavigate(
                ConfirmOrderScreenDirections.actionConfirmOrderScreenToStopPointsScreen(
                    addresses = AddressDataList(list = viewModel.selectedAddressesData.value ?: emptyList()),
                    currentLocation = viewModel.currentAddressData.value?.location
                )
            )
        }

        setFragmentResultListener(Const.stopPointsRemoved) { _, bundle ->
            val removedId = bundle.getInt(Const.stopPointsRemoved)
            viewModel.stopPointRemoved(removedId)
        }
    }

    override fun loadObservers() {
        viewModel.orderTypesList.observe(this, orderTypesListObserver)
        viewModel.currentAddressData.observe(viewLifecycleOwner, currentAddressDataObserver)
        viewModel.selectedAddressesData.observe(viewLifecycleOwner, selectedAddressesDataObserver)
        viewModel.pathBetweenLocations.observe(this, pathBetweenLocationsObserver)
    }

    private val currentAddressDataObserver = Observer<AddressData> { addressData ->
        binding.currentAddress.text = addressData.addressName
    }

    private val selectedAddressesDataObserver = Observer<List<AddressData>> { addressesList ->
        if (addressesList.size == 1) {
            binding.selectedAddress.text = addressesList.first().addressName
        } else {
            binding.selectedAddress.text = getString(R.string.address_count, addressesList.size)
        }
    }

    private val orderTypesListObserver = Observer<List<CarOrderTypeData>> {
        carOrderTypesAdapter.submitList(it)
    }

    private val pathBetweenLocationsObserver = Observer<List<LatLng>> {
        val startPoint = MarkerOptions().position(it.firstOrNull() ?: return@Observer)
        startPoint.icon(bitmapDescriptorFromVector(R.drawable.ic_location_red))
        parentScreen.addMarker(startPoint)

        val endPoint = MarkerOptions().position(it.last())
        endPoint.icon(bitmapDescriptorFromVector(R.drawable.ic_location_blue))
        parentScreen.addMarker(endPoint)


        val lineOptions = PolylineOptions()
        lineOptions.addAll(it)
        lineOptions.color(ResourcesCompat.getColor(resources, R.color.poly_line_color, null))
        lineOptions.width(3.dpFloat)
        lineOptions.geodesic(true)
        parentScreen.drawPolyline(lineOptions)
    }

    override fun onDestroyScreenUI() {
        clearFragmentResultListener(Const.stopPointsRemoved)
        clearFragmentResultListener(Const.selectedAddress)
        parentScreen.clearMap()
    }
}