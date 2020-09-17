package com.qianlei.webserver.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

/**
 * JSON 配置文件解析器
 *
 * @author qianlei
 */
class JsonServerConfigParser : IServerConfigParser {

    override fun parse(filepath: String): ServerConfig {
        return jacksonObjectMapper().readValue(File(filepath), ServerConfig::class.java)
    }
}