package com.qianlei.webserver.http

import mu.KotlinLogging
import java.net.URLDecoder
import java.nio.charset.Charset

/**
 * HTTP请求
 *
 * @author qianlei
 */
data class HttpRequest(
    val method: String,
    val path: String,
    val httpVersion: String,
    //TODO header与body分离
    val header: Map<String, List<String>>
) {

    companion object {
        private val log = KotlinLogging.logger { }

        /**
         * 解析请求信息
         * 返回null说明请求内容不合法
         */
        fun parse(byteArray: ByteArray, charset: Charset = Charsets.UTF_8): HttpRequest? {
            val lines = String(byteArray, charset).intern().trim().split("\r\n")
            //第一行处理方法，路径和HTTP版本参数
            val firstLineParams = lines[0].split(" ")
            if (firstLineParams.size < 3) {
                return null
            }
            val method = firstLineParams[0]
            val path = URLDecoder.decode(firstLineParams[1], charset)
            val version = firstLineParams[2]
            log.debug("request:$method $path $version")
            val header = lines.mapIndexedNotNull { index, line ->
                if (index != 0 && line.isNotBlank()) {
                    val lineParams = line.split(":")
                    if (lineParams.size > 2) {
                        val key = lineParams[0]
                        val value = lineParams[1].split(";")
                        return@mapIndexedNotNull key to value
                    }
                }
                null
            }.toMap()
            return HttpRequest(method, path, version, header)
        }
    }
}