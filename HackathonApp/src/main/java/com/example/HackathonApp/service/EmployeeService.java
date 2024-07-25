
package com.example.HackathonApp.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HackathonApp.repository.EmployeeRepository;
import com.example.HackathonApp.model.Employee;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }
   
  
}


