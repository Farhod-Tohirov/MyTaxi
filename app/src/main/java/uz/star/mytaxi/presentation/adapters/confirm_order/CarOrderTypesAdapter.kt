package uz.star.mytaxi.presentation.adapters.confirm_order

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData
import uz.star.mytaxi.databinding.ItemChooseCarTypeBinding
import uz.star.mytaxi.utils.helpers.SingleBlock

/**
 * Created by Farhod Tohirov on 21-August-2023, 16:05
 **/

class CarOrderTypesAdapter : ListAdapter<CarOrderTypeData, CarOrderTypesAdapter.ViewHolder>(ItemCarOrderTypeDiffUtilCallback) {

    private var orderTypeClickListener: SingleBlock<CarOrderTypeData>? = null

    inner class ViewHolder(private val binding: ItemChooseCarTypeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { orderTypeClickListener?.invoke(getItem(bindingAdapterPosition)) }
        }

        fun bind() {
            val data = getItem(bindingAdapterPosition)
            binding.image.setImageResource(data.carImage)
            binding.title.text = data.typeName
            binding.price.text = data.price
            if (data.isSelected) {
                binding.root.setBackgroundResource(R.drawable.bg_selected_car)
            } else {
                binding.root.setBackgroundColor(Color.WHITE)
            }
        }

        fun onViewRecycled() {
        }
    }

    fun setOnAddressClickListener(f: SingleBlock<CarOrderTypeData>) {
        orderTypeClickListener = f
    }


    object ItemCarOrderTypeDiffUtilCallback : DiffUtil.ItemCallback<CarOrderTypeData>() {
        override fun areItemsTheSame(oldItem: CarOrderTypeData, newItem: CarOrderTypeData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CarOrderTypeData, newItem: CarOrderTypeData) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemChooseCarTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun onViewRecycled(holder: ViewHolder) = holder.onViewRecycled()
}