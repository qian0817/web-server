package com.qianlei.webserver.filter

import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse
import com.qianlei.webserver.servlet.Servlet

/**
 * 过滤器链
 *
 * @author
 */
class ApplicationFilterChain(
    private val servlet: Servlet?,
    private val filters: List<Filter>
) : FilterChain {
    //当前执行到了哪个filter
    private var pos = -1

    override fun doFilter(request: HttpRequest, response: HttpResponse) {
        pos++
        if (pos < filters.size) {
            filters[pos].doFilter(request, response, this)
        } else {
            if (servlet == null) {
                handleNotFound(request, response)
            } else {
                servlet.service(request, response)
            }
        }
    }

    private fun handleNotFound(request: HttpRequest, response: HttpResponse) {
        response.body = "PATH ${request.path} NOT FOUND".toByteArray()
    }
}