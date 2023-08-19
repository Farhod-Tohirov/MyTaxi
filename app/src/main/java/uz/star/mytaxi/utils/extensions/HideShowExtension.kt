package uz.star.mytaxi.utils.extensions

import android.view.View

/**
 * Created by Farhod Tohirov on 13-Feb-21
 **/

fun View.hide() {
    try {
        this.visibility = View.GONE
    } catch (_: Exception) {
    }
}

fun View.show() {
    try {
        this.visibility = View.VISIBLE
    } catch (_: Exception) {
    }
}

fun View.invisible() {
    try {
        this.visibility = View.INVISIBLE
    } catch (_: Exception) {
    }
}

fun View.hideWithAnimation(duration: Long = 200) {
    try {
        this.animate().alpha(0f).setDuration(duration).start()
    } catch (_: Exception) {
    }
}

fun View.showWithAnimation(duration: Long = 100) {
    try {
        this.animate().alpha(1f).setDuration(duration).start()
    } catch (_: Exception) {
    }
}

fun View.visible(state: Boolean) {
    if (state) this.show() else this.hide()
}


fun View.rotateDown(duration: Long = 300) {
    animate().rotation(0f).setDuration(duration).start()
}

fun View.rotateUp(duration: Long = 300, value: Float = 90f) {
    animate().rotation(value).setDuration(duration).start()
}