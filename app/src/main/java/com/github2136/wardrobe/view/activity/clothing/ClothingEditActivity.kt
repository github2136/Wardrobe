package com.github2136.wardrobe.view.activity.clothing

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.github2136.basemvvm.BaseActivity
import com.github2136.util.FileUtil
import com.github2136.util.dp2px
import com.github2136.wardrobe.R
import com.github2136.wardrobe.common.Other
import com.github2136.wardrobe.databinding.ActivityClothingEditBinding
import com.github2136.wardrobe.view.dialog.MediaDialog
import com.github2136.wardrobe.vm.clothing.ClothingEditVM

/**
 * Created by YB on 2021/10/11
 * 编辑
 */
class ClothingEditActivity : BaseActivity<ClothingEditVM, ActivityClothingEditBinding>() {
    override fun getLayoutId() = R.layout.activity_clothing_edit
    val mediaWidth by lazy { (resources.displayMetrics.widthPixels - ((8 * 2 + 20f * 3).dp2px)) / 4 }
    val mediaUris = mutableListOf<Uri>()

    //旧添加的媒体文件
    val oldMedia = mutableListOf<String>()
    override fun initData(savedInstanceState: Bundle?) {
        bind.view = this
        bind.vm = vm
        setSupportActionBar(bind.title as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "编辑"
        bind.ibAddMedia.layoutParams = ViewGroup.LayoutParams(mediaWidth, mediaWidth)
        vm.clothingLD.value = intent.getParcelableExtra(ARG_CLOTHING)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_clothing_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_ok -> {
                if (bind.flMedia.childCount < 2) {
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
                        ciPicture.clear()
                        ciPicture.addAll(oldMedia)
                        ciPicture.addAll(mediaUris.map { FileUtil.getFileAbsolutePath(this@ClothingEditActivity, it)!! }.toMutableList())
                    }
                    vm.edit()
                }
            }
            R.id.menu_delete -> {
                AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("确认删除吗？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("删除") { _, _ ->
                        vm.del()
                    }.show()
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
                        Other.getMediaUri(this@ClothingEditActivity, layoutInflater, uri, mediaWidth, bind.flMedia, mediaUris)
                    }
                }
            }
        }
    }

    override fun initObserve() {
        super.initObserve()
        vm.clothingLD.observe(this, Observer {
            if (it.ciSeason.contains("春")) {
                bind.cbSpring.isChecked = true
            }
            if (it.ciSeason.contains("夏")) {
                bind.cbSummer.isChecked = true
            }
            if (it.ciSeason.contains("秋")) {
                bind.cbAutumn.isChecked = true
            }
            if (it.ciSeason.contains("冬")) {
                bind.cbWinter.isChecked = true
            }
            val typeIndex = resources.getStringArray(R.array.arr_clothing_type).indexOf(it.ciType)
            bind.spType.setSelection(typeIndex)
            it.ciPicture.forEach {
                Other.getMediaPath(this, layoutInflater, it, mediaWidth, bind.flMedia, oldMedia, true)
            }
        })
        vm.addLD.observe(this, Observer {
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    companion object {
        const val REQUEST_ADD_MEDIA = 806
        const val ARG_CLOTHING = "CLOTHING"
    }
}