package org.example.api.controller;

import org.example.api.dto.OrderResponse;
import org.example.api.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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

@SpringBootTest()
@AutoConfigureMockMvc
class OrderControllerSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    private OrderResponse response;

    @BeforeEach
    void setup() {

        response = new OrderResponse(
                1,
                LocalDateTime.of(2026, 8, 14, 9, 30),
                2,
                "Acme",
                3,
                "John Doe"
        );
    }

    // CREATE - 201
    @Test
    @WithMockUser(authorities = {"ORDER_CREATE"})
    void create_withPermission_shouldReturn201() throws Exception {
        when(orderService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/orders")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "orderDate": "2026-08-14T09:30:00",
                                    "customerId": 2,
                                    "employeeId": 3
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1));

        verify(orderService).create(any());
    }

    // CREATE - 403
    @Test
    @WithMockUser(authorities = {})
    void create_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "orderDate": "2026-08-14T09:30:00",
                                    "customerId": 2,
                                    "employeeId": 3
                                }
                                """))
                .andExpect(status().isForbidden());

        verify(orderService, never()).create(any());
    }

    // GET ORDER BY ID - 200
    @Test
    @WithMockUser(authorities = {"ORDER_VIEW"})
    void getOrderById_withPermission_shouldReturn200() throws Exception {
        when(orderService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Acme"));

        verify(orderService).getById(1);
    }

    // GETALL - 200
    @Test
    @WithMockUser(authorities = {"ORDER_VIEW"})
    void getAll_withPermission_shouldReturn200() throws Exception {
        when(orderService.getAll())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/orders")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeName").value("John Doe"));

        verify(orderService).getAll();
    }

    // GETALL - 403
    @Test
    @WithMockUser(authorities = {})
    void getAll_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(orderService, never()).getAll();
    }

    // BYEMPLOYEE - 200
    @Test
    @WithMockUser(username ="user",authorities = {"ORDER_ADMIN_VIEW"})
    void byEmployee_withPermission_shouldReturn200() throws Exception {
        when(orderService.getByEmployee(3))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/orders/by-employee/3")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(3));

        verify(orderService).getByEmployee(3);
    }

    // BYEMPLOYEE - 403
    @Test
    @WithMockUser(username ="user",authorities = {"ORDER_VIEW"})
    void byEmployee_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/orders/by-employee/3")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(orderService, never()).getByEmployee(3);
    }

    // BETWEEN - 200
    @Test
    @WithMockUser(username ="admin",authorities = {"ORDER_ADMIN_VIEW"})
    void between_withPermission_shouldReturn200() throws Exception {
        LocalDateTime from = LocalDateTime.of(2026, 8, 14, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 8, 14, 23, 59);

        when(orderService.getBetween(from, to))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/orders/between")
                        .param("from", "2026-08-14T00:00:00")
                        .param("to", "2026-08-14T23:59:00")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1));

        verify(orderService).getBetween(from,to);
    }

    // UPDATE - 200
    @Test
    @WithMockUser(username ="admin",authorities = {"ORDER_UPDATE"})
    void update_withPermission_shouldReturn200() throws Exception {
        when(orderService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/orders/1")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "orderDate": "2026-08-14T09:30:00",
                                    "customerId": 2,
                                    "employeeId": 3
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));

        verify(orderService).update(eq(1), any());
    }

    // UPDATE - 403
    @Test
    @WithMockUser(username ="user",authorities = {})
    void update_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/v1/orders/1")
                        .with(testSecurityContext())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "orderDate": "2026-08-14T09:30:00",
                                    "customerId": 2,
                                    "employeeId": 3
                                }
                                """))
                .andExpect(status().isForbidden());

        verify(orderService, never()).update(eq(1), any());
    }

    // DELETE - 204
    @Test
    @WithMockUser(username ="admin",authorities = {"ORDER_DELETE"})
    void delete_withPermission_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/1")
                        .with(testSecurityContext()))
                .andExpect(status().isNoContent());

        verify(orderService).delete(1);
    }

    // DELETE - 403
    @Test
    @WithMockUser(username ="user",authorities = {})
    void delete_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/1")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());

        verify(orderService, never()).delete(1);
    }
}
