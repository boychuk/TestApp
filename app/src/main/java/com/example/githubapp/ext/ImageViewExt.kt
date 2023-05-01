package com.example.githubapp.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.src(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}