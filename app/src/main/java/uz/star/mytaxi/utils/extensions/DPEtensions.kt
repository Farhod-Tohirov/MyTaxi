package uz.star.mytaxi.utils.extensions

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by Farhod Tohirov on 21-June-2022, 11:50
 **/

val Int.dp
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()

val Int.dpFloat
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
