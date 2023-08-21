package uz.star.mytaxi.data.entities.confirm_order

/**
 * Created by Farhod Tohirov on 21-August-2023, 20:55
 **/

data class CarOrderTypeData(
    val id: Int,
    val price: String,
    val typeName: String,
    val carImage: Int,
    var isSelected: Boolean = false
)
