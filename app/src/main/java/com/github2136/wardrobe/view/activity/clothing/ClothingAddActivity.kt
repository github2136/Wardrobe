package com.github2136.wardrobe.view.activity.clothing

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.github2136.basemvvm.BaseActivity
import com.github2136.util.FileUtil
import com.github2136.util.dp2px
import com.github2136.wardrobe.R
import com.github2136.wardrobe.common.Other
import com.github2136.wardrobe.databinding.ActivityClothingAddBinding
import com.github2136.wardrobe.view.dialog.MediaDialog
import com.github2136.wardrobe.vm.clothing.ClothingAddVM

/**
 * Created by YB on 2021/10/11
 * 添加
 */
class ClothingAddActivity : BaseActivity<ClothingAddVM, ActivityClothingAddBinding>() {
    override fun getLayoutId() = R.layout.activity_clothing_add
    val mediaWidth by lazy { (resources.displayMetrics.widthPixels - ((8 * 2 + 20f * 3).dp2px)) / 4 }
    val mediaUris = mutableListOf<Uri>()

    override fun initData(savedInstanceState: Bundle?) {
        bind.view = this
        bind.vm = vm
        setSupportActionBar(bind.title as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "添加"
        bind.ibAddMedia.layoutParams = ViewGroup.LayoutParams(mediaWidth, mediaWidth)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_clothing_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_ok -> {
                if (mediaUris.isEmpty()) {
                    showToast("请至少添加一张图片")
                } else if (!bind.cbSpring.isChecked && !bind.cbSummer.isChecked && !bind.cbAutumn.isChecked && !bind.cbWinter.isChecked) {
                    showToast("请至少选择一个季节")
                } else {
                    vm.clothingLD.value?.apply {
                        ciType = bind.spType.selectedItem.toString()
                        val season = mutableListOf<String>()
                        if (bind.cbSpring.isChecked) {
                            season.add("春")
                        }
                        if (bind.cbSummer.isChecked) {
                            season.add("夏")
                        }
                        if (bind.cbAutumn.isChecked) {
                            season.add("秋")
                        }
                        if (bind.cbWinter.isChecked) {
                            season.add("冬")
                        }
                        ciSeason = season.joinToString { it }
                        ciPicture = mediaUris.map { FileUtil.getFileAbsolutePath(this@ClothingAddActivity, it)!! }.toMutableList()
                    }
                    vm.save()
                }
            }
        }
        return true
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ibAddMedia -> {
                if (bind.flMedia.childCount < 8) {
                    val intent = Intent(this, MediaDialog::class.java)
                    intent.putExtra(MediaDialog.ARG_MEDIA_TYPE, MediaDialog.CAPTURE or MediaDialog.PHOTO_PICKER)
                    startActivityForResult(intent, REQUEST_ADD_MEDIA)
                } else {
                    showToast("最多添加7个媒体文件")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_ADD_MEDIA -> {
                    data?.run {
                        val uri = getParcelableExtra<Uri>(MediaDialog.RESULT_URI)
                        Other.getMediaUri(this@ClothingAddActivity, layoutInflater, uri, mediaWidth, bind.flMedia, mediaUris)
                    }
                }
            }
        }
    }

    override fun initObserve() {
        super.initObserve()
        vm.addLD.observe(this, Observer {
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    companion object {
        const val REQUEST_ADD_MEDIA = 806
    }
}