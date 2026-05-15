package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github2136.wardrobe.base.ui.theme.AppTheme

/**
 * Created by YB on 2021/10/9
 * 记录列表
 */
class ClothingListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                ClothingListScreen()
            }
        }
    }

    // override var autoInit = false
    // private val permissionArrayMap = ArrayMap<String, String>()
    // val permissionUtil by lazy { PermissionUtil(this) }
    // private lateinit var fileterDialog: AlertDialog
    // override fun initData(savedInstanceState: Bundle?) {
    //     bind.view = this
    //     bind.vm = vm
    //     setSupportActionBar(bind.title as Toolbar)
    //     title = "衣橱"
    //
    //     permissionArrayMap[Manifest.permission.WRITE_EXTERNAL_STORAGE] = "读写手机存储"
    //     permissionArrayMap[Manifest.permission.CAMERA] = "相机"
    //     permissionUtil.getPermission(permissionArrayMap) {
    //         vm.baseInitData()
    //     }
    //     vm.adapter.setOnItemClickListener { position ->
    //         val item = vm.adapter.getItem(position)!!
    //         startActivityForResult(Intent(this, ClothingEditActivity::class.java).apply { putExtra(ClothingEditActivity.Companion.ARG_CLOTHING, item) }, REQUEST_EDIT)
    //     }
    // }
    //
    // override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    //     menuInflater.inflate(R.menu.menu_clothing_list, menu)
    //     return super.onCreateOptionsMenu(menu)
    // }
    //
    // override fun onOptionsItemSelected(item: MenuItem): Boolean {
    //     when (item.itemId) {
    //         R.id.menu_title_add -> startActivityForResult(Intent(this, ClothingAddActivity::class.java), REQUEST_ADD)
    //         R.id.menu_title_filter -> {
    //             if (!::fileterDialog.isInitialized) {
    //                 val dataBinding = DataBindingUtil.inflate<DialogFilterBinding>(layoutInflater, R.layout.dialog_filter, null, false)
    //                 resources.getStringArray(R.array.arr_clothing_type).forEachIndexed { index, s ->
    //                     val checkBox = CheckBox(this)
    //                     checkBox.text = s
    //                     checkBox.isChecked = true
    //                     dataBinding.flType.addView(checkBox)
    //                 }
    //                 fileterDialog = AlertDialog.Builder(this)
    //                     .setTitle("过滤")
    //                     .setView(dataBinding.root)
    //                     .setPositiveButton("确定", null)
    //                     .create()
    //                 fileterDialog.setCanceledOnTouchOutside(false)
    //                 fileterDialog.setOnShowListener {
    //                     fileterDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
    //                         vm.seasonLD.value?.apply {
    //                             clear()
    //                             if (dataBinding.cbSpring.isChecked) {
    //                                 add("春")
    //                             }
    //                             if (dataBinding.cbSummer.isChecked) {
    //                                 add("夏")
    //                             }
    //                             if (dataBinding.cbAutumn.isChecked) {
    //                                 add("秋")
    //                             }
    //                             if (dataBinding.cbWinter.isChecked) {
    //                                 add("冬")
    //                             }
    //                         }
    //                         vm.typeLD.value?.apply {
    //                             clear()
    //                             dataBinding.flType.children.forEach { checkBox ->
    //                                 (checkBox as CheckBox).apply {
    //                                     if (isChecked) {
    //                                         add(checkBox.text.toString())
    //                                     }
    //                                 }
    //                             }
    //                         }
    //                         if (vm.seasonLD.value!!.isEmpty()) {
    //                             showToast("至少选择一个季节")
    //                         } else if (vm.typeLD.value!!.isEmpty()) {
    //                             showToast("至少选择一个类型")
    //                         } else {
    //                             vm.baseInitData()
    //                             fileterDialog.dismiss()
    //                         }
    //                     }
    //                 }
    //             }
    //             fileterDialog.show()
    //         }
    //     }
    //     return true
    // }
    //
    // override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //     super.onActivityResult(requestCode, resultCode, data)
    //     if (resultCode == Activity.RESULT_OK) {
    //         if (requestCode == REQUEST_ADD) {
    //             vm.baseInitData()
    //         } else if (requestCode == REQUEST_EDIT) {
    //             vm.baseInitData()
    //         }
    //     }
    // }
    //
    // override fun onRestart() {
    //     super.onRestart()
    //     permissionUtil.onRestart()
    // }
    //
    // override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    //     super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    //     permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults)
    // }
    //
    // companion object {
    //     const val REQUEST_ADD = 474
    //     const val REQUEST_EDIT = 62
    // }
    val refreshResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingListScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("衣橱") },
                actions = {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Left),
                        tooltip = {
                            PlainTooltip { Text("添加") }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Filled.Add, "添加")

                        }
                    }
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Left),
                        tooltip = {
                            PlainTooltip { Text("过滤") }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.FilterAlt, "过滤")

                        }
                    }
                }
            )
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            item {
                Text("00000\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0")
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
private fun ClothingListScreenPreview() {
    AppTheme {
        ClothingListScreen()
    }
}