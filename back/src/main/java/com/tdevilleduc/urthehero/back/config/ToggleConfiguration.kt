package com.tdevilleduc.urthehero.back.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.togglz.core.manager.EnumBasedFeatureProvider
import org.togglz.core.spi.FeatureProvider
import org.togglz.core.user.NoOpUserProvider
import org.togglz.core.user.UserProvider

@Configuration
class ToggleConfiguration {
    @Bean
    fun featureProvider(): FeatureProvider {
        return EnumBasedFeatureProvider(Features::class.java)
    }
}