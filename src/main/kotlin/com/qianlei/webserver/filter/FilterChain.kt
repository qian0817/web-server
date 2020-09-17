package com.qianlei.webserver.filter

import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse

/**
 * 过滤器链接口
 * @author qianlei
 */
interface FilterChain {
    /**
     * 执行下一个过滤器的逻辑
     *
     */
    fun doFilter(request: HttpRequest, response: HttpResponse)
}