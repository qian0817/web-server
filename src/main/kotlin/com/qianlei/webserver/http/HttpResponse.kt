package com.qianlei.webserver.http

import java.nio.charset.Charset

/**
 * HTTP响应
 *
 * @author qianlei
 *
 */
data class HttpResponse(
    var responseCode: HttpResponseCode = HttpResponseCode.OK,
    var responseParams: MutableMap<String, MutableList<String>> = hashMapOf(),
    var body: ByteArray = byteArrayOf(),
) {
    /**
     * 将响应转化为字节数组
     */
    fun toByteArray(charSet: Charset = Charsets.UTF_8): ByteArray {
        val headBuilder = StringBuilder()
        headBuilder.append("HTTP/1.1 ${responseCode.code} ${responseCode.desc}").append("\r\n")
        responseParams.forEach { (t, u) ->
            headBuilder.append(t).append(": ").append(u.joinToString("; ")).append("\r\n")
        }
        headBuilder.append("\r\n")
        val headerRaw = headBuilder.toString().toByteArray(charSet)
        val response = ByteArray(headerRaw.size + body.size)
        System.arraycopy(headerRaw, 0, response, 0, headerRaw.size)
        System.arraycopy(body, 0, response, headerRaw.size, body.size)
        return response
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpResponse

        if (responseCode != other.responseCode) return false
        if (responseParams != other.responseParams) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = responseCode.hashCode()
        result = 31 * result + responseParams.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }
}