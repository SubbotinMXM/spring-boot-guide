package com.example.springbootguide.employees;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Этот метод писал сам, не по уроку. Может быть надо по-другому
    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
    }

    public Employee createEmployee(Employee employee) {
        // Валидация
        // Id должен быть пустой, тк устанавливается самой БД
        // Уникальный email
        // Зарплата более 5000

        if (employee.getId() != null) {
            throw new IllegalArgumentException("Id must be empty");
        }
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already taken");
        }
        if (employee.getSalary() <= 5000) {
            throw new IllegalArgumentException("Salary must be bigger than 5000");
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Employee by id=%s not found".formatted(id));
        }
        employeeRepository.deleteById(id);
    }

    @Transactional // аннотация, чтобы когда мы воспользуемся сеттером, изменения прокинулись в БД
    public void updateEmployee(Long id, String email, Integer salary) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee by id=%s not found".formatted(id)));

        if (email != null
            && !email.isEmpty()
            && !email.equals(employee.getEmail())) {
            Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);
            if (employeeOpt.isPresent()){
                throw new IllegalArgumentException("Email already taken");
            }
            employee.setEmail(email);
        }

        if (salary != null){
            if (salary <= 5000){
                throw new IllegalArgumentException("Salary must be bigger than 5000");
            }
            employee.setSalary(salary);
        }
    }
}

