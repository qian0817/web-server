package com.qianlei.webserver.config

/**
 * 每个servlet的配置
 *
 */
data class ServletConfig(
    /**
     * servlet类
     */
    val name: String,
    /**
     * 请求的路径
     */
    val path: String,
    /**
     * 自定义字段，如果serlvet需要可在此定义
     */
    val param: Map<String, Any> = mapOf()
)
