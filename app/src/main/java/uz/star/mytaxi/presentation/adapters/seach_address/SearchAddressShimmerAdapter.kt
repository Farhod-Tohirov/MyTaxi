package uz.star.mytaxi.presentation.adapters.seach_address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.star.mytaxi.databinding.ItemSearchAddressShimmerBinding

/**
 * Created by Farhod Tohirov on 21-August-2023, 16:05
 **/

class SearchAddressShimmerAdapter : ListAdapter<Char, SearchAddressShimmerAdapter.ViewHolder>(ItemAddressDiffUtilCallback) {

    inner class ViewHolder(private val binding: ItemSearchAddressShimmerBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.startShimmer()
        }

        fun bind() {
            val data = getItem(bindingAdapterPosition)
        }

        fun onViewRecycled() {
        }
    }

    object ItemAddressDiffUtilCallback : DiffUtil.ItemCallback<Char>() {
        override fun areItemsTheSame(oldItem: Char, newItem: Char) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Char, newItem: Char) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSearchAddressShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun onViewRecycled(holder: ViewHolder) = holder.onViewRecycled()
}