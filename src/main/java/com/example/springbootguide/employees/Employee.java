package com.example.springbootguide.employees;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long id;

    private String name;

    private String email;

    private LocalDate birthDate;

    @Transient
    // говорит о том, что нет необходимости это поле сохранять в БД, тк возраст можно высчитывать из birthDate
    private Integer age;

    private Integer salary;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getAge() {
        if (age == null) {
            this.age = Period.between(
                            birthDate,
                            LocalDate.now())
                    .getYears();
        }
        return age;
    }

    public Integer getSalary() {
        return salary;
    }

    public Employee(Long id, String name, String email, LocalDate birthDate, Integer salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.age = Period.between(
                        birthDate,
                        LocalDate.now())
                .getYears();
        this.salary = salary;
    }

    public Employee() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
