package uz.star.mytaxi.presentation.adapters.stop_point

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.databinding.ItemStopPointBinding
import uz.star.mytaxi.utils.helpers.ItemMoveCallback
import uz.star.mytaxi.utils.helpers.SingleBlock
import java.util.*

/**
 * Created by Farhod Tohirov on 21-August-2023, 16:05
 **/

class StopPointsAdapter : ListAdapter<AddressData, StopPointsAdapter.ViewHolder>(ItemAddressDiffUtilCallback), ItemMoveCallback.ItemTouchHelperContract {

    private var addressClickListener: SingleBlock<AddressData>? = null
    private var data = mutableListOf<AddressData>()

    inner class ViewHolder(private val binding: ItemStopPointBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { addressClickListener?.invoke(getItem(bindingAdapterPosition)) }
        }

        fun bind() {
            val data = getItem(bindingAdapterPosition)
            binding.title.text = data.addressName
        }

        fun onViewRecycled() {
        }
    }

    fun setOnAddressClickListener(f: SingleBlock<AddressData>) {
        addressClickListener = f
    }

    fun submitData(list: List<AddressData>) {
        data = list.toMutableList()
        submitList(data)
    }

    object ItemAddressDiffUtilCallback : DiffUtil.ItemCallback<AddressData>() {
        override fun areItemsTheSame(oldItem: AddressData, newItem: AddressData) = oldItem.addressId == newItem.addressId
        override fun areContentsTheSame(oldItem: AddressData, newItem: AddressData) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemStopPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun onViewRecycled(holder: ViewHolder) = holder.onViewRecycled()

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(myViewHolder: ViewHolder?) {

    }

    override fun onRowClear(myViewHolder: ViewHolder?) {

    }
}