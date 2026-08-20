package org.example.api.controller;

import org.example.api.dto.CustomerResponse;
import org.example.api.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    private CustomerResponse response;

    @BeforeEach
    void setup() {
        response = new CustomerResponse(
                "John",
                "Doe",
                "Quan7",
                "HCM",
                "75000",
                "VN"
        );
    }

    // CREATE - 201
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_CREATE"})
    void create_withPermission_shouldReturn201() throws Exception {
        when(customerService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                        .with(testSecurityContext())
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value("John"));

        verify(customerService).create(any());
    }

    // CREATE - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void create_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(post("/api/v1/customers")
                        .with(testSecurityContext())
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

    // GETALL - 200
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_VIEW"})
    void getAll_withPermission_shouldReturn200() throws Exception {
        when(customerService.getAll())
                .thenReturn(List.of(response, response));

        mockMvc.perform(get("/api/v1/customers")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John"));

        verify(customerService).getAll();
    }

    // GETALL - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void getAll_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/customers")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(customerService, never()).getAll();
    }

    // GETBYID - 200
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_VIEW"})
    void getById_withPermission_shouldReturn200() throws Exception {
        when(customerService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/1")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"));

        verify(customerService).getById(1);
    }

    // GETBYID - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void getById_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/customers/1")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(customerService, never()).getById(1);
    }

    // SEARCHBYNAME - 200
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_VIEW"})
    void searchByName_withPermission_shouldReturn200() throws Exception {
        when(customerService.searchByName("John"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/customers/search")
                        .param("name", "John")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John"));

        verify(customerService).searchByName("John");
    }

    // SEARCHBYNAME - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void searchByName_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/customers/search")
                        .param("name", "John")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(customerService, never()).searchByName("John");
    }

    // UPDATE - 200
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_UPDATE"})
    void update_withPermission_shouldReturn200() throws Exception {
        when(customerService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/customers/1")
                        .with(testSecurityContext())
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

        verify(customerService).update(eq(1), any());
    }

    // UPDATE - 400 - VALIDATION
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_UPDATE"})
    void update_withPermissionAndBlankCustomerName_shouldReturn400() throws Exception {
        mockMvc.perform(put("/api/v1/customers/1")
                        .with(testSecurityContext())
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
                .andExpect(jsonPath("$.message").value("Validation fail"))
                .andExpect(jsonPath("$.fieldErrors.customerName").value("Customer name is required"));

        verify(customerService, never()).update(eq(1), any());
    }

    // UPDATE - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void update_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/v1/customers/1")
                        .with(testSecurityContext())
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

        verify(customerService, never()).update(eq(1), any());
    }

    // DELETE - 204 - NO CONTENT
    @Test
    @WithMockUser(username = "admin", authorities = {"CUSTOMER_DELETE"})
    void delete_withPermission_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
                        .with(testSecurityContext()))
                .andExpect(status().isNoContent());

        verify(customerService).delete(1);
    }

    // DELETE - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void delete_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(customerService, never()).delete(1);
    }
}
