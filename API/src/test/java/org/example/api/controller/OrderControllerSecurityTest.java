package org.example.api.controller;

import org.example.api.dto.OrderResponse;
import org.example.api.security.CustomUserDetailsService;
import org.example.api.security.JwtService;
import org.example.api.security.RestAccessDeniedHandler;
import org.example.api.security.RestAuthenticationEntityPoint;
import org.example.api.security.SecurityConfig;
import org.example.api.service.OrderService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
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
class OrderControllerSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    CustomUserDetailsService userDetailsService;

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

    @Test
    void create_withPermission_shouldReturn201() throws Exception {
        when(orderService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/orders")
                        .with(user("user").authorities(() -> "ORDER_CREATE"))
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

    @Test
    void create_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .with(user("user").authorities(List.of()))
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

    @Test
    void getById_withPermission_shouldReturn200() throws Exception {
        when(orderService.getById(1))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1")
                        .with(user("user").authorities(() -> "ORDER_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Acme"));
    }

    @Test
    void getById_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/orders/1")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());

        verify(orderService, never()).getById(1);
    }

    @Test
    void getAll_withPermission_shouldReturn200() throws Exception {
        when(orderService.getAll())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/orders")
                        .with(user("user").authorities(() -> "ORDER_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeName").value("John Doe"));
    }

    @Test
    void getAll_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());

        verify(orderService, never()).getAll();
    }

    @Test
    void byEmployee_withPermission_shouldReturn200() throws Exception {
        when(orderService.getByEmployee(3))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/orders/by-employee/3")
                        .with(user("user").authorities(() -> "ORDER_ADMIN_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(3));
    }

    @Test
    void byEmployee_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/orders/by-employee/3")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());

        verify(orderService, never()).getByEmployee(3);
    }

    @Test
    void between_withPermission_shouldReturn200() throws Exception {
        LocalDateTime from = LocalDateTime.of(2026, 8, 14, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 8, 14, 23, 59);

        when(orderService.getBetween(from, to))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/orders/between")
                        .param("from", "2026-08-14T00:00:00")
                        .param("to", "2026-08-14T23:59:00")
                        .with(user("user").authorities(() -> "ORDER_ADMIN_VIEW")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1));
    }

    @Test
    void between_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/orders/between")
                        .param("from", "2026-08-14T00:00:00")
                        .param("to", "2026-08-14T23:59:00")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());

        verify(orderService, never()).getBetween(any(), any());
    }

    @Test
    void update_withPermission_shouldReturn200() throws Exception {
        when(orderService.update(eq(1), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/orders/1")
                        .with(user("user").authorities(() -> "EMPLOYEE_UPDATE"))
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

    @Test
    void update_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/v1/orders/1")
                        .with(user("user").authorities(List.of()))
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

    @Test
    void delete_withPermission_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/1")
                        .with(user("user").authorities(() -> "ORDER_DELETE")))
                .andExpect(status().isNoContent());

        verify(orderService).delete(1);
    }

    @Test
    void delete_withoutPermission_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/1")
                        .with(user("user").authorities(List.of())))
                .andExpect(status().isForbidden());

        verify(orderService, never()).delete(1);
    }
}
