package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github2136.wardrobe.base.ui.theme.AppTheme

/**
 * Created by YB on 2021/10/9
 * 记录列表
 */
class ClothingListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MaterialTheme.colorScheme
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
}
@Composable
fun ClothingListScreen() {
    Text("xxx")
}
@Preview(showBackground = true)
@Composable
private fun ClothingListScreenPreview() {
    AppTheme {
        ClothingListScreen()
    }
}