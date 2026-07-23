package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.CustomerRequest;
import org.example.api.dto.CustomerResponse;
import org.example.api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CustomerRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(
            @PathVariable Integer id
    ){
        return service.getById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll(){
        return service.getAll();
    }

    @GetMapping("/search")
    public List<CustomerResponse> search(@RequestParam String name){
        return service.searchByName(name);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerRequest request
    ){
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
}
