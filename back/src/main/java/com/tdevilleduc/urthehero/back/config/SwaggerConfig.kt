package com.tdevilleduc.urthehero.back.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["classpath:/swagger/swagger-messages_fr.properties"])
internal class SwaggerConfig {

    /**
     * Create default ApiInfo
     * @param properties
     * @return
     */
    @Bean
    fun createApiInfo(): OpenAPI? {
        return OpenAPI()
//                .info(Info()
//                        .title(properties.getTitle())
//                        .description(properties.getDescription())
//                        .version(properties.getVersion())
//                        .license(License()
//                                .name(properties.getLicense())
//                                .url(properties.getLicenseUrl())))
//        val contact: ApiInfoProperties.Contact = properties.getContact()
//        if (contact != null) {
//            openAPI.info.contact(Contact()
//                    .name(properties.getContact().getName())
//                    .url(properties.getContact().getUrl())
//                    .email(properties.getContact().getEmail()))
//        }
    }

}