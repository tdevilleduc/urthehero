package com.tdevilleduc.urthehero.back.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = [
    "classpath:/swagger/swagger-messages_fr.properties",
    "classpath:/swagger/swagger-model-messages_fr.properties"
])
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
internal class SwaggerConfig {

    /**
     * Create default ApiInfo
     * @param properties
     * @return
     */
    @Bean
    fun springShopOpenAPI(): OpenAPI? {
        return OpenAPI()
                .info(Info().title("SpringShop API")
                        .description("Spring shop sample application")
                        .version("v0.0.1")
                        .license(License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
    }

}