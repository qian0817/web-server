package com.qianlei.webserver.http

/**
 * Http头中的ContentType属性
 */
enum class HttpContentType(val type: String) {
    TEXT_CSS("text/css"),
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    TEXT_HTML("text/html"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    TEXT_PLAIN("text/plain"),
    IMAGE_X_ICON("image/x-icon"),
    IMAGE_SVG_XML("image/svg+xml");

    companion object {
        fun findContentTypeByExtensionName(extensionName: String?): HttpContentType {
            if (extensionName == null) {
                return TEXT_PLAIN
            }
            return when (extensionName) {
                "png" -> IMAGE_PNG
                "jpg", "jpeg" -> IMAGE_JPEG
                "html", "htm" -> TEXT_HTML
                "js" -> APPLICATION_JAVASCRIPT
                "css" -> TEXT_CSS
                "ico" -> IMAGE_X_ICON
                "svg" -> IMAGE_SVG_XML
                else -> TEXT_PLAIN
            }
        }
    }
}