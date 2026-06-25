package com.github2136.wardrobe.model.entity

/**
 * Created by 44569 on 2026/6/25
 * 网络解析
 */
data class NetworkResponse<T>(
    val data: T? = null,
    val code: Int = 1000,
    val message: String? = null,
)