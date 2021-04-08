package com.picpay.desafio.android.extensions

import android.view.View

object ViewExtension {

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.show() {
        visibility = View.VISIBLE
    }
}