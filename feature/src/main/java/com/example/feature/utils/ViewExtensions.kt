package com.example.feature.utils

import android.view.View
import android.widget.ImageView
import kotlin.math.roundToInt

fun View.updateHeight(newHeight: Int) {
    if (layoutParams.height != newHeight) {
        layoutParams = layoutParams.apply { height = newHeight }
        post {
            if (layoutParams.height != height) {
                layoutParams = layoutParams
            }
        }
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}


fun ImageView.resizeRequest(width: Int, height: Int) {
    if (this.width != 0) {
        updateSize(width, height)
    } else {
        addOneShotLayoutChangeListener { updateSize(width, height) }
    }
}

fun ImageView.updateSize(imageWidth: Int, imageHeight: Int) {
    val scale = width.toFloat() / imageWidth
    val newHeight = (imageHeight * scale).roundToInt()
    updateHeight(newHeight)
}

fun View.addOneShotLayoutChangeListener(onLayoutAction: () -> Unit) {
    addOnLayoutChangeListener(OneShotLayoutChangeListener(this, onLayoutAction))
}

class OneShotLayoutChangeListener(private val view: View, private val onLayoutAction: () -> Unit) :
    View.OnLayoutChangeListener {
    override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int,
    ) {
        view.removeOnLayoutChangeListener(this)
        onLayoutAction.invoke()
    }
}