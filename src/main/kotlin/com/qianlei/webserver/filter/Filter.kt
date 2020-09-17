package com.qianlei.webserver.filter

import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse

/**
 * 过滤器
 */
interface Filter {
    /**
     * 执行过滤器逻辑
     */
    fun doFilter(request: HttpRequest, response: HttpResponse, chain: FilterChain)

    /**
     * 过滤器顺序
     */
    fun order(): Int
}