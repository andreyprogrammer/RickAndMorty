package com.example.anderymerkurev.data.network

import android.widget.ImageView
import com.example.anderymerkurev.data.R
import com.squareup.picasso.Picasso

class PicassoLoader {
    fun loadImage(url: String, imageView: ImageView) {
        Picasso
            .get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder_error)
            .into(imageView)
    }
}