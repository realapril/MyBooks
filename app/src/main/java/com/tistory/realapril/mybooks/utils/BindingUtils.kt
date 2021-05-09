package com.tistory.realapril.mybooks.utils

import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingUtils {
    @JvmStatic // add this line !!
    @BindingAdapter("imagePath")
    fun loadImage(imageView: ImageView, @Nullable path: String?) {

        Glide.with(imageView.context).load(path)
            //.error()
            .into(imageView)
    }
}