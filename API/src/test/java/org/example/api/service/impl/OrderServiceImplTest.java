package org.example.api.service.impl;

import org.example.api.dto.OrderRequest;
import org.example.api.dto.OrderResponse;
import org.example.api.entity.Customer;
import org.example.api.entity.Employee;
import org.example.api.entity.Order;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.CustomerRepository;
import org.example.api.repository.EmployeeRepository;
import org.example.api.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    private Order order;
    private Customer customer;
    private Employee employee;
    private LocalDateTime orderDate;

    @BeforeEach
    void setup() {

        customer = new Customer();
        customer.setCustomerId(2);
        customer.setCustomerName("Acme");

        employee = new Employee();
        employee.setEmployeeId(3);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        order = new Order();
        order.setOrderId(1);
        order.setOrderDate(null);
        order.setCustomer(customer);
        order.setEmployee(employee);
    }

    // CREATE - SAVE ORDER
    @Test
    void create_whenCustomerAndEmployeeExist_shouldSaveOrder() {
        OrderRequest request = new OrderRequest();
        request.setOrderDate(null);
        request.setCustomerId(2);
        request.setEmployeeId(3);

        when(customerRepository.findById(2))
                .thenReturn(Optional.of(customer));
        when(employeeRepository.findById(3))
                .thenReturn(Optional.of(employee));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order savedOrder = invocation.getArgument(0);
                    savedOrder.setOrderId(1);
                    return savedOrder;
                });

        OrderResponse res = orderService.create(request);

        assertEquals(1, res.getOrderId());
        assertNotNull(res.getOrderDate()); // check bảo đảm orderDate không null

        assertEquals("Acme", res.getCustomerName());
        assertEquals("John Doe", res.getEmployeeName());
        verify(orderRepository).save(any(Order.class));
    }

    // CREATE - THROW EXCEPTION
    @Test
    void create_whenCustomerNotFound_shouldThrowException() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(99);
        request.setEmployeeId(3);

        when(customerRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.create(request));

        verify(employeeRepository, never()).findById(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    // UPDATE - UPDATE ORDER
    @Test
    void update_whenOrderCustomerAndEmployeeExist_shouldUpdateOrder() {
        LocalDateTime updatedDate = LocalDateTime.of(2026, 8, 15, 10, 0);
        OrderRequest request = new OrderRequest();
        request.setOrderDate(updatedDate);
        request.setCustomerId(2);
        request.setEmployeeId(3);

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));
        when(customerRepository.findById(2))
                .thenReturn(Optional.of(customer));
        when(employeeRepository.findById(3))
                .thenReturn(Optional.of(employee));
        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        OrderResponse res = orderService.update(1, request);

        assertEquals(1, res.getOrderId());
        assertEquals(updatedDate, res.getOrderDate());
        assertEquals("Acme", res.getCustomerName());
        verify(orderRepository).save(order);
    }

    // UPDATE - THROW EXCEPTION
    @Test
    void update_whenOrderNotFound_shouldThrowException() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(2);
        request.setEmployeeId(3);

        when(orderRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.update(99, request));

        verify(customerRepository, never()).findById(any());
        verify(employeeRepository, never()).findById(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    // UPDATE - THROW EXCEPTION
    @Test
    void update_whenCustomerNotFound_shouldThrowException() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(99);
        request.setEmployeeId(3);

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));
        when(customerRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.update(1, request));

        verify(employeeRepository, never()).findById(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    // UPDATE - THROW EXCEPTION
    @Test
    void update_whenEmployeeNotFound_shouldThrowException() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(2);
        request.setEmployeeId(99);

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));
        when(customerRepository.findById(2))
                .thenReturn(Optional.of(customer));
        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.update(1, request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    // DELETE - DELETE ORDER
    @Test
    void delete_whenOrderExists_shouldDeleteOrder() {
        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));

        orderService.delete(1);

        verify(orderRepository).delete(order);
    }

    // DELETE - THROW EXCEPTION
    @Test
    void delete_whenOrderNotFound_shouldThrowException() {
        when(orderRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.delete(99));

        verify(orderRepository, never()).delete(any(Order.class));
    }

    // GETBYID - RETURN ORDER
    @Test
    void getById_whenOrderExists_shouldReturnOrder() {
        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));

        OrderResponse res = orderService.getById(1);

        assertEquals(1, res.getOrderId());
        assertEquals("Acme", res.getCustomerName());
        assertEquals("John Doe", res.getEmployeeName());
    }

    // GETBYID - THROW EXCEPTION
    @Test
    void getById_whenOrderNotFound_shouldThrowException() {
        when(orderRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.getById(99));
    }

    // GETALL - RETURN LIST
    @Test
    void getAll_shouldReturnList() {
        when(orderRepository.findAll())
                .thenReturn(List.of(order, order));

        List<OrderResponse> results = orderService.getAll();

        assertEquals(2, results.size());
        assertEquals("Acme", results.get(0).getCustomerName());
    }

    // GETBYEMPLOYEE - RETURN ORDERS
    @Test
    void getByEmployee_shouldReturnOrders() {
        when(orderRepository.findOrdersByEmployeeWithDetails(3))
                .thenReturn(List.of(order));

        List<OrderResponse> results = orderService.getByEmployee(3);

        assertEquals(1, results.size());
        assertEquals(3, results.get(0).getEmployeeId());
    }

    // GETBETWEEN - RETURN ORDERS
    @Test
    void getBetween_shouldReturnOrders() {
        LocalDateTime from = LocalDateTime.of(2026, 8, 14, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 8, 14, 23, 59);

        when(orderRepository.findOrdersWithDetailsBetween(from, to))
                .thenReturn(List.of(order));

        List<OrderResponse> results = orderService.getBetween(from, to);

        assertEquals(1, results.size());
        assertEquals(orderDate, results.get(0).getOrderDate());
    }
}
