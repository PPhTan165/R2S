package org.example.api.service;

import org.example.api.dto.EmployeeRequest;
import org.example.api.dto.EmployeeResponse;
import org.example.api.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {
    List<EmployeeResponse> getAll();
    EmployeeResponse getById(Integer id);
    EmployeeResponse create(EmployeeRequest request);
    EmployeeResponse update(Integer id, EmployeeRequest request);
    void delete(Integer id);
}
