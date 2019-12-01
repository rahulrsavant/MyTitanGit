package com.mkyong.web.dao;
 
import java.util.List;

import com.mkyong.web.model.Employee;

// Jay yogeshwar 
 
public interface EmployeeDao {
 public List<Employee> getEmployees();
 public Employee getEmployee(Long employeeId);
 public int deleteEmployee(Long employeeId); 
 public int updateEmployee(Employee employee);
 public int createEmployee(Employee employee);
}
