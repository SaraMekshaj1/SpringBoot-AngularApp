package com.example.HackathonApp.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.HackathonApp.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
 
  @Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:keyword% OR e.lastName LIKE %:keyword% OR e.skills LIKE %:keyword% OR e.experience LIKE %:keyword%")
  List<Employee> searchEmployees(@Param("keyword") String keyword);

    
}
