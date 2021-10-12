package com.github2136.wardrobe.view.dialog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github2136.photopicker.activity.CaptureActivity
import com.github2136.photopicker.activity.PhotoPickerActivity

/**
 * Created by YB on 2020/4/9
 * 媒体文件添加
 */
class MediaDialog : AppCompatActivity() {
    var mediaType: Int = 0
    private val photo_picker = "本地图片"
    private val capture = "拍照"
    private var whitch = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this))
        mediaType = intent.getIntExtra(ARG_MEDIA_TYPE, 0)
        val item = mutableListOf<String>()
        if (mediaType and PHOTO_PICKER == PHOTO_PICKER) {
            item.add(photo_picker)
        }
        if (mediaType and CAPTURE == CAPTURE) {
            item.add(capture)
        }
        val dialog = AlertDialog.Builder(this)
            .setTitle("操作")
            .setItems(item.toTypedArray()) { _, whitch ->
                this.whitch = whitch
                when (item[whitch]) {
                    photo_picker -> {
                        //图片选择
                        val intent = Intent(this, PhotoPickerActivity::class.java)
                        intent.putExtra(PhotoPickerActivity.ARG_PICKER_COUNT, 1)
                        startActivityForResult(intent, REQUEST_PHOTOPICKER)
                    }
                    capture      -> {
                        //拍照
                        val intent = Intent(this, CaptureActivity::class.java)
                        startActivityForResult(intent, REQUEST_CAPTURE)
                    }
                }
            }.setOnDismissListener {
                if (whitch == -1) {
                    finish()
                }
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            var mediaType = 0
            when (requestCode) {
                REQUEST_PHOTOPICKER -> {
                    uri = data?.getParcelableArrayListExtra<Uri>(PhotoPickerActivity.ARG_RESULT_URI)?.first()
                    mediaType = PHOTO_PICKER
                }
                REQUEST_CAPTURE     -> {
                    uri = data?.data
                    mediaType = CAPTURE
                }
            }

            uri?.run {
                val intent = Intent()
                intent.putExtra(RESULT_MEDIA_TYPE, mediaType)
                intent.putExtra(RESULT_URI, this)
                setResult(Activity.RESULT_OK, intent)
            }
        }
        finish()
    }

    companion object {
        //本地图片
        const val PHOTO_PICKER = 0b0001
        //拍照
        const val CAPTURE = 0b0010

        const val REQUEST_PHOTOPICKER = 659
        const val REQUEST_CAPTURE = 998

        const val ARG_MEDIA_TYPE = "MEDIA_TYPE"

        const val RESULT_MEDIA_TYPE = "MEDIA_TYPE"
        const val RESULT_URI = "URI"
    }
}