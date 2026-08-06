package org.example.api.controller;

import org.example.api.security.CustomUserDetailsService;
import org.example.api.security.JwtService;
import org.example.api.security.RestAccessDeniedHandler;
import org.example.api.security.RestAuthenticationEntityPoint;
import org.example.api.security.SecurityConfig;
import org.example.api.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
@ImportAutoConfiguration({
        SecurityAutoConfiguration.class,
        ServletWebSecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
})
@Import({
        SecurityConfig.class,
        RestAuthenticationEntityPoint.class,
        RestAccessDeniedHandler.class
})
@AutoConfigureMockMvc()
class EmployeeControllerSecurityTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    CustomUserDetailsService userDetailsService;

    //401 - no authen
    @Test
    void getAll_withoutToken_shouldReturn401()throws Exception{
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isUnauthorized());
    }

    //403-authen but no thor
    @Test
    void getAll_withoutPermission_shouldReturn403() throws Exception{
        mockMvc.perform(get("/api/v1/employees")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());
    }

    //200 - correct author
    @Test
    void getAll_withPermission_shouldReturn200() throws Exception{
        when(employeeService.getAll())
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/employees")
                        .with(user("user").authorities(() -> "EMPLOYEE_VIEW")))
                .andExpect(status().isOk());
    }
}
