package com.example.premierleagueapp.core.presentation.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadFromUrl(
    url: String?,
    placeholder: Int? = null,
    error: Int? = null,
    listener: RequestListener<Drawable>? = null
) {
    val glideApp = Glide.with(context)
        .load(url)

    placeholder?.let {
        glideApp.apply(RequestOptions().placeholder(it))
    }
    error?.let {
        glideApp.apply(RequestOptions().error(it))
    }

    listener?.let {
        glideApp.listener(it)
    }

    glideApp.into(this)
}

