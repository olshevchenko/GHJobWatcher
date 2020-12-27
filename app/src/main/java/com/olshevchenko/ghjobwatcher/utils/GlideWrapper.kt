package com.olshevchenko.ghjobwatcher.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.olshevchenko.ghjobwatcher.R

/**
 * Wrapper class for Glide with caching, drawing progress, etc..
 */
class GlideWrapper {

    fun getProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 30f
            setStyle(CircularProgressDrawable.LARGE)
            setColorSchemeColors(R.color.white)
            start()
        }
    }

    fun loadImage(view: ImageView, uri: String?, context: Context) {
        Log.i(
            "GlideWrapper",
            "+++ Gonna call Glide.load() $uri"
        )
        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(getProgressDrawable(context))
            .error(R.drawable.ic_broken_image)

        Glide.with(view.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(view)
    }

    // when image is cached locally no need for .placeholder or .error
    fun loadCachedImage(view: ImageView, uri: String?) {
        Log.i(
            "GlideWrapper",
            "+++ Gonna call Glide.loadCachedImage() $uri"
        )
        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        Glide.with(view.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(view)
    }

}