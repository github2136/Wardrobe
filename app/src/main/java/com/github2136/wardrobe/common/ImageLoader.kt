package com.github2136.wardrobe.common

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github2136.wardrobe.R
import com.jxgis.forestfiredetection.common.GlideApp

/**
 * Created by yb on 2020/2/17
 */
class ImageLoader : com.github2136.photopicker.other.ImageLoader {
    override fun loadThumbnail(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, path: String) {
        GlideApp.with(context)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(resizeX, resizeY)
            .placeholder(R.drawable.img_picker_place)
            .error(R.drawable.img_picker_fail)
            .centerCrop()
            .dontAnimate()
            .into(imageView)
    }

    override fun loadAnimatedGifThumbnail(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, path: String) {
        GlideApp.with(context)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(resizeX, resizeY)
            .placeholder(R.drawable.img_picker_place)
            .error(R.drawable.img_picker_fail)
            .centerCrop()
            .into(imageView)
    }

    override fun loadImage(context: Context, imageView: ImageView, path: String) {
        val glide = if (TextUtils.isDigitsOnly(path)) {
            GlideApp.with(context).load(path.toInt())
        } else {
            GlideApp.with(context).load(path)
        }
        glide.diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_picker_place)
            .error(R.drawable.img_picker_fail)
            .dontAnimate()
            .into(imageView)
    }

    override fun loadAnimatedGifImage(context: Context, imageView: ImageView, path: String) {
        GlideApp.with(context)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_picker_place)
            .error(R.drawable.img_picker_fail)
            .into(imageView)
    }

    override fun supportAnimatedGifThumbnail(): Boolean {
        return true
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }
}