package com.sumeet.cars360.util

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.sumeet.cars360.R

fun showDialogBox(
    activity: Activity,
    title: String,
    message: String,
    icon: Drawable?,
    positiveButton: String,
    negativeButton: String,
    action: () -> Unit
) {
    AlertDialog.Builder(activity) //set message, title, and icon
        .setTitle(title)
        .setMessage(message)
        .setIcon(icon)
        .setPositiveButton(positiveButton) { dialog, _ ->
            action.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(negativeButton) { dialog, _ -> dialog.dismiss() }
        .create().show()
}

fun showSnackBar(activity: Activity?, view: View, msg: String) {
    val snack = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Snackbar.make(
            view, msg, Snackbar.LENGTH_SHORT
        )
            .setBackgroundTint(view.resources.getColor(R.color.gray, null))
            .setTextColor(view.resources.getColor(R.color.primaryRed, activity?.theme))
    } else {
        Snackbar.make(
            view, msg, Snackbar.LENGTH_SHORT
        )
    }
    snack.setAction("OK") {
        snack.dismiss()
    }
    snack.show()
}