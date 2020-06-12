package com.example.premierleagueapp.core.presentation.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.premierleagueapp.core.presentation.di.module.SvgSoftwareLayerSetter

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


fun ImageView.loadSVGFromUrl(
    url: String?,
    placeholder: Int? = null,
    error: Int? = null
) {
    val glideApp = Glide.with(context)
        .`as`(PictureDrawable::class.java)
        .load(url)
        .listener(SvgSoftwareLayerSetter())

    placeholder?.let {
        glideApp.apply(RequestOptions().placeholder(it))
    }
    error?.let {
        glideApp.apply(RequestOptions().error(it))
    }

    glideApp.into(this)
}

