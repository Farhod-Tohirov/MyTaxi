package uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.databinding.ScreenStopPointsBinding
import uz.star.mytaxi.presentation.adapters.stop_point.StopPointsAdapter
import uz.star.mytaxi.presentation.screens.base.BaseScreenDialog
import uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points.viewmodel.StopPointsViewModel
import uz.star.mytaxi.presentation.screens.main.confirm_order.stop_points.viewmodel.impl.StopPointsViewModelImpl
import uz.star.mytaxi.utils.helpers.Const
import uz.star.mytaxi.utils.helpers.ItemMoveCallback

/**
 * Created by Farhod Tohirov on 22-August-2023, 12:01
 **/

@AndroidEntryPoint
class StopPointsScreen : BaseScreenDialog<ScreenStopPointsBinding>(R.layout.screen_stop_points, ScreenStopPointsBinding::bind) {

    override val viewModel: StopPointsViewModel by viewModels<StopPointsViewModelImpl>()

    private val args: StopPointsScreenArgs by navArgs()

    override val recyclerViewIdList = listOf(R.id.stopPointsList)

    private val stopPointsAdapter = StopPointsAdapter()

    override fun loadViews() {
        val callback = ItemMoveCallback(stopPointsAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.stopPointsList)

        binding.stopPointsList.adapter = stopPointsAdapter

        stopPointsAdapter.setOnDeleteButtonClickListener(viewModel::pointDeleteClick)

        binding.addPointButton.setOnClickListener {
            safeNavigate(StopPointsScreenDirections.actionStopPointsScreenToAddStopPointScreen(currentLocation = args.currentLocation))
        }
    }

    override fun loadObservers() {
        viewModel.stopPointsList.observe(this, stopPointsListObserver)
        viewModel.notifyItemsChanged.observe(this, notifyItemsChangesObserver)
    }

    private val notifyItemsChangesObserver = Observer<Int> {
        setFragmentResult(Const.stopPointsRemoved, bundleOf(Const.stopPointsRemoved to it))
    }

    private val stopPointsListObserver = Observer<List<AddressData>> {
        stopPointsAdapter.submitData(it)
    }
}