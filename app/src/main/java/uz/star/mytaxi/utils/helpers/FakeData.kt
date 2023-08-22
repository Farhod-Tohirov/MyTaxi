package uz.star.mytaxi.utils.helpers

import uz.star.mytaxi.R
import uz.star.mytaxi.data.entities.confirm_order.CarOrderTypeData

/**
 * Created by Farhod Tohirov on 22-August-2023, 09:41
 **/

object FakeData {
    val carOrderTypesList = listOf(
        CarOrderTypeData(1, "14 900 cум", "Эконом", R.drawable.ic_car_type_economy),
        CarOrderTypeData(2, "16 200 cум", "Комфорт", R.drawable.ic_car_type_comfort),
        CarOrderTypeData(3, "12 900 cум", "Доставка", R.drawable.ic_car_type_delivery),
    )
}