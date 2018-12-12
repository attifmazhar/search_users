package com.assignment.ui.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

object CustomBindingAdapter{

    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Picasso.with(imageView.context).load(it).into(imageView)
        }
    }

}