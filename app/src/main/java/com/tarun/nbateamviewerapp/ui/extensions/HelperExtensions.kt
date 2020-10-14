package com.tarun.nbateamviewerapp.ui.extensions

import android.content.Context
import android.widget.Toast

/**
 * Displays a toast of [Toast.LENGTH_LONG] duration.
 *
 * @param message The resource Id of the message to show.
 */
fun Context.displayLongToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}