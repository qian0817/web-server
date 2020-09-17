package com.qianlei.webserver

import com.qianlei.webserver.http.HttpResponse
import com.qianlei.webserver.http.HttpResponseCode
import mu.KotlinLogging

/**
 * 在请求时出现异常处理
 *
 * @author qianlei
 */
object ExceptionHandler {
    private val log = KotlinLogging.logger { }
    fun handleException(e: Exception): HttpResponse {
        log.error(e) { "unresolved exception" }
        val body = StringBuilder(e.toString()).append("\r\n")
        val stackTrace = e.stackTrace
        for (element in stackTrace) {
            body.append(element.toString()).append("\r\n")
        }

        return HttpResponse(
            HttpResponseCode.INTERNAL_SERVER_ERROR,
            body = body.toString().toByteArray()
        )
    }
}