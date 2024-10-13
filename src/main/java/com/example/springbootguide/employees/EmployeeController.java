package com.example.springbootguide.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Этот метод писал сам, не по уроку. Может быть надо по-другому
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id){
        return employeeService.getById(id); // Если не найден, выбросит исключение
    }

    @PostMapping("/createEmployee")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping("/{id}")
    public void updateEmployee(
            @PathVariable Long id,
            @RequestParam (value = "email", required = false) String email,
            @RequestParam (value = "salary", required = false) Integer salary){
        employeeService.updateEmployee(id, email, salary);
    }
}
