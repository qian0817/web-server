package com.qianlei.webserver.filter

import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream


/**
 * GZIP压缩过滤器
 *
 * @author qianlei
 */
class GZipFilter : Filter {
    override fun doFilter(request: HttpRequest, response: HttpResponse, chain: FilterChain) {
        chain.doFilter(request, response)
        val out = ByteArrayOutputStream()
        val gzip = GZIPOutputStream(out)
        gzip.write(response.body)
        gzip.close()
        response.body = out.toByteArray()
        response.responseParams["Content-Encoding"] = arrayListOf("gzip")
    }

    override fun order(): Int {
        return Int.MIN_VALUE + 2
    }
}