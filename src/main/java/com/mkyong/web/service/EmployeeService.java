package com.mkyong.web.service;
 
import java.util.List;

import com.mkyong.web.model.Employee;
 
public interface EmployeeService {
 public List<Employee> getEmployees();
 public Employee getEmployee(Long employeeId);
 public int deleteEmployee(Long employeeId); 
 public int updateEmployee(Employee employee);
 public int createEmployee(Employee employee); 
}
