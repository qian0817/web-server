package com.qianlei.webserver.http

/**
 * HTTP状态码
 *
 * @author qianlei
 */
enum class HttpResponseCode(val code: Int, val desc: String) {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");
}