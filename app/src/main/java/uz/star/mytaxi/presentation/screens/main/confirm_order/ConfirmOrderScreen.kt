package uz.star.mytaxi.presentation.screens.main.confirm_order

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.databinding.ScreenConfirmOrderBinding
import uz.star.mytaxi.presentation.adapters.confirm_order.CarOrderTypesAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreen
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.ConfirmOrderViewModel
import uz.star.mytaxi.presentation.screens.main.confirm_order.viewmodel.impl.ConfirmOrderViewModelImpl

/**
 * Created by Farhod Tohirov on 21-August-2023, 19:18
 **/

@AndroidEntryPoint
class ConfirmOrderScreen : BaseScreen<ScreenConfirmOrderBinding>(R.layout.screen_confirm_order, ScreenConfirmOrderBinding::bind) {

    override val viewModel: ConfirmOrderViewModel by viewModels<ConfirmOrderViewModelImpl>()

    override val recyclerViewIdList = listOf(R.id.orderTypeList)

    private val carOrderTypesAdapter = CarOrderTypesAdapter()

    override fun loadViews() {
        binding.backButton.setOnClickListener { closeScreen() }
        binding.payment.setOnClickListener {}
        binding.schedule.setOnClickListener { }
        binding.wishes.setOnClickListener { }

        binding.orderTypeList.adapter = carOrderTypesAdapter
    }

    override fun loadObservers() {
        viewModel.orderTypesList.observe(this, orderTypesListObserver)
    }

    private val orderTypesListObserver = Observer<List<CarOrderTypeData>> {
        carOrderTypesAdapter.submitList(it)
    }
}