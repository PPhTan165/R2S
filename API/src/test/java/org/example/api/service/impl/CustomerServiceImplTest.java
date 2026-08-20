package org.example.api.service.impl;

import org.example.api.dto.CustomerRequest;
import org.example.api.dto.CustomerResponse;
import org.example.api.entity.Customer;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setup(){
        customer = new Customer();

        customer.setCustomerName("John");
        customer.setContactName("Doe");
        customer.setAddress("Quan7");
        customer.setCity("HCM");
        customer.setPostalCode("75000");
        customer.setCountry("VN");
    }

    // GETALL - RETURN LIST
    @Test
    void getAll_shouldReturnList(){
        when(customerRepository.findAll())
                .thenReturn(List.of(customer,customer));

        List<CustomerResponse>  results = customerService.getAll();

        assertEquals(2,results.size());
    }

    // GETBYID - RETURN CUSTOMER
    @Test
    void getById_whenExists_shouldReturnCustomer(){
        when(customerRepository.findById(1))
                .thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getById(1);
        assertEquals("John", response.getCustomerName());
    }

    // GETBYID - THROW EXCEPTION
    @Test
    void getById_whenNotExists_shouldReturnException(){
        when(customerRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()-> customerService.getById(99));

    }

    // SEARCHBYNAME - RETURN CUSTOMER
    @Test
    void searchByName_shouldReturnCustomer(){
        when(customerRepository.findByCustomerNameContainingIgnoreCase("John"))
                .thenReturn(List.of(customer,customer));

        List<CustomerResponse> results = customerService.searchByName("John");
        assertEquals(2,results.size());
    }

    // CREATE - SAVE CUSTOMER
    @Test
    void create_shouldSaveCustomer(){
        CustomerRequest req = new CustomerRequest();
        req.setCustomerName("John");
        req.setContactName("Doe");
        req.setAddress("Quan7");
        req.setCity("HCM");
        req.setPostalCode("75000");
        req.setCountry("VN");

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse res = customerService.create(req);

        assertEquals("John",res.getCustomerName());

        verify(customerRepository).save(any(Customer.class));
    }

    // UPDATE - UPDATE CUSTOMER
    @Test
    void update_whenExists_shouldUpdate(){
        when(customerRepository.findById(1))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerRequest req = new CustomerRequest();
        req.setCustomerName("Updated");

        CustomerResponse res = customerService.update(1,req);
        assertEquals("Updated",res.getCustomerName());

        verify(customerRepository).save(any(Customer.class));
    }

    // UPDATE - THROW EXCEPTION
    @Test
    void update_whenNotExists_shouldThrowException(){
        when(customerRepository.findById(99))
                .thenReturn(Optional.empty());

        CustomerRequest req = new CustomerRequest();
        req.setCustomerName("Updated");

        assertThrows(ResourceNotFoundException.class,
                ()-> customerService.update(99,req));

        verify(customerRepository,never()).save(any(Customer.class));
    }

    // DELETE - DELETE CUSTOMER
    @Test
    void delete_whenExists_shouldDelete(){
        when(customerRepository.findById(1))
                .thenReturn(Optional.of(customer));

        customerService.delete(1);
        verify(customerRepository).delete(customer);
    }

    // DELETE - THROW EXCEPTION
    @Test
    void delete_whenNotExists_shouldThrowException(){
        when(customerRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()-> customerService.delete(99));

        verify(customerRepository,never()).delete(any(Customer.class));
    }
}
