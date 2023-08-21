package uz.star.mytaxi.presentation.adapters.seach_address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.star.mytaxi.data.entities.address.SelectedAddressData
import uz.star.mytaxi.databinding.ItemSelectedAddressBinding
import uz.star.mytaxi.utils.helpers.SingleBlock

/**
 * Created by Farhod Tohirov on 21-August-2023, 16:05
 **/

class SelectedAddressAdapter : ListAdapter<SelectedAddressData, SelectedAddressAdapter.ViewHolder>(ItemAddressDiffUtilCallback) {

    private var addressClickListener: SingleBlock<SelectedAddressData>? = null

    inner class ViewHolder(private val binding: ItemSelectedAddressBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { addressClickListener?.invoke(getItem(bindingAdapterPosition)) }
        }

        fun bind() {
            val data = getItem(bindingAdapterPosition)
            binding.title.text = data.addressName
            binding.subtitle.text = data.formattedAddressName
            binding.icon.setBackgroundResource(data.icon)
        }

        fun onViewRecycled() {
        }
    }

    fun setOnAddressClickListener(f: SingleBlock<SelectedAddressData>) {
        addressClickListener = f
    }


    object ItemAddressDiffUtilCallback : DiffUtil.ItemCallback<SelectedAddressData>() {
        override fun areItemsTheSame(oldItem: SelectedAddressData, newItem: SelectedAddressData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SelectedAddressData, newItem: SelectedAddressData) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSelectedAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun onViewRecycled(holder: ViewHolder) = holder.onViewRecycled()
}