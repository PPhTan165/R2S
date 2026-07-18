package org.example.api.service.impl;

import jakarta.transaction.Transactional;
import org.example.api.dto.EmployeeRequest;
import org.example.api.dto.EmployeeResponse;
import org.example.api.entity.Employee;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.EmployeeRepository;
import org.example.api.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public List<EmployeeResponse> getAll(){
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponse getById(Integer id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id =" + id));
        return toResponse(employee);
    }

    @Override
    public EmployeeResponse create(EmployeeRequest request){
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setBirthDate(request.getBirthDate());
        if(request.getSupervisorId() != null) {
            Employee supervisor = employeeRepository.findById(request.getSupervisorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found with id = " + request.getSupervisorId()));
            employee.setSupervisor(supervisor);
        }else{
            employee.setSupervisor(null);
        }
        Employee saved = employeeRepository.save(employee);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponse update(Integer id, EmployeeRequest request){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id =" +id));

        employee.setFirstName(request.getFirstName());
        employee.setFirstName(request.getFirstName());
        employee.setBirthDate(request.getBirthDate());
        if(request.getSupervisorId() != null){

            Employee supervisor = employeeRepository.findById(request.getSupervisorId())
                    .orElseThrow(()->new ResourceNotFoundException("Supervisor not found with id ="+request.getSupervisorId()));
            employee.setEmployeeId(supervisor.getEmployeeId());
        }else {
            employee.setSupervisor(null);
        }
        Employee updated = employeeRepository.save(employee);
        return toResponse(employee);
    }

    @Override
    public void delete(Integer id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id = "+ id));

        employeeRepository.delete(employee);
    }

    private EmployeeResponse toResponse(Employee e){
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(e.getEmployeeId());
        response.setFirstName(e.getFirstName());
        response.setLastName(e.getLastName());
        response.setBirthDate(e.getBirthDate());
        response.setSupervisorId(e.getSupervisor().getEmployeeId());
        return response;
    }
}
