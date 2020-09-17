package com.qianlei.webserver.filter

import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse

/**
 * 对响应头加上Server和Content-Length
 *
 * @author qianlei
 */
class ResponseHeaderFilter : Filter {
    override fun doFilter(request: HttpRequest, response: HttpResponse, chain: FilterChain) {
        chain.doFilter(request, response)
        response.responseParams["Server"] = arrayListOf("simple-web-server")
        response.responseParams["Content-Length"] = arrayListOf(response.body.size.toString())
    }

    override fun order(): Int {
        return Int.MIN_VALUE + 1
    }
}