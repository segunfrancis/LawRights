package com.segunfrancis.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.segunfrancis.common.R

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}
