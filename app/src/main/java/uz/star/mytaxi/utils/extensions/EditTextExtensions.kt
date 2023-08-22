package uz.star.mytaxi.utils.extensions

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.EditText
import uz.star.mytaxi.utils.helpers.EmptyBlock


/**
 * Created by Farhod Tohirov on 21-August-2023, 15:37
 **/

@SuppressLint("ClickableViewAccessibility")
fun EditText.setOnDrawableRightClickListener(callBack: EmptyBlock) {
    setOnTouchListener(OnTouchListener { _, event ->
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= right - compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                callBack()
                return@OnTouchListener true
            }
        }
        false
    })
}
