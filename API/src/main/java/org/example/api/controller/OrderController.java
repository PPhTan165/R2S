package org.example.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.OrderRequest;
import org.example.api.dto.OrderResponse;
import org.example.api.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<OrderResponse> getAll(){
        return service.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-employee/{employeeId}")
    public List<OrderResponse> byEmployee(@PathVariable Integer employeeId){
        return service.getByEmployee(employeeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/between")
    public List<OrderResponse> between(
            @RequestParam @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)LocalDateTime from,
            @RequestParam @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)LocalDateTime to
            ){
        return service.getBetween(from,to);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable Integer id, @RequestBody OrderRequest request){
        return service.update(id,request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
