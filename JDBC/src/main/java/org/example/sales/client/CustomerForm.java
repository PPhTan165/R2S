package org.example.sales.client;

import org.example.sales.entities.Customer;

import java.util.Scanner;

public class CustomerForm {
    private final Scanner sc;

    public CustomerForm(Scanner sc) {
        this.sc = sc;
    }

    public int getId(){
        int id;
        System.out.println("Enter id: ");
        id = Integer.parseInt(sc.nextLine());

        return id;
    }

    public Customer getCustomer(){
        Customer customer = new Customer();

        System.out.print("Enter customer name: ");
        customer.setName(sc.nextLine());

        System.out.print("Enter contact name: ");
        customer.setContact(sc.nextLine());

        System.out.print("Enter address: ");
        customer.setAddress(sc.nextLine());

        System.out.print("Enter city: ");
        customer.setCity(sc.nextLine());

        System.out.print("Enter postal code: ");
        customer.setPostCode(sc.nextLine());

        System.out.print("Enter country: ");
        customer.setCountry(sc.nextLine());

        return customer;
    }
}
