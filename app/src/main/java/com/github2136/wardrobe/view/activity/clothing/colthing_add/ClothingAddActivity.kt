package com.github2136.wardrobe.view.activity.clothing.colthing_add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github2136.wardrobe.R
import com.github2136.wardrobe.base.ui.theme.AppTheme
import com.github2136.wardrobe.common.Other
import com.github2136.wardrobe.view.dialog.MediaDialog
import com.github2136.wardrobe.view.activity.clothing.colthing_add.ClothingAddVM
import com.github2136.wardrobe.view.activity.clothing.colthing_list.ClothingListScreen
import com.github2136.wardrobe.view.activity.clothing.colthing_list.ClothingListVM

/**
 * Created by YB on 2021/10/11
 * 添加
 */
class ClothingAddActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val viewModel: ClothingAddVM = viewModel()
                ClothingAddScreen()
            }
        }
    }
}

/* : BaseActivity<ClothingAddVM, ActivityClothingAddBinding>() {
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
                    intent.putExtra(MediaDialog.Companion.ARG_MEDIA_TYPE, MediaDialog.Companion.CAPTURE or MediaDialog.Companion.PHOTO_PICKER)
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
                        val uri = getParcelableExtra<Uri>(MediaDialog.Companion.RESULT_URI)
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
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingAddScreen() {


    val context = LocalContext.current
    val seasons = listOf("春", "夏", "秋", "冬")
    val seasonCheckedList = remember { mutableStateListOf<Int>() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("添加") },
                actions = {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Left),
                        tooltip = {
                            PlainTooltip { Text("保存") }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Filled.Check, "保存")
                        }
                    }
                }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("类型")
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text("请选择")
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("季节")
                Spacer(modifier = Modifier.width(16.dp))
                MultiChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    seasons.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = seasons.size),
                            onCheckedChange = {
                                if (index in seasonCheckedList) {
                                    seasonCheckedList.remove(index)
                                } else {
                                    seasonCheckedList.add(index)
                                }
                            },
                            checked = index in seasonCheckedList,
                        ) {
                            Text(label)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("图片")
                FlowColumn {
                    OutlinedIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Add, "添加")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("备注")

            }
        }
    }
}
@Preview
@Composable
private fun ClothingAddScreenPreview() {
    ClothingAddScreen()
}