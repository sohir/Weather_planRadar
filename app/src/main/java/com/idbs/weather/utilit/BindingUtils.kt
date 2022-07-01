package com.idbs.weather.utilit

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.idbs.weather.R

@BindingAdapter("imageUrl_svg")
fun ImageView.loadUrl(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()
    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_img_placeholder)
        .error(R.drawable.ic_img_placeholder)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}


fun getProcessDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
// val imgUri = Uri.parse(uri).buildUpon().scheme("https").build()
    val imgUri = Uri.parse(uri).buildUpon().build()
    val requestOptions = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_img_placeholder)
    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(imgUri)
        .into(this)
}









