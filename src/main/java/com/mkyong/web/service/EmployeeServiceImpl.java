package com.mkyong.web.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkyong.web.dao.EmployeeDao;
import com.mkyong.web.model.Employee;
 
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
 @Autowired
 private EmployeeDao employeeDao;
 
 public List<Employee> getEmployees() {
  List<Employee> employees = employeeDao.getEmployees();
  return employees;
 }
 
 public Employee getEmployee(Long employeeId) {
  Employee employee = employeeDao.getEmployee(employeeId);
  return employee;
 }
 
 public int deleteEmployee(Long employeeId) {
  return employeeDao.deleteEmployee(employeeId);
 }
 
 public int updateEmployee(Employee employee) {
  return employeeDao.updateEmployee(employee);
 }
 
 public int createEmployee(Employee employee) {
  return employeeDao.createEmployee(employee);
 }
}
