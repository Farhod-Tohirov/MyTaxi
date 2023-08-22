package uz.star.mytaxi.utils.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


/**
 * Created by Farhod Tohirov on 22-August-2023, 16:11
 **/

fun Fragment.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
    vectorDrawable?.setBounds(0, 0, 24.dp, 24.dp)
    val bitmap = Bitmap.createBitmap(24.dp, 24.dp, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}