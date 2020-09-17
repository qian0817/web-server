package com.qianlei.webserver.config

interface IServerConfigParser {
    /**
     * 解析并返回[filepath]文件的配置
     */
    fun parse(filepath: String): ServerConfig
}