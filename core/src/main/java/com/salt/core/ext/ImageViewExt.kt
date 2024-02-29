package com.salt.core.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.salt.core.R

fun ImageView.loadImage(
    url: String,
    @DrawableRes error: Int = R.drawable.ic_launcher_foreground,
) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide
        .with(this.context)
        .load(url)
        .error(error)
        .into(this)
}

fun ImageView.loadImage(
    @DrawableRes int: Int,
) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, int))
}