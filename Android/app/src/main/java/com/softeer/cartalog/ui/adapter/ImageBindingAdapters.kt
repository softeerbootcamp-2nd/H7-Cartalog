package com.softeer.cartalog.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imgUrl")
fun setImageWithUrl(
    imageView: ImageView,
    imgUrl: String
) {
    imageView.load(imgUrl)
}