package com.github2136.wardrobe.view.activity.clothing.colthing_list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github2136.wardrobe.base.ui.theme.AppTheme
import com.github2136.wardrobe.model.entity.Clothing
import com.github2136.wardrobe.view.activity.clothing.colthing_add.ClothingAddActivity

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
                val viewModel: ClothingListVM = viewModel()
                val items by viewModel.items.collectAsState()
                val isLoadingMore by viewModel.isLoadingMore.collectAsState()
                val isRefreshing by viewModel.isRefreshing.collectAsState()
                val isSuccess by viewModel.isSuccess.collectAsState()
                val hasMoreData by viewModel.hasMoreData.collectAsState()
                ClothingListScreen(items, isLoadingMore, isRefreshing, isSuccess, hasMoreData) {
                    viewModel.loadMoreData()
                }
            }
        }
    }


    val refreshResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingListScreen(
    items: List<Clothing> = mutableListOf(),
    isLoadingMore: Boolean = false,
    isRefreshing: Boolean = false,
    isSuccess: Boolean = true,
    hasMoreData: Boolean = false,
    loadMoreData: () -> Unit = {},
) {


    val listState = rememberLazyListState()

    // 监听滚动到底部，加载更多数据
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null &&
                lastVisibleItem.index >= items.size - 5 &&
                !isLoadingMore && hasMoreData && !isRefreshing
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            loadMoreData.invoke()
        }
    }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {

        }
    }
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
                            launcher.launch(Intent(context, ClothingAddActivity::class.java))
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = isRefreshing,
            onRefresh = {
                loadMoreData.invoke()
            }) {
            if (items.isEmpty()) {
                if (isSuccess) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight() // 明确告诉 Box 要占满高度
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("暂无数据")
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight() // 明确告诉 Box 要占满高度
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("获取失败")
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    items(
                        items = items,
                        key = { item -> item.ciId }
                    ) { item ->
                        Text("${item.ciId}")
                    }

                    // 加载更多指示器
                    if (isLoadingMore) {
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
private fun ClothingListScreenEmptyPreview() {
    AppTheme {
        ClothingListScreen()
    }
}
@Preview(showBackground = true)
@Composable
private fun ClothingListScreenFailPreview() {
    AppTheme {
        ClothingListScreen(isSuccess = false)
    }
}
@Preview(showBackground = true)
@Composable
private fun ClothingListScreenListPreview() {
    AppTheme {
        ClothingListScreen(mutableListOf(Clothing()))
    }
}