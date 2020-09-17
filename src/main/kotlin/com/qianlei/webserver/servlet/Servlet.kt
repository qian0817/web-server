package com.qianlei.webserver.servlet

import com.qianlei.webserver.config.ServerConfig
import com.qianlei.webserver.config.ServletConfig
import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse
import com.qianlei.webserver.http.HttpResponseCode


/**
 * servlet 接口定义
 *
 * @author qianlei
 */
interface Servlet {
    /**
     * 处理方法
     */
    fun service(request: HttpRequest, response: HttpResponse) {
        when (request.method.toLowerCase()) {
            "get" -> doGet(request, response)
            "post" -> doPost(request, response)
            else -> handleMethodNotAllow(response)
        }
    }

    fun doGet(request: HttpRequest, response: HttpResponse) {
        handleMethodNotAllow(response)
    }

    fun doPost(request: HttpRequest, response: HttpResponse) {
        handleMethodNotAllow(response)
    }

    fun handleMethodNotAllow(response: HttpResponse) {
        response.responseCode = HttpResponseCode.METHOD_NOT_ALLOWED
        response.body = "Method is not allow".toByteArray()
    }

    fun init(serverConfig: ServerConfig, servletConfig: ServletConfig)
}