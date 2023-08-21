package uz.star.mytaxi.presentation.adapters.seach_address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.star.mytaxi.data.entities.address.AddressData
import uz.star.mytaxi.databinding.ItemSearchAddressBinding
import uz.star.mytaxi.utils.helpers.SingleBlock

/**
 * Created by Farhod Tohirov on 21-August-2023, 16:05
 **/

class SearchAddressAdapter : ListAdapter<AddressData, SearchAddressAdapter.ViewHolder>(ItemAddressDiffUtilCallback) {

    private var addressClickListener: SingleBlock<AddressData>? = null

    inner class ViewHolder(private val binding: ItemSearchAddressBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { addressClickListener?.invoke(getItem(bindingAdapterPosition)) }
        }

        fun bind() {
            val data = getItem(bindingAdapterPosition)
            binding.title.text = data.addressName
            binding.subtitle.text = data.formattedAddressName
        }

        fun onViewRecycled() {
        }
    }

    fun setOnAddressClickListener(f: SingleBlock<AddressData>) {
        addressClickListener = f
    }


    object ItemAddressDiffUtilCallback : DiffUtil.ItemCallback<AddressData>() {
        override fun areItemsTheSame(oldItem: AddressData, newItem: AddressData) = oldItem.addressId == newItem.addressId
        override fun areContentsTheSame(oldItem: AddressData, newItem: AddressData) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSearchAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun onViewRecycled(holder: ViewHolder) = holder.onViewRecycled()
}