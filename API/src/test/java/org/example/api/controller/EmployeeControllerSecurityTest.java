package org.example.api.controller;

import org.example.api.dto.EmployeeResponse;
import org.example.api.service.EmployeeService;
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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
class EmployeeControllerSecurityTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    private EmployeeResponse response;

    @BeforeEach
    void setup() {
        response = new EmployeeResponse();
        response.setEmployeeId(1);
        response.setFirstName("John");
        response.setLastName("Doe");
    }

    // GETALL - 401
    @Test
    @WithMockUser(username = "user")
    void getAll_withoutToken_shouldReturn401()throws Exception{
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isUnauthorized());
        verify(employeeService,never()).getAll();
    }

    // GETALL - 403
    @Test
    @WithMockUser(roles = "USER", authorities = {})
    void getAll_withoutPermission_shouldReturn403() throws Exception{
        mockMvc.perform(get("/api/v1/employees")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(employeeService,never()).getAll();
    }

    // GETALL - 200
    @Test
   @WithMockUser(authorities = {"EMPLOYEE_VIEW"})
    void getAll_withPermission_shouldReturn200() throws Exception{
        when(employeeService.getAll())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/employees")
//                       .with(user("admin").authorities(()->"EMPLOYEE_VIEW"))
                                .with(testSecurityContext())
                )
                .andExpect(status().isOk());

        verify(employeeService).getAll();
    }

    // CREATE - 201
    @Test
    @WithMockUser(username = "admin", authorities = {"EMPLOYEE_CREATE"})
    void create_withPermission_shouldReturn201() throws Exception {
        when(employeeService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/employees")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(employeeService).create(any());
    }

    // CREATE - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void create_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(post("/api/v1/employees")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe"
                                }
                                """))
                .andExpect(status().isForbidden());

        verify(employeeService, never()).create(any());
    }

    // UPDATE - 200
    @Test
    @WithMockUser(username = "admin", authorities = {"EMPLOYEE_UPDATE"})
    void update_withPermission_shouldReturn200() throws Exception {
        when(employeeService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/employees/1")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(employeeService).update(eq(1), any());
    }

    // UPDATE - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void update_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/v1/employees/1")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe"
                                }
                                """))
                .andExpect(status().isForbidden());

        verify(employeeService, never()).update(eq(1), any());
    }

    // DELETE - 204
    @Test
    @WithMockUser(username = "admin", authorities = {"EMPLOYEE_DELETE"})
    void delete_withPermission_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1")
                        .with(testSecurityContext()))
                .andExpect(status().isNoContent());

        verify(employeeService).delete(1);
    }

    // DELETE - 403
    @Test
    @WithMockUser(username = "user", authorities = {})
    void delete_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(employeeService, never()).delete(1);
    }
}
