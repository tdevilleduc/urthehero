package com.tdevilleduc.urthehero.back.config

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.togglz.junit5.AllEnabled
import org.togglz.testing.TestFeatureManager

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
@WebAppConfiguration
class ToggleConfigurationTest : AbstractTest() {

    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    @AllEnabled(Features::class)
    fun test_disablePageFeature_thenSuccess(featureManager: TestFeatureManager) {
        assertTrue(Features.PAGE_FEATURE.isActive)
        featureManager.disable(Features.PAGE_FEATURE)
        assertFalse(Features.PAGE_FEATURE.isActive)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/page/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    @AllEnabled(Features::class)
    fun test_disablePersonFeature_thenSuccess(featureManager: TestFeatureManager) {
        assertTrue(Features.PERSON_FEATURE.isActive)
        featureManager.disable(Features.PERSON_FEATURE)
        assertFalse(Features.PERSON_FEATURE.isActive)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}