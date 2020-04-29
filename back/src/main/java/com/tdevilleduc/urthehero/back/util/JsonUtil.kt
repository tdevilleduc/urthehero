package com.tdevilleduc.urthehero.back.util

import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtil {

    companion object {
        @JvmStatic
        fun asJsonString(obj: Any): String {
            return try {
                val mapper = ObjectMapper()
                mapper.writeValueAsString(obj)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}