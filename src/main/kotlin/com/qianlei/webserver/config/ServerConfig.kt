package com.qianlei.webserver.config

import com.qianlei.webserver.filter.Filter

/**
 * 服务器配置类
 *
 * @author qianlei
 */
data class ServerConfig(
    val port: Int = 3000,
    val encoding: String = "UTF-8",
    private val filters: List<String> = listOf(),
    val servlets: List<ServletConfig> = listOf()
) {
    fun filters(): List<Filter> =
        filters.map {
            val clazz = Class.forName(it)
            clazz.getDeclaredConstructor().newInstance() as Filter
        }
}