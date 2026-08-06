package org.example.api.service.impl;

import org.example.api.dto.EmployeeRequest;
import org.example.api.dto.EmployeeResponse;
import org.example.api.entity.Employee;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setup(){
        employee = new Employee();
        Employee supervisor = new Employee();
        supervisor.setEmployeeId(10);

        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setBirthDate(LocalDate.of(1995,1,1));
        employee.setSupervisor(supervisor);
    }

    //GET ALL
    @Test
    void getAll_shouldReturnList(){
        //arrange: chuẩn bị
        when(employeeRepository.findAll())
                .thenReturn(List.of(employee,employee));

        //act: (nếu tồn tại) gọi method
        List<EmployeeResponse> result = employeeService.getAll();

        //assert: Kiểm tra kết quả (expected, actual)
        assertEquals(2,result.size());
    }

    //GET BY ID - success
    @Test
    void getById_whenExists_shouldReturnEmployee(){
        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));

        EmployeeResponse res = employeeService.getById(1);

        assertEquals("John",res.getFirstName());
    }

    //GET BY ID - not found
    @Test
    void getById_whenNotExists_shouldThrowException(){
        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()-> employeeService.getById(99));
    }

    //CREATE
    @Test
    void create_shouldSavedEmployee(){
        EmployeeRequest rq = new EmployeeRequest();
        rq.setFirstName("John");
        rq.setLastName("Doe");
        rq.setBirthDate(LocalDate.of(1995,1,1));
        rq.setSupervisorId(10);

        Employee supervisor = new Employee();
        supervisor.setEmployeeId(10);

        when(employeeRepository.findById(10))
                .thenReturn(Optional.of(supervisor));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponse res = employeeService.create(rq);

        //Kiểm tra kết quả expected và actual
        assertEquals("John",res.getFirstName());

        //Kiểm tra repo có gọi method save không
        //any(Employee.class): Chấp nhận mọi object thuộc class Employee
        verify(employeeRepository).save(any(Employee.class));
    }

    //UPDATE
    @Test
    void update_whenExists_shouldUpdate(){
        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        EmployeeRequest req = new EmployeeRequest();
        req.setFirstName("Updated");

        EmployeeResponse res = employeeService.update(1,req);
        assertEquals("Updated",res.getFirstName());

        verify(employeeRepository).save(employee);
    }

    //UPDATE - Not found
    @Test
    void update_whenNotExists_shouldThrowException(){
        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());

        EmployeeRequest req = new EmployeeRequest();
        req.setFirstName("Updated");

        assertThrows(ResourceNotFoundException.class,
                ()-> employeeService.update(99,req));

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //DELETE
    @Test
    void delete_whenExists_shouldDelete(){
        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));
        employeeService.delete(1);
        verify(employeeRepository).delete(employee);
    }

    //DELETE - NOT EXISTS
    @Test
    void delete_whenNotExists_shouldDelete(){
        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                ()-> employeeService.delete(99));
        verify(employeeRepository,never()).delete(any(Employee.class));
    }
}
