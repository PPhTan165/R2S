package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.CustomerRequest;
import org.example.api.dto.CustomerResponse;
import org.example.api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    //CREATE
    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CustomerRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    //READ BY ID
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    @GetMapping("/{id}")
    public CustomerResponse getById(
            @PathVariable Integer id
    ){
        return service.getById(id);
    }

    //READ ALL
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    @GetMapping
    public List<CustomerResponse> getAll(){
        return service.getAll();
    }

    //SEARCH
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    @GetMapping("/search")
    public List<CustomerResponse> search(@RequestParam String name){
        return service.searchByName(name);
    }

    //UPDATE
    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerRequest request
    ){
        return service.update(id,request);
    }

    //DELETE
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
