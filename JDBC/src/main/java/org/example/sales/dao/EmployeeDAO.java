package org.example.sales.dao;

import org.example.sales.entities.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {
    private Connection conn;

    public EmployeeDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Employee> selectAll() throws SQLException{
        if(conn == null){
            return null;
        }

        String select = "select * from employees";
        ArrayList<Employee> employees = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(select);
            ResultSet resultSet = ps.executeQuery();
        ){
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("Employee_id"));
                employee.setLastName(resultSet.getString("Last_name"));
                employee.setFirstName(resultSet.getString("First_Name"));
                employee.setBirthdate(resultSet.getString("Birth_date"));
                employee.setSupervisor(resultSet.getInt("supervisor_id"));

                employees.add(employee);
            }

        }catch (SQLException e){
            throw new SQLException("Can not display Employee "+e.getMessage());
        }
        return employees;
    }

    public boolean insert(Employee employee) throws SQLException{
        if(conn == null){
            return false;
        }

        String insert = "{call sp_add_employee(?,?,?,?)}";

        int index = 1;
        try(CallableStatement cs = conn.prepareCall(insert);){
            cs.setString(index++,employee.getLastName());
            cs.setString(index++,employee.getFirstName());
            cs.setString(index++,employee.getBirthdate());
            cs.setInt(index++,employee.getSupervisor());

            if(cs.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        return false;
    }

    public boolean update(int id, Employee employee) throws SQLException{
        if(conn == null){
            return false;
        }

        String update = "{call sp_update_employee(?,?,?,?,?)}";

        int index = 1;
        try(CallableStatement cs = conn.prepareCall(update);){
            cs.setString(index++,employee.getLastName());
            cs.setString(index++,employee.getFirstName());
            cs.setString(index++,employee.getBirthdate());
            cs.setInt(index++,employee.getSupervisor());
            cs.setInt(index++,id);

            if(cs.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) throws SQLException{
        if(conn == null){
            return false;
        }

        String delete = "{call sp_delete_employee(?)}";

        int index = 1;
        try(CallableStatement cs = conn.prepareCall(delete);){
            cs.setInt(index++,id);

            if(cs.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        return false;
    }
}
