package uz.star.mytaxi.utils.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment


/**
 * Created by Farhod Tohirov on 06-July-2023, 11:00
 **/

fun Fragment.changeStatusBarAppearance(isLight: Boolean) {
    val decor: View = requireActivity().window.decorView
    val wic = WindowInsetsControllerCompat(requireActivity().window, decor)
    wic.isAppearanceLightStatusBars = isLight
}

fun Fragment.changeStatusBarColor(@ColorRes color: Int) {
    requireActivity().window.statusBarColor = ResourcesCompat.getColor(resources, color, null)
    requireActivity().window.navigationBarColor = ResourcesCompat.getColor(resources, color, null)
}