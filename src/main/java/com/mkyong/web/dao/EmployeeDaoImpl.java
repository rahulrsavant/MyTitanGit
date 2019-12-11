package com.mkyong.web.dao;

// changed
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mkyong.web.model.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Employee> getEmployees() {
		List<Employee> employees = null;

		try {
			employees = jdbcTemplate.query("SELECT * FROM master.dbo.trn_employee",
					new BeanPropertyRowMapper<Employee>(Employee.class));
			/*
			 * Employee e1=new Employee(); Employee e2=new Employee();
			 * 
			 * e1.setAge(10L); e1.setEmployeeId(1L); e1.setFirstName("first");
			 * e1.setLastName("Name");
			 * 
			 * e2.setAge(20L); e2.setEmployeeId(2L); e2.setFirstName("Second");
			 * e2.setLastName("Name");
			 * 
			 * employees.set(0, e1); employees.set(1,e2);
			 */

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public Employee getEmployee(Long employeeId) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject("SELECT * FROM master.dbo.trn_employee WHERE employee_id = ?",
					new Object[] { employeeId }, new BeanPropertyRowMapper<Employee>(Employee.class));
					/*
					 * Employee e1=new Employee(); Employee e2=new Employee();
					 * 
					 * e1.setAge(10L); e1.setEmployeeId(1L);
					 * e1.setFirstName("first"); e1.setLastName("Name");
					 * 
					 * e2.setAge(20L); e2.setEmployeeId(2L);
					 * e2.setFirstName("Second"); e2.setLastName("Name");
					 * 
					 * if(employeeId==1) employee=e1; else employee=e2;
					 */

					
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return employee;

	}

	public int deleteEmployee(Long employeeId) {
		int count = jdbcTemplate.update("DELETE from master.dbo.trn_employee WHERE employee_id = ?", new Object[] { employeeId });
		// int count=0;
		return count;
	}

	public int updateEmployee(Employee employee) {
		int count = jdbcTemplate.update(
				"UPDATE master.dbo.trn_employee set first_name = ? , last_name = ? , age = ? where employee_id = ?", new Object[] {
						employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getEmployeeId() });
		return count;
		// return 0;
	}

	public int createEmployee(Employee employee) {
	//	jdbcTemplate.execute("drop table master.dbo.trn_employee");
		
		//jdbcTemplate.execute("create table trn_employee(employee_id int,first_name VARCHAR, last_name varchar, age int)");
	//	jdbcTemplate.execute("CREATE TABLE master.dbo.trn_employee (employee_id int,first_name varchar(100),last_name varchar(100),age int)");
		
		int count = jdbcTemplate.update(
				"INSERT INTO master.dbo.trn_employee(employee_id,first_name, last_name, age)VALUES(?,?,?,?)", new Object[] {
						employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getAge() });
		return count;

		/*
		 * Employee e1=new Employee();
		 * 
		 * e1.setAge(10L); e1.setEmployeeId(1L); e1.setFirstName("first");
		 * e1.setLastName("Name"); return 0;
		 */
	}

}
