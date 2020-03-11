package com.tdevilleduc.urthehero.back.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["classpath:/swagger/swagger-messages_fr.properties"])
internal class SwaggerConfig {

}