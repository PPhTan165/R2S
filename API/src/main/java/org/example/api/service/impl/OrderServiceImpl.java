package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrderRequest;
import org.example.api.dto.OrderResponse;
import org.example.api.entity.Customer;
import org.example.api.entity.Employee;
import org.example.api.entity.Order;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.CustomerRepository;
import org.example.api.repository.EmployeeRepository;
import org.example.api.repository.OrderRepository;
import org.example.api.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    private OrderResponse mapToResponse(Order order) {
        String empName = (order.getEmployee() != null)
                ? order.getEmployee().getFirstName() + " " + order.getEmployee().getLastName()
                : null;
        String cusName = (order.getCustomer() != null)
                ? order.getCustomer().getCustomerName()
                : null;

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getCustomer() != null ? order.getCustomer().getCustomerId() : null,
                cusName,
                order.getEmployee() != null ? order.getEmployee().getEmployeeId() : null,
                empName
        );
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        Customer c = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id =" + request.getCustomerId()));
        Employee e = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = " + request.getEmployeeId()));

        Order o = new Order();
        o.setOrderDate(request.getOrderDate() != null ? request.getOrderDate() : LocalDateTime.now());
        o.setCustomer(c);
        o.setEmployee(e);

        return mapToResponse(orderRepository.save(o));
    }

    @Override
    public OrderResponse update(Integer id, OrderRequest request) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id = " + id));
        Customer c = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id =" + request.getCustomerId()));
        Employee e = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = " + request.getEmployeeId()));

        o.setCustomer(c);
        o.setEmployee(e);

        if (request.getOrderDate() != null) o.setOrderDate(request.getOrderDate());

        return mapToResponse(orderRepository.save(o));
    }

    @Override
    public void delete(Integer id) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with Id = " + id));
        orderRepository.delete(o);
    }

    @Override
    public OrderResponse getById(Integer id) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with  id =" + id));

        return mapToResponse(o);
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getByEmployee(Integer employeeId) {
        return orderRepository.findOrdersByEmployeeWithDetails(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getBetween(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findOrdersWithDetailsBetween(from, to)
                .stream().map(this::mapToResponse).toList();
    }


}
