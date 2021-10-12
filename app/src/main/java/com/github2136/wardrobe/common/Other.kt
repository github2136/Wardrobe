package com.github2136.wardrobe.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.github2136.photopicker.activity.PhotoViewActivity
import com.github2136.util.DateUtil
import com.github2136.util.FileUtil
import com.github2136.wardrobe.R
import com.jxgis.forestfiredetection.common.GlideApp
import com.jxgis.forestfiredetection.common.glideOptionsCenterCrop
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.net.URLEncoder
import java.util.*

/**
 * Created by YB on 2019/9/26
 * 杂项类
 */
object Other {
    val mimeTypeMap by lazy { MimeTypeMap.getSingleton() }

    /**
     * 打开GPS
     */
    fun openGPS(activity: Activity): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(activity)
                .setTitle("温馨提示")
                .setMessage("为了更好的体验，请打开GPS。")
                .setPositiveButton("打开") { _, _ ->
                    // 转到手机设置界面，用户设置GPS
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activity.startActivity(intent)
                }
                .show()
            return false
        }
        return true
    }

    /**
     * 获取文件名
     */
    fun FileUtil.getFileName(path: String): String {
        val index = path.lastIndexOf("/") + 1
        return path.substring(index)
    }

    /**
     * 获取请求对象
     */
    fun getRequestBody(path: String): RequestBody {
        return File(path).asRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())
    }

    /**
     * 获取对象
     */
    fun getMultipartBody(paramName: String, path: String): MultipartBody.Part {
        val requestFile = File(path).asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(paramName, File(path).name, requestFile)
    }

    /**
     * 耗时
     */
    fun getTimeConsumingString(date1: Date, date2: Date): String {
        var interval: Long //相差时间
        val dateTimeMil = date1.time
        var diffTimeMil = date2.time - dateTimeMil
        val relativeTimeStr = StringBuilder()

        if (DateUtil.HOUR in DateUtil.SECOND until diffTimeMil) {
            interval = diffTimeMil / DateUtil.HOUR
            relativeTimeStr.append(String.format("%02d:", interval))
            diffTimeMil %= DateUtil.HOUR
        } else {
            relativeTimeStr.append("00:")
        }
        if (DateUtil.MINUTE in DateUtil.SECOND until diffTimeMil) {
            interval = diffTimeMil / DateUtil.MINUTE
            relativeTimeStr.append(String.format("%02d:", interval))
            diffTimeMil %= DateUtil.MINUTE
        } else {
            relativeTimeStr.append("00:")
        }
        interval = diffTimeMil / DateUtil.SECOND
        relativeTimeStr.append(String.format("%02d", interval))
        return relativeTimeStr.toString()
    }


    /**
     * 复制本地数据至外部存储
     */
    fun copyDataBase(context: Context) {
        var input: InputStream? = null
        var output: OutputStream? = null
        try {
            input = FileInputStream(context.getDatabasePath(Constants.SQLITE_NAME))
            output = FileOutputStream(FileUtil.getExternalStorageRootPath() + File.separator + Constants.SQLITE_NAME)
            val buf = ByteArray(1024)
            var len = 0
            while ({ len = input!!.read(buf);len }() != -1) {
                output.write(buf, 0, len)
            }
        } finally {
            input?.close()
            output?.close()
        }

        try {
            input = FileInputStream(context.getDatabasePath("ForestRanger.db-shm"))
            output = FileOutputStream(FileUtil.getExternalStorageRootPath() + File.separator + "${Constants.SQLITE_NAME}-shm")
            val buf = ByteArray(1024)
            var len = 0
            while ({ len = input!!.read(buf);len }() != -1) {
                output.write(buf, 0, len)
            }
        } finally {
            input?.close()
            output?.close()
        }

        try {
            input = FileInputStream(context.getDatabasePath("ForestRanger.db-wal"))
            output = FileOutputStream(FileUtil.getExternalStorageRootPath() + File.separator + "${Constants.SQLITE_NAME}-wal")
            val buf = ByteArray(1024)
            var len = 0
            while ({ len = input.read(buf);len }() != -1) {
                output.write(buf, 0, len)
            }
        } finally {
            input?.close()
            output?.close()
        }

    }

    /**
     * app是否安装
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        val installed: Boolean
        installed = try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return installed
    }

    //使用网络或本地地址判断
    fun isVideo(path: String): Boolean {
        return mimeTypeMap.getMimeTypeFromExtension(FileUtil.getSuffix(path))?.startsWith("video/") == true
    }

    fun isVoice(path: String): Boolean {
        return mimeTypeMap.getMimeTypeFromExtension(FileUtil.getSuffix(path))?.startsWith("audio/") == true
    }

    fun isImage(path: String): Boolean {
        return mimeTypeMap.getMimeTypeFromExtension(FileUtil.getSuffix(path))?.startsWith("image/") == true
    }

    /**
     * 获取视频缩略图
     */
    fun getVideoBitmap(path: String): Bitmap? {
        var bitmap: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(path)
            bitmap = retriever.frameAtTime
        } catch (ex: Exception) {
        } finally {
            try {
                retriever.release()
            } catch (ex: Exception) {
            }
        }
        return bitmap
    }

    /**
     * 保存bitmap
     */
    fun saveBitmap(sourceBitmap: Bitmap, filePath: String): Boolean {
        val file = File(filePath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        return try {
            val fileOut = FileOutputStream(file)
            sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOut)
            fileOut.flush()
            fileOut.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 获取RTSP地址的ip端口，第一个为ip，第二个为端口
     */
    fun getHostPort(url: String): Array<String>? {
        var i1 = url.indexOf("rtsp://")
        if (i1 == -1) {
            return null
        }
        i1 += 6
        val i2 = url.indexOf(":", i1)
        if (i2 == -1) {

            return null
        }
        val i3 = url.indexOf("/", i2)
        if (i3 == -1) {
            return null
        }
        return arrayOf(url.substring(i1 + 1, i2), url.substring(i2 + 1, i3))
    }

    /**
     * 生成视频本地地址
     */
    fun getVideoLocalPath(context: Context, url: String) =
        FileUtil.getExternalStorageProjectPath(context) + "/${Constants.DIR_VIDEO}/" + FileUtil.createFileName("." + FileUtil.getSuffix(url))

    /**
     * 生成音频本地地址
     */
    fun getVoiceLocalPath(context: Context, url: String) =
        FileUtil.getExternalStorageProjectPath(context) + "/${Constants.DIR_VOICE}/" + FileUtil.createFileName("." + FileUtil.getSuffix(url))

    /**
     * 生成图片本地地址
     */
    fun getImageLocalPath(context: Context) =
        FileUtil.getExternalStorageProjectPath(context) + "/${Constants.DIR_IMAGE}/" + FileUtil.createFileName(".jpg")


    /**
     * 添加本地Uri媒体文件地址到布局
     * @param mediaType 文件类型，具体查看MediaDialog
     * @param viewGroup 添加的布局
     * @param mediaUris 路径集合
     */
    fun getMediaUri(context: Context, layoutInflater: LayoutInflater, uri: Uri, mediaWidth: Int, viewGroup: ViewGroup, mediaUris: MutableList<Uri>) {
        mediaUris.add(uri)
        val v = layoutInflater.inflate(R.layout.view_media, viewGroup, false)
        v.layoutParams = ViewGroup.LayoutParams(mediaWidth, mediaWidth)
        val iv: ImageView = v.findViewById(R.id.ivMedia)
        val ibDelete: ImageButton = v.findViewById(R.id.ibDelete)
        ibDelete.visibility = View.VISIBLE
        viewGroup.addView(v, viewGroup.childCount - 1)

        ibDelete.setOnClickListener {
            AlertDialog.Builder(context).setTitle("提示")
                .setMessage("是否删除该文件")
                .setPositiveButton("删除") { _, _ ->
                    viewGroup.removeView(v)
                    mediaUris.remove(uri)
                }
                .show()
        }

        v.setOnClickListener {
            val intent = Intent(context, PhotoViewActivity::class.java)
            intent.putStringArrayListExtra(PhotoViewActivity.ARG_PHOTOS, arrayListOf(FileUtil.getFileAbsolutePath(context, uri)))
            context.startActivity(intent)

        }
        GlideApp.with(context)
            .load(uri)
            .apply(glideOptionsCenterCrop)
            .into(iv)
    }


    /**
     * 添加本地真实路径或网络路径媒体文件地址到布局
     */
    fun getMediaPath(context: Context, layoutInflater: LayoutInflater, url: String, mediaWidth: Int, viewGroup: ViewGroup, mediaPaths: MutableList<String>? = null, del: Boolean = false) {
        mediaPaths?.add(url)
        val v = layoutInflater.inflate(R.layout.view_media, viewGroup, false)
        v.layoutParams = ViewGroup.LayoutParams(mediaWidth, mediaWidth)
        val iv: ImageView = v.findViewById(R.id.ivMedia)
        if (del) {
            val ibDelete: ImageButton = v.findViewById(R.id.ibDelete)
            ibDelete.visibility = View.VISIBLE
            viewGroup.addView(v, viewGroup.childCount - 1)
            ibDelete.setOnClickListener {
                AlertDialog.Builder(context).setTitle("提示")
                    .setMessage("是否删除该文件")
                    .setPositiveButton("删除") { _, _ ->
                        viewGroup.removeView(v)
                        mediaPaths?.remove(url)
                    }
                    .show()
            }
        } else {
            viewGroup.addView(v)
        }

        v.setOnClickListener {
            val intent = Intent(context, PhotoViewActivity::class.java)
            intent.putStringArrayListExtra(PhotoViewActivity.ARG_PHOTOS, arrayListOf(url.getFilePath()))
            context.startActivity(intent)
        }

        GlideApp.with(context)
            .load(url.getFilePath())
            .apply(glideOptionsCenterCrop)
            .into(iv)
    }
}

fun String.urlEncode(): String = URLEncoder.encode(this, "UTF-8")

/**
 * 16进制字符转ByteArray
 */
fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }
    return chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}

/**
 * ByteArray转16进制字符
 */
fun ByteArray.encodeHex(): String {
    return joinToString(separator = "") { "%02x".format(it) }
}

/**
 * 获取本地路径或网络路径
 */
fun String.getFilePath(): String {
    return if (startsWith("/storage")) {
        //本地路径
        this
    } else {
        this
    }
}
