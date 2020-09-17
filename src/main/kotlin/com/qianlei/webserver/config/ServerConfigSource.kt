package com.qianlei.webserver.config

object ServerConfigSource {
    fun load(filepath: String): ServerConfig {
        val configParser = when (getExtensionName(filepath)) {
            "json" -> JsonServerConfigParser()
            else -> TODO()
        }
        return configParser.parse(filepath)
    }

    private fun getExtensionName(filepath: String): String {
        return filepath.substring(filepath.lastIndexOf('.') + 1)
    }
}