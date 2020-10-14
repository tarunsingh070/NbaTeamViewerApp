package com.tarun.nbateamviewerapp.ui.extensions

import android.view.View

/**
 * An extension function that sets a view to [View.VISIBLE] or [View.GONE] based on the boolean passed in.
 *
 * @param shouldShow Boolean indicating if the view should be made [View.VISIBLE] or [View.GONE].
 */
fun View.setVisibility(shouldShow: Boolean) {
    if (shouldShow) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}