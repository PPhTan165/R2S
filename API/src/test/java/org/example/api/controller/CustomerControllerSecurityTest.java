package org.example.api.controller;

import org.example.api.dto.CustomerResponse;
import org.example.api.security.CustomUserDetailsService;
import org.example.api.security.JwtService;
import org.example.api.security.RestAccessDeniedHandler;
import org.example.api.security.RestAuthenticationEntityPoint;
import org.example.api.security.SecurityConfig;
import org.example.api.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
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
@AutoConfigureMockMvc
class CustomerControllerSecurityTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    CustomUserDetailsService userDetailsService;

    private CustomerResponse response;

    @BeforeEach
    void setup(){
        response = new CustomerResponse(
                "John",
                "Doe",
                "Quan7",
                "HCM",
                "75000",
                "VN");
    }
    //CREATE - 201
    @Test
    void create_withPermission_shouldReturn201() throws Exception {
        when(customerService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                        .with(user("user").authorities(() -> "CUSTOMER_CREATE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerName": "John",
                                    "contactName": "Doe",
                                    "address": "Quan7",
                                    "city": "HCM",
                                    "postalCode": "75000",
                                    "country": "VN"
                                }
                                """))
                .andExpect(status().isCreated());

        verify(customerService).create(any());
    }

    //CREATE - 403
    @Test
    void create_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(post("/api/v1/customers")
                        .with(user("user").authorities(List.of()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerName": "John",
                                    "contactName": "Doe",
                                    "address": "Quan7",
                                    "city": "HCM",
                                    "postalCode": "75000",
                                    "country": "VN"
                                }
                                """))
                .andExpect(status().isForbidden());

        verify(customerService, never()).create(any());
    }

    //GET ALL - 200
    @Test
    void getAll_withPermission_shouldReturn200() throws Exception {
        when(customerService.getAll())
                .thenReturn(List.of(response, response));

        mockMvc.perform(get("/api/v1/customers")
                        .with(user("user").authorities(()->"CUSTOMER_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John"));
    }

    //GET ALL - 403
    @Test
    void getAll_withoutPermission_shouldReturn403() throws Exception {
        when(customerService.getAll())
                .thenReturn(List.of(response, response));

        mockMvc.perform(get("/api/v1/customers")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());
    }

    //GET BY ID - 200
    @Test
    void getById_withPermission_shouldReturn200() throws Exception {
        when(customerService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/1")
                        .with(user("user").authorities(()->"CUSTOMER_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"));
    }

    //GET BY ID - 403
    @Test
    void getById_withoutPermission_shouldReturn200() throws Exception {
        when(customerService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/1")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());
    }

    //SEARCH BY NAME - 200
    @Test
    void searchByName_withPermission_shouldReturn200() throws Exception{
        when(customerService.searchByName("John"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/customers/search")
                        .param("name","John")
                        .with(user("user").authorities(()->"CUSTOMER_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John"));

    }

    //SEARCH BY NAME - 403
    @Test
    void searchByName_withoutPermission_shouldReturn403() throws Exception{
        when(customerService.searchByName("John"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/customers/search")
                        .param("name","John")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());

    }

    //UPDATE - 200
    @Test
    void update_withPermission_shouldReturn200() throws Exception {

        when(customerService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/customers/1")
                        .with(user("user").authorities(()->"CUSTOMER_UPDATE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                      "customerName": "John",
                                      "contactName": "Doe",
                                      "address": "Quan7",
                                      "city": "HCM",
                                      "postalCode": "75000",
                                      "country": "VN"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"));

    }

    //UPDATE - 400
    @Test
    void update_withPermissionAndBlankCustomerName_shouldReturn400() throws Exception {
        mockMvc.perform(put("/api/v1/customers/1")
                        .with(user("user").authorities(()->"CUSTOMER_UPDATE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                      "customerName": "",
                                      "contactName": "Doe",
                                      "address": "Quan7",
                                      "city": "HCM",
                                      "postalCode": "75000",
                                      "country": "VN"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.customerName").value("Customer name is required"));

        verify(customerService, never()).update(eq(1), any());
    }

    //UPDATE - 403
    @Test
    void update_withoutPermission_shouldReturn403() throws Exception {

        when(customerService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/customers/1")
                        .with(user("user").authorities(List.of()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                      "customerName": "John",
                                      "contactName": "Doe",
                                      "address": "Quan7",
                                      "city": "HCM",
                                      "postalCode": "75000",
                                      "country": "VN"
                                }
                                """))
                .andExpect(status().isForbidden());

    }

    //DELETE - 204
    @Test
    void delete_withPermission_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
                        .with(user("user").authorities(()->"CUSTOMER_DELETE")))
                .andExpect(status().isNoContent());
    }

    //DELETE - 403
    @Test
    void delete_withoutPermission_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());
    }
}
