package com.idbs.weather.utilit

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.idbs.weather.R

@BindingAdapter("imageUrl_svg")
fun ImageView.loadUrl(url: String) {

    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}










