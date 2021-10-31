package com.example.neighbors.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.R

class Utils {
    companion object {
        fun loadImage(url: String, layout: View, image: ImageView){
            Glide.with(layout)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_baseline_person_outline_24)
                .error(R.drawable.ic_baseline_person_outline_24)
                .skipMemoryCache(false)
                .into(image)
        }
    }
}