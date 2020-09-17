package com.qianlei.webserver

import com.qianlei.webserver.config.ServerConfigSource
import mu.KotlinLogging


fun main(args: Array<String>) {
    val log = KotlinLogging.logger { }
    val configPath = if (args.isEmpty()) {
        log.warn { "use default config" }
        HttpServer::class.java.classLoader.getResource("config.json")!!.path
    } else {
        log.info { "config path is ${args[0]}" }
        args[0]
    }
    val config = ServerConfigSource.load(configPath)
    val server = HttpServer(config)
    server.listen()
}
