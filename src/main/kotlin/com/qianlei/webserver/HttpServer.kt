package com.qianlei.webserver

import com.qianlei.webserver.config.ServerConfig
import com.qianlei.webserver.config.ServerConfigSource
import com.qianlei.webserver.filter.ApplicationFilterChain
import com.qianlei.webserver.http.HttpRequest
import com.qianlei.webserver.http.HttpResponse
import com.qianlei.webserver.servlet.Servlet
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import mu.KotlinLogging
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

/**
 * HTTP服务器
 *
 * @author qianlei
 */
class HttpServer(private val config: ServerConfig) {
    private val log = KotlinLogging.logger { }
    private val requestChannel = Channel<Pair<HttpRequest, SocketChannel>>()

    /**
     * 存放所有servlet信息
     * key为servlet的路径
     * value为对应的servlet类
     */
    private val servlets: List<Pair<String, Servlet>>

    /**
     * 所有过滤器信息
     */
    private val filters = config.filters()

    init {
        //根据配置信息从中获取到所有的servlet
        servlets = config.servlets.map {
            val clazz = Class.forName(it.name)
            val servlet = clazz.getDeclaredConstructor().newInstance() as Servlet
            servlet.init(config, it)
            it.path to servlet
        }
        GlobalScope.launch(Dispatchers.IO) {
            for (c in requestChannel) {
                c.second.use {
                    try {
                        c.second.write(ByteBuffer.wrap(handle(c.first)))
                    } catch (e: Exception) {
                        log.error { e }
                    }
                }
            }
        }
    }

    private val selector = Selector.open()
    private val serverSocketChannel =
        ServerSocketChannel.open().apply {
            configureBlocking(false)
            bind(InetSocketAddress("127.0.0.1", 3000))
        }

    fun listen() {
        log.info { "web server is running on port ${config.port}" }
        log.info { "encoding is ${config.encoding}" }
        serverSocketChannel.register(selector, serverSocketChannel.validOps())
        while (true) {
            selector.select()
            val selectedKeys = selector.selectedKeys()
            val itr = selectedKeys.iterator()
            while (itr.hasNext()) {
                val key = itr.next()
                itr.remove()
                if (!key.isValid) {
                    continue
                } else if (key.isAcceptable) {
                    val client = serverSocketChannel.accept()
                    client.configureBlocking(false)
                    client.register(selector, SelectionKey.OP_READ)
                    log.debug { "accept socket from ${client.remoteAddress}" }
                } else if (key.isReadable) {
                    val client = key.channel() as SocketChannel
                    //读取请求内容
                    val byteList = arrayListOf<Byte>()
                    val buffer = ByteBuffer.allocate(256)
                    buffer.limit(buffer.capacity())
                    while (true) {
                        val offset = client.read(buffer)
                        if (offset <= 0) {
                            break
                        }
                        byteList.addAll(ByteArray(offset) { buffer.array()[it] }.toList())
                        buffer.flip()
                    }
                    GlobalScope.launch(Dispatchers.IO) {
                        val request = withContext(Dispatchers.Default) {
                            HttpRequest.parse(byteList.toByteArray())
                        }
                        if (request != null) {
                            requestChannel.send(request to client)
                        } else {
                            client.close()
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理请求
     */
    private fun handle(request: HttpRequest): ByteArray {
        return try {
            val response = HttpResponse()
            // 找到与路径匹配的servlet
            val servlet = servlets.find { request.path.matches(it.first.toRegex()) }?.second
            val filterChain = ApplicationFilterChain(servlet, filters)
            filterChain.doFilter(request, response)
            response
        } catch (e: Exception) {
            ExceptionHandler.handleException(e)
        }.toByteArray()
    }
}