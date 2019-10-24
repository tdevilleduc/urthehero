package com.tdevilleduc.urthehero.back.actuator;

import com.tdevilleduc.urthehero.back.BackApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author xpax624
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {BackApplication.class})
public class ITActuatorTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Mock mvc pour les appels Rest
     */
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void health() throws Exception{
        final MvcResult result = this.mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertNotNull(response);
        assertThat(response.getContentAsString()).contains("\"status\":\"UP\"");
    }

}
