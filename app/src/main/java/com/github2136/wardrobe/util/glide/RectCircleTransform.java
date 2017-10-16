package com.github2136.wardrobe.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 矩形圆角变形
 * Created by yubin on 2016/3/23.
 */
public class RectCircleTransform extends BitmapTransformation {
    public RectCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) return null;
        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap mBitmap;
        Matrix matrix = new Matrix();
        int w;
        w = toTransform.getWidth() > toTransform.getHeight() ? toTransform.getHeight() : toTransform.getWidth();
        int x, y;
        if (toTransform.getWidth() > toTransform.getHeight()) {
            x = (toTransform.getWidth() - toTransform.getHeight()) / 2;
            y = 0;
        } else {
            x = 0;
            y = (toTransform.getHeight() - toTransform.getWidth()) / 2;
        }
//                        ? toTransform.getHeight() : toTransform.getWidth();
        //放大或缩小图片
        float scale = ((float) outWidth) / size;
        matrix.setScale(scale, scale);
        mBitmap = Bitmap.createBitmap(toTransform, x, y, w, w, matrix, false);

//        int x = (toTransform.getWidth() - size) / 2;
//        int y = (toTransform.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createScaledBitmap(mBitmap, outWidth, outHeight, false);

        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = outWidth / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}