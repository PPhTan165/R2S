package org.example.sales.client;

import org.example.sales.dao.CustomerDAO;
import org.example.sales.entities.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SalesManagement {
    private static Scanner sc;
    private CustomerDAO customerDAO;
    private CustomerForm customerForm;

    static final String GET_ALL_CUSTOMERS = "1";
    static final String ADD_NEW_CUSTOMER = "2";
    static final String UPDATE_CUSTOMER = "3";
    static final String REMOVE_CUSTOMER = "4";
    static final String QUIT = "0";


    private void initialize() throws SQLException {
        sc = new Scanner(System.in);
        Connection conn = getConnection();

        //Customer
        customerForm = new CustomerForm(sc);
        customerDAO = new CustomerDAO(conn);
    }

    public SalesManagement() throws SQLException {
        initialize();
    }

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/sales";

            return DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Can not open connection " + e.getMessage());
        }
    }

    private static void createMenu() {
        System.out.println("\n1. Get all customers");
        System.out.println("2. Add new an customer");
        System.out.println("3. Change customer information");
        System.out.println("4. Remove an customer");
        System.out.println("0. Quit");
        System.out.print("Your choice: ");
    }

    private void displayAllCustomers() throws SQLException {
        ArrayList<Customer> customers = customerDAO.selectAll();

        if (customers == null || customers.isEmpty()) {
            System.out.println("Not found");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void addCustomer() throws SQLException {
        Customer customer = customerForm.getCustomer();

        if (customerDAO.insert(customer)) {
            System.out.println("Successful");
        } else {
            System.out.println("Unsuccessful");
        }
    }

    private void updateCustomer() throws SQLException {
        int id = customerForm.getId();
        Customer customer = customerForm.getCustomer();

        if (customerDAO.update(id, customer)) {
            System.out.println("Successful");
        } else {
            System.out.println("Unsuccessful");
        }
    }

    private void removeCustomer() throws SQLException {
        int id = customerForm.getId();

        if (customerDAO.delete(id)) {
            System.out.println("Successful");
        } else {
            System.out.println("Unsuccessful");
        }
    }

    public static void main(String[] args) {
        String choice = "";
        SalesManagement management = null;

        do {
            try {
                management = new SalesManagement();
                createMenu();
                choice = sc.nextLine();

                switch (choice) {
                    case GET_ALL_CUSTOMERS:
                        management.displayAllCustomers();
                        break;
                    case ADD_NEW_CUSTOMER:
                        management.addCustomer();
                        break;
                    case UPDATE_CUSTOMER:
                        management.updateCustomer();
                        break;
                    case REMOVE_CUSTOMER:
                        management.removeCustomer();
                        break;
                    default:
                        if (!choice.equals(QUIT)) {
                            System.out.println("Wrong choice");
                        }

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (!choice.equals(QUIT));
    }
}
