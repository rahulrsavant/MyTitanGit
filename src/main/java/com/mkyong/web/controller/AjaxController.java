package com.mkyong.web.controller;
// Ajax comment line
//from sts
//one more
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.Employee;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;
import com.mkyong.web.service.EmployeeService;

@RestController
public class AjaxController {

	List<User> users;
	
	 @Autowired
	 private EmployeeService employeeService;

	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, limited the json data display to client.
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/search/api/getSearchResult")
	public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

		AjaxResponseBody result = new AjaxResponseBody();

		if (isValidSearchCriteria(search)) {
			List<User> users = findByUserNameOrEmail(search.getUsername(), search.getEmail());

			if (users.size() > 0) {
				result.setCode("200");
				result.setMsg("");
				result.setResult(users);
			} else {
				result.setCode("204");
				result.setMsg("No user!");
			}

		} else {
			result.setCode("400");
			result.setMsg("Search criteria is empty!");
		}

		//AjaxResponseBody will be converted into json format and send back to client.
		return result;

	}

	private boolean isValidSearchCriteria(SearchCriteria search) {

		boolean valid = true;

		if (search == null) {
			valid = false;
		}

		if ((StringUtils.isEmpty(search.getUsername())) && (StringUtils.isEmpty(search.getEmail()))) {
			valid = false;
		}

		return valid;
	}

	// Init some users for testing
	@PostConstruct
	private void iniDataForTesting() {
		users = new ArrayList<User>();

		User user1 = new User("mkyong", "pass123", "mkyong@yahoo.com", "012-1234567", "address 123");
		User user2 = new User("yflow", "pass456", "yflow@yahoo.com", "016-7654321", "address 456");
		User user3 = new User("laplap", "pass789", "mkyong@yahoo.com", "012-111111", "address 789");
		users.add(user1);
		users.add(user2);
		users.add(user3);

	}

	// Simulate the search function
	private List<User> findByUserNameOrEmail(String username, String email) {

		List<User> result = new ArrayList<User>();

		for (User user : users) {

			if ((!StringUtils.isEmpty(username)) && (!StringUtils.isEmpty(email))) {

				if (username.equals(user.getUsername()) && email.equals(user.getEmail())) {
					result.add(user);
					continue;
				} else {
					continue;
				}

			}
			if (!StringUtils.isEmpty(username)) {
				if (username.equals(user.getUsername())) {
					result.add(user);
					continue;
				}
			}

			if (!StringUtils.isEmpty(email)) {
				if (email.equals(user.getEmail())) {
					result.add(user);
					continue;
				}
			}

		}

		return result;

	}
	 
	 
	 
	 
	 
	 //------------------------------ GET ALL Employees  ------------------------------
	 @RequestMapping(value = "/employee", method = RequestMethod.GET, produces = "application/json")
	 public ResponseEntity<List<Employee>> employees() {
	 
	  HttpHeaders headers = new HttpHeaders();
	  List<Employee> employees = employeeService.getEmployees();
	 
	  if (employees == null) {
	   return new ResponseEntity<List<Employee>>(HttpStatus.NOT_FOUND);
	  }
	  headers.add("Number Of Records Found", String.valueOf(employees.size()));
	  return new ResponseEntity<List<Employee>>(employees, headers, HttpStatus.OK);
	 }
	 
	 
	 
	 //-------------------------------- Get Emp ------------------------------------------------------
	 
	 @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	 public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long employeeId) {
	  Employee employee = employeeService.getEmployee(employeeId);
	  if (employee == null) {
	   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	  }
	  return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	 }
	 
	 
	 //-------------------------------- Get Emp ------------------------------------------------------
	 
	 @RequestMapping(value = "/employeeSix", method = RequestMethod.GET)
	 public ResponseEntity<Employee> getEmployeeagain() {
	  Employee employee = employeeService.getEmployee(6L);
	  if (employee == null) {
	   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	  }
	  return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	 }
	 
	 	 
	 //--------------------------------- DELETE Emp ---------------------------------
	 @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.DELETE)
	 public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long employeeId) {
	  HttpHeaders headers = new HttpHeaders();
	  Employee employee = employeeService.getEmployee(employeeId);
	  if (employee == null) {   
	   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	  }
	  employeeService.deleteEmployee(employeeId);
	  headers.add("Employee Deleted - ", String.valueOf(employeeId));
	  return new ResponseEntity<Employee>(employee, headers, HttpStatus.NO_CONTENT);
	 }
	 
	 
	 
	 
	 //---------------------------------- CREATE Emp ----------------------------------------
	 @JsonView(Views.Public.class)
	 @RequestMapping(value = "/employee", method = RequestMethod.POST,produces = "application/json")
	 public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
	  HttpHeaders headers = new HttpHeaders();
	  if (employee == null) {
	   return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
	  }
	  employeeService.createEmployee(employee);
	  headers.add("Employee Created  - ", String.valueOf(employee.getEmployeeId()));
	  return new ResponseEntity<Employee>(employee, headers, HttpStatus.CREATED);
	 }
	 
	 
	 
	 
	 
	 //---------------------------------- UPDATE Emp ------------------------------------------
	 @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
	 public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee) {
	  HttpHeaders headers = new HttpHeaders();
	  Employee isExist = employeeService.getEmployee(employeeId);
	  if (isExist == null) {   
	   return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	  } else if (employee == null) {
	   return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
	  }
	  employeeService.updateEmployee(employee);
	  headers.add("Employee Updated  - ", String.valueOf(employeeId));
	  return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
	 }
	 
}
