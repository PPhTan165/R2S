package org.example.api.controller;

import org.example.api.dto.EmployeeResponse;
import org.example.api.security.JwtAuthenticationFilter;
import org.example.api.service.EmployeeService;
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

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private EmployeeResponse response;

    @BeforeEach
    void setup(){
        response = new EmployeeResponse();
        response.setEmployeeId(1);
        response.setFirstName("John");
    }

    // CREATE - 201
    @Test
    void create_shouldReturn201()throws Exception{
        when(employeeService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "firstName": "John",
                        "lastName": "Doe"
                        }
                        """))
                .andExpect(status().isCreated());
    }

    // GETALL - 200
    @Test
    void getAll_shouldReturn200() throws Exception{
        when(employeeService.getAll())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    // GETBYID - 200
    @Test
    void getById_shouldReturn200()throws Exception{
        when(employeeService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    // DELETE - 204
    @Test
    void delete_shouldReturn204()throws Exception{
        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
    }

    // UPDATE - 200
    @Test
    void update_shouldReturn200()throws Exception{
        when(employeeService.update(eq(1),any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "firstName": "John",
                        "lastName": "Kai"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
        verify(employeeService).update(eq(1),any());
    }

}
