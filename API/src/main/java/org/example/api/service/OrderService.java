package org.example.api.service;

import org.example.api.dto.OrderRequest;
import org.example.api.dto.OrderResponse;
import org.example.api.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    OrderResponse update(Integer id,OrderRequest request);
    void delete(Integer id);
    OrderResponse getById(Integer id);
    List<OrderResponse> getAll();
    List<OrderResponse> getByEmployee(Integer employeeId);
    List<OrderResponse> getBetween(LocalDateTime from, LocalDateTime to);
}
