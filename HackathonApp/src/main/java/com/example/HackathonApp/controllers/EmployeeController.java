package com.example.HackathonApp.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.HackathonApp.model.Employee;
import com.example.HackathonApp.repository.EmployeeRepository;
import com.example.HackathonApp.service.EmployeeService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

import com.example.HackathonApp.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	 @Autowired
    private EmployeeService employeeService;

    @Autowired
	private EmployeeRepository employeeRepository;
	
	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}		
	/* 
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
		
	}*/

    @GetMapping("/employees/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Employee> employees = employeeService.searchEmployees(keyword);
        return ResponseEntity.ok(employees);
    }

@PostMapping("/employees")
public ResponseEntity<?> createEmployee(@Valid @RequestPart("employee") Employee employee,
                                        @RequestPart("file") MultipartFile file,
                                        BindingResult result) {
    if (result.hasErrors()) {
        // Process validation errors
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    try {
        if (file != null && !file.isEmpty()) {
            employee.setProfilePhoto(file.getBytes());
        }
    } catch (IOException e) {
        return ResponseEntity.status(500).body("Error processing file");
    }

    // Save employee if no errors
    Employee savedEmployee = employeeService.save(employee);
    return ResponseEntity.ok(savedEmployee);
}

   

	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}



    @PutMapping(value = "/employees/{id}" )
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
                                            @Valid @RequestPart("employee") Employee employeeDetails,
                                            @RequestPart(value = "file", required = false) MultipartFile file,
                                            BindingResult result) {
        if (result.hasErrors()) {
            // Process validation errors
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
    
        // Fetch employee by ID
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
    
        // Update fields
        if (employeeDetails.getFirstName() != null) employee.setFirstName(employeeDetails.getFirstName());
        if (employeeDetails.getLastName() != null) employee.setLastName(employeeDetails.getLastName());
        if (employeeDetails.getEmailId() != null) employee.setEmailId(employeeDetails.getEmailId());
        if (employeeDetails.getExperience() != null) employee.setExperience(employeeDetails.getExperience());
        if (employeeDetails.getSkills() != null) employee.setSkills(employeeDetails.getSkills());
    
        // Handle file upload
        try {
            if (file != null && !file.isEmpty()) {
                employee.setProfilePhoto(file.getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file");
        }
    
        // Save updated employee
        try {
            Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating employee");
        }
    }
    
	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
                employeeRepository.delete(employee);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return ResponseEntity.ok(response);
            }

	
}
        