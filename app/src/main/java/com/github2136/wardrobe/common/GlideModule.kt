package com.jxgis.forestfiredetection.common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.github2136.wardrobe.R

/**
 * Created by yb on 2020/2/17
 */
@GlideModule
class GlideModule : AppGlideModule()

var roundedHeadDrawable: Drawable? = null
var filletHeadDrawable: Drawable? = null

//圆角
val glideOptionsFilletHead by lazy {
    RequestOptions
            .bitmapTransform(RoundedCorners(20))
//        .placeholder(R.drawable.ic_head_def)
//        .error(R.drawable.ic_head_def)
            .placeholder(filletHeadDrawable)
            .error(filletHeadDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
}

//圆形头像
val glideOptionsRoundedHead by lazy {
    RequestOptions
            .circleCropTransform()
//        .placeholder(R.drawable.ic_head_def)
//        .error(R.drawable.ic_head_def)
            .placeholder(roundedHeadDrawable)
            .error(roundedHeadDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
}

//居中裁剪
val glideOptionsCenterCrop by lazy {
    RequestOptions
            .centerCropTransform()
            .placeholder(R.drawable.img_picker_place)
            .error(R.drawable.img_picker_fail)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
}

class CircleRoundDrawable : Drawable {
    private var paint: Paint = Paint()    //画笔
    private var mWidth = 0//图片宽与高的最小值
    private var rectF: RectF? = null//矩形


    private var radius = 0    //半径
    private var roundAngle = 30 //默认圆角

    private var bitmap: Bitmap? = null//位图

    private var type = 2 //默认为圆形


    constructor(context: Context, resID: Int) : this(BitmapFactory.decodeResource(context.resources, resID), 0, 0)
    constructor(context: Context, resID: Int, w: Int, h: Int) : this(BitmapFactory.decodeResource(context.resources, resID), w, h)
    constructor(oldBitmap: Bitmap, w: Int, h: Int) {
        if (w != 0) {
            val matrix = Matrix()
            val scaleWidth = w.toFloat() / oldBitmap.width
            val scaleHeight = h.toFloat() / oldBitmap.height
            matrix.postScale(scaleWidth, scaleHeight)
            bitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.width, oldBitmap.height, matrix, true)
        } else {
            bitmap = oldBitmap
        }
        paint.setAntiAlias(true) //抗锯齿
        paint.setDither(true) //抖动,不同屏幕尺的使用保证图片质量

        ///位图渲染器
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.setShader(bitmapShader)
        mWidth = Math.min(bitmap!!.width, bitmap!!.height)
        //初始化半径
        radius = mWidth / 2
    }

    /***
     * 设置圆角
     * @param roundAngle 百分比
     */
    fun setRoundAngle(roundAngle: Float) {
        this.roundAngle = (roundAngle * bitmap!!.width).toInt()
    }

    fun setType(type: Int) {
        this.type = type
    }

    /**
     * drawable将被绘制在画布上的区域
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        //绘制区域
        rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    /**
     * 核心方法
     *
     * @param canvas
     */
    override fun draw(canvas: Canvas) {
        if (type == Type_Circle) {
            canvas.drawCircle(mWidth / 2.toFloat(), mWidth / 2.toFloat(), radius.toFloat(), paint)
        } else {
            canvas.drawRoundRect(rectF, roundAngle.toFloat(), roundAngle.toFloat(), paint)
        }
    }

    override fun setAlpha(i: Int) {
        paint.setAlpha(i)
        invalidateSelf() //更新设置
    }

    override fun getIntrinsicHeight(): Int {
        return if (type == Type_Circle) {
            mWidth
        } else bitmap!!.height
    }

    override fun getIntrinsicWidth(): Int {
        return if (type == Type_Circle) {
            mWidth
        } else bitmap!!.width
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.setColorFilter(colorFilter)
        invalidateSelf() //更行设置
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    companion object {
        const val TYPE_Round = 1
        const val Type_Circle = 2
    }
}