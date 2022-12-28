package com.sikl3gend.EmployeeCRUD.controller;

import com.sikl3gend.EmployeeCRUD.entity.Employee;
import com.sikl3gend.EmployeeCRUD.exception.ResourceNotFound;
import com.sikl3gend.EmployeeCRUD.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="http://localhost:4200")

public class EmployeeController {

    @Autowired
    public EmployeeRepository repository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return repository.findAll();
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable int id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Not record found with given Id: " + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee employee2 = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("No record found with this id: " + id));
        employee2.setName(employee.getName());
        employee2.setAddress(employee.getAddress());
        employee2.setSalary(employee.getSalary());
        Employee updateEmployee = repository.save(employee2);
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee (@PathVariable int id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("No record found with this id: " + id));
        repository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
