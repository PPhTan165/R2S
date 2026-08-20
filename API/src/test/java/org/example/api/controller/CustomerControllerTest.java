package org.example.api.controller;

import org.example.api.dto.CustomerResponse;
import org.example.api.security.JwtAuthenticationFilter;
import org.example.api.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private CustomerResponse response;

    @BeforeEach
    void setup() {
        response = new CustomerResponse(
                "John",
                "Doe",
                "Quan7",
                "HCM",
                "75000",
                "VN");
    }

    // CREATE - 201
    @Test
    void create_shouldReturn201() throws Exception {
        when(customerService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerName":"John",
                                    "contactName":"Doe"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    // GETALL - 200
    @Test
    void getAll_shouldReturn200() throws Exception {
        when(customerService.getAll())
                .thenReturn(List.of(response, response));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John"));
    }

    // GETBYID - 200
    @Test
    void getById_shouldReturn200() throws Exception {
        when(customerService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"));
    }

    // SEARCHBYNAME - 200
    @Test
    void searchByName_shouldReturnCustomers() throws Exception{
        when(customerService.searchByName("John"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/customers/search")
                .param("name","John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("John"));

    }

    // UPDATE - 200
    @Test
    void update_shouldReturn200() throws Exception {

        when(customerService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/customers/1")
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

        verify(customerService).update(eq(1),any());
    }

    // DELETE - 204
    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isNoContent());
    }
}
