package com.jatinvashisht.linearsearchvisualizer

import android.content.Context
import android.widget.Toast

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT, string: String): Toast {
    return Toast.makeText(context, string, duration).apply { show() }
}