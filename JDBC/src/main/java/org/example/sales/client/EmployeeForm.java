package org.example.sales.client;

import org.example.sales.entities.Employee;

import java.util.Scanner;

public class EmployeeForm {
    private final Scanner sc;

    public EmployeeForm(Scanner sc) {
        this.sc = sc;
    }

    public int getId(){
        int id;
        System.out.println("Enter id: ");
        id = Integer.parseInt(sc.nextLine());

        return id;
    }

    public Employee getEmployee(){
        Employee employee = new Employee();

        System.out.print("Enter last name: ");
        employee.setLastName(sc.nextLine());

        System.out.print("Enter first name: ");
        employee.setFirstName(sc.nextLine());

        System.out.print("Enter birth date (YYYY-mm-DD): ");
        employee.setBirthdate(sc.nextLine());

        System.out.print("Enter supervisorID: ");
        employee.setSupervisor(Integer.parseInt(sc.nextLine()));

        return employee;
    }
}
