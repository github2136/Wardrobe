package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github2136.wardrobe.base.ui.theme.AppTheme
import com.github2136.wardrobe.view.activity.clothing.colthing_add.ClothingAddActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                test(viewModel())
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
    val refreshResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun test(viewModel: ClothingListVM) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val hasMoreData by viewModel.hasMoreData.collectAsState()

    val listState = rememberLazyListState()

    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        viewModel.refreshTet()
        // isRefreshing = true
        // coroutineScope.launch {
        //     delay(5000)
        //     itemCount += 5
        //     isRefreshing = false
        // }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Title") },
                // Provide an accessible alternative to trigger refresh.
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Filled.Refresh, "Trigger Refresh")
                    }
                },
            )
        }
    ) {
        PullToRefreshBox(
            modifier = Modifier.padding(it),
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            LazyColumn(Modifier.fillMaxSize()) {


                items(
                    items = items,
                    key = { item -> item.ciId }
                ) { item ->
                    Text("${item.ciId}")
                }

                // 加载更多指示器
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                // 没有更多数据时显示提示
                if (!hasMoreData && items.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "没有更多数据了",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingListScreen(viewModel: ClothingListVM) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val hasMoreData by viewModel.hasMoreData.collectAsState()

    val listState = rememberLazyListState()

    // 监听滚动到底部，加载更多数据
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val temp = lastVisibleItem != null &&
                lastVisibleItem.index >= items.size - 5 &&
                !isLoading && hasMoreData && !isRefreshing
            temp
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.refreshTet()
        }
    }
    val context = LocalContext.current

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
                            context.startActivity(Intent(context, ClothingAddActivity::class.java))
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
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.initData()
            }) {
            if (items.isEmpty() && !isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("暂无数据")
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    items(
                        items = items,
                        key = { item -> item.ciId }
                    ) { item ->
                        Text("${item.ciId}")
                    }

                    // 加载更多指示器
                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    // 没有更多数据时显示提示
                    if (!hasMoreData && items.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "没有更多数据了",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun ClothingListScreenPreview() {
    AppTheme {
        ClothingListScreen(viewModel())
    }
}