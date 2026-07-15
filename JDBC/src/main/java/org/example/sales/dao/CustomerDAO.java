package org.example.sales.dao;

import org.example.sales.entities.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {
    private final Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Customer> selectAll() throws SQLException{
        if(conn == null){
            return null;
        }

        String select = "select * from customers";
        ArrayList<Customer> customers = new ArrayList<>();

        try(Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(select);
        ){
            while (result.next()){
                Customer customer = new Customer();

                customer.setId(result.getInt("Customer_ID"));
                customer.setName(result.getString("Customer_Name"));
                customer.setContact(result.getString("Contact_Name"));
                customer.setAddress(result.getString("Address"));
                customer.setCity(result.getString("City"));
                customer.setPostCode(result.getString("Postal_Code"));
                customer.setCountry(result.getString("Country"));

                customers.add(customer);
            }
        }catch (SQLException e){
            throw new SQLException("Can not display customers: " + e.getMessage());
        }
        return customers;
    }

    public boolean insert(Customer customer) throws SQLException{
        if(conn == null){
            return false;
        }

        String insert = "insert into customers(customer_name,contact_name,address,city,postal_code,country)"
                + "values (?,?,?,?,?,?)";

        int index =1 ;
        try(PreparedStatement ps = conn.prepareStatement(insert)){
            ps.setString(index++,customer.getName());
            ps.setString(index++,customer.getContact());
            ps.setString(index++,customer.getAddress());
            ps.setString(index++,customer.getCity());
            ps.setString(index++,customer.getPostalCode());
            ps.setString(index++,customer.getCountry());

            if(ps.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            throw new SQLException("Can not insert customer " + e.getMessage());
        }
        return false;
    }

    public boolean update(int id, Customer customer) throws SQLException{
        if(conn == null){
            return false;
        }

        String update = "update customers set customer_name = ?, contact_name = ?, address = ?" +
                ",city = ?, postal_code = ?, country = ? where Customer_ID = ?";

        int index =1 ;
        try(PreparedStatement ps = conn.prepareStatement(update)){
            ps.setString(index++,customer.getName());
            ps.setString(index++,customer.getContact());
            ps.setString(index++,customer.getAddress());
            ps.setString(index++,customer.getCity());
            ps.setString(index++,customer.getPostalCode());
            ps.setString(index++,customer.getCountry());
            ps.setInt(index++,id);

            if(ps.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            throw new SQLException("Can not insert customer " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) throws SQLException {
        if(conn == null){
            return false;
        }

        String delete = "delete from customers where customer_id = ? ";

        int index = 1;
        try(PreparedStatement ps = conn.prepareStatement(delete)){
            ps.setInt(index,id);

            if(ps.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            throw new SQLException("Can not delete customer " + e.getMessage());
        }
        return false;
    }
}
