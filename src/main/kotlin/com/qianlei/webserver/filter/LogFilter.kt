package com.qianlei.webserver.filter

import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse
import mu.KotlinLogging

/**
 * 日志过滤器
 *
 * 对请求和响应记录日志
 *
 * @author qianlei
 */
class LogFilter : Filter {
    private val log = KotlinLogging.logger { }

    override fun doFilter(request: HttpRequest, response: HttpResponse, chain: FilterChain) {
        log.debug { "request: ${request.method} ${request.path} ${request.httpVersion}" }
        chain.doFilter(request, response)
        log.debug { "response: ${response.responseCode}" }
    }

    override fun order(): Int {
        return Int.MIN_VALUE
    }
}