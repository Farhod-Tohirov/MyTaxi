package uz.star.mytaxi.utils.extensions

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import uz.star.mytaxi.utils.helpers.SingleBlock

/**
 * Created by Farhod Tohirov on 16-September-2021, 16-21
 **/

fun Fragment.setBackStackLiveData(title: String, bundle: Bundle, navController: NavController = findNavController()) {
    navController.previousBackStackEntry?.savedStateHandle?.set(title, bundle)
}

fun Fragment.getBackStackLiveData(title: String, navController: NavController = findNavController(), block: SingleBlock<Bundle>) {
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>(title)?.observe(this) {
        block(it)
        navController.currentBackStackEntry?.savedStateHandle?.remove<Bundle>(title)
    }
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
