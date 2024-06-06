package com.vosaa.majoritytechassignment.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vosaa.majoritytechassignment.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty())
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_image))
                .into(view)
        else
            view.setImageResource(R.drawable.ic_image)

    }
}
