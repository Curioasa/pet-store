package com.rbc.controllers;

import com.rbc.context.PetTestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PetTestContext.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void userTest() throws Exception {
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(
                (Principal) () -> "testUser",
                null,
                Arrays.asList(new GrantedAuthority[]{
                        (GrantedAuthority) () -> "ROLE_ADMIN"}));
        mockMvc.perform(get("/user").principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testUser")))
                .andExpect(jsonPath("$.roles", is("[ROLE_ADMIN]")));
    }
}
