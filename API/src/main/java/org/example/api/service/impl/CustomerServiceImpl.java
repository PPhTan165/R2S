package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.CustomerRequest;
import org.example.api.dto.CustomerResponse;
import org.example.api.entity.Customer;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.CustomerRepository;
import org.example.api.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    private Customer mapToEntity(CustomerRequest request){
        Customer c = new Customer();
        c.setCustomerName(request.getCustomerName());
        c.setContactName(request.getContactName());
        c.setAddress(request.getAddress());
        c.setCity(request.getCity());
        c.setPostalCode(request.getPostalCode());
        c.setCountry(request.getCountry());
        return c;
    }

    private CustomerResponse mapToResponse(Customer c){
        return new CustomerResponse(
                c.getCustomerName(),
                c.getContactName(),
                c.getAddress(),
                c.getCity(),
                c.getPostalCode(),
                c.getCountry()
        );
    }

    @Override
    public CustomerResponse create(CustomerRequest request){
        Customer customer = mapToEntity(request);
        return mapToResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse update(Integer id, CustomerRequest request){
        Customer customer = repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found"));

        customer.setCustomerName(request.getCustomerName());
        customer.setContactName(request.getContactName());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setPostalCode(request.getPostalCode());
        customer.setCountry(request.getCountry());

        return mapToResponse(repository.save(customer));
    }

    @Override
    public void delete(Integer id){
        Customer c = repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found with id = "+id));
        repository.delete(c);
    }

    @Override
    public CustomerResponse getById(Integer id){
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public List<CustomerResponse> getAll(){
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public  List<CustomerResponse> searchByName(String keyword){
        return repository.findByCustomerNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


}
