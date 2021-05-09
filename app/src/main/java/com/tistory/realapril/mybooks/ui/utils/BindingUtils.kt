package com.tistory.realapril.mybooks.ui.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingUtils {
    @JvmStatic // add this line !!
    @BindingAdapter("imagePath")
    fun loadImage(imageView: ImageView, @Nullable path: String?) {

        Glide.with(imageView.context).load(path)
            //.error()
            .into(imageView)
    }
}