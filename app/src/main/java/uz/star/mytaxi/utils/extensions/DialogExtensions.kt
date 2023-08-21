package uz.star.mytaxi.utils.extensions

import android.app.Dialog
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Farhod Tohirov on 21-August-2023, 18:43
 **/

fun BottomSheetDialogFragment.makeBottomSheetFullScreenDialog(): Dialog {
    val dialog = BottomSheetDialog(requireContext(), theme)
    dialog.setOnShowListener {
        val bottomSheetDialog = it as BottomSheetDialog
        val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        parentLayout?.let {
            val behaviour = BottomSheetBehavior.from(parentLayout)
            setupFullHeight(parentLayout)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottomSheetDialog.behavior.skipCollapsed = true
    }
    return dialog
}


private fun setupFullHeight(bottomSheet: View) {
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    bottomSheet.layoutParams = layoutParams
}
