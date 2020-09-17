package com.qianlei.webserver.servlet

import com.qianlei.webserver.config.ServerConfig
import com.qianlei.webserver.config.ServletConfig
import com.qianlei.webserver.http.HttpContentType
import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse
import com.qianlei.webserver.http.HttpResponseCode
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files

/**
 * 处理静态资源的Servlet
 *
 * @author qianlei
 */
class StaticResourceServlet : Servlet {
    private val log = KotlinLogging.logger { }

    //编码
    private lateinit var encoding: String

    //默认页面
    private lateinit var defaultPage: String

    //根路径在系统的哪个目录
    private lateinit var root: String

    override fun init(serverConfig: ServerConfig, servletConfig: ServletConfig) {
        encoding = serverConfig.encoding
        defaultPage = servletConfig.param["index"] as? String ?: "index.html"
        root = servletConfig.param["root"] as String
    }

    override fun doGet(request: HttpRequest, response: HttpResponse) {
        //获取路径
        val path = if (request.path.endsWith("/")) request.path + defaultPage else request.path
        log.debug { "request path:$path" }
        //获取扩展名
        val extensionName = path.substring(path.lastIndexOf(".") + 1)
        //根据扩展名获取内容类型
        val contentType = HttpContentType.findContentTypeByExtensionName(extensionName)
        val file = File("$root\\$path")
        //判断文件存在
        if (!file.exists() || !file.isFile) {
            handleFileNotExist(response, path)
            return
        }
        val content = Files.readAllBytes(file.toPath())
        response.responseParams["Content-Type"] = arrayListOf(contentType.type, "charset=${encoding}")
        response.responseParams["Accept-Ranges"] = arrayListOf("bytes")
        response.body = content
    }

    private fun handleFileNotExist(response: HttpResponse, path: String) {
        log.debug { "file $path NOT FOUND" }
        response.responseCode = HttpResponseCode.NOT_FOUND
        response.body = "FILE $path NOT FOUND".toByteArray()
    }
}