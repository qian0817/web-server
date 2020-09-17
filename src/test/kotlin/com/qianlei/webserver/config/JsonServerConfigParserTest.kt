package com.qianlei.webserver.config

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class JsonServerConfigParserTest {
    private val parser = JsonServerConfigParser()

    @Test
    fun parse() {
        val config = parser.parse(JsonServerConfigParserTest::class.java.classLoader.getResource("config.json")!!.path)
        assertEquals(config.encoding,"UTF-8")
        assertEquals(config.port,3000)
        assertEquals(config.servlets.size,1)
        assertEquals(config.filters().size,3)
    }
}