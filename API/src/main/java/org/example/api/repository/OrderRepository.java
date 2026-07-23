package org.example.api.repository;

import org.example.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerCustomerId(Integer customerId);

    @Query("""
                    SELECT o FROM Order o
                    JOIN FETCH o.customer c
                    JOIN FETCH o.employee e
                    WHERE o.orderDate BETWEEN :from AND :to
            """)
    List<Order> findOrdersWithDetailsBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
                SELECT o FROM Order o
                JOIN FETCH o.customer c
                JOIN FETCH o.employee e
                WHERE e.employeeId = :empId
            """)
    List<Order> findOrdersByEmployeeWithDetails(@Param("empId") Integer empId);
}