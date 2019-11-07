package com.tdevilleduc.urthehero.back.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
@PropertySource(value = {
        "classpath:/swagger/swagger-messages_fr.properties"
})
class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(apis())
                .paths(paths())
                .build()
                .apiInfo(apiInfo());
    }

    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/basic-error-controller.*"));
    }

    private Predicate<RequestHandler> apis() {
        return Predicates.not(RequestHandlerSelectors.basePackage("org.springframework"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "URtheHero API",
                "BackEnd API for URtheHero application.",
                "API 0.0.1-SNAPSHOT",
                "Terms of service",
                new Contact("Thomas Devile-Duc", "urthehero.tdevilleduc.com", "urthehero.app@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
