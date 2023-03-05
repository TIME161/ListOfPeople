package pro.sky.listofpeople.service;
import org.springframework.stereotype.Service;
import pro.sky.listofpeople.Employee;
import pro.sky.listofpeople.exceptions.EmployeeAlreadyAddedException;
import pro.sky.listofpeople.exceptions.EmployeeNotFoundException;
import pro.sky.listofpeople.exceptions.EmployeeStorageIsFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class EmployeeService {
    private final int maxEmployeesCount = 11;
    List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("Арнольд","Шашков"),
            new Employee("Тарас","Власов"),
            new Employee("Егор","Матвеев" ),
            new Employee("Родион","Гордеев"),
            new Employee("Юстин","Николаев"),
            new Employee("Ева","Моисеева"),
            new Employee("Ульяна","Кузнецова"),
            new Employee("Харитина","Логинова"),
            new Employee("Ляля","Лукина"),
            new Employee("Астра","Боброва")
    ));

    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            return employee;
        } else throw new EmployeeNotFoundException("Такого пользователя нет в базе");
    }

    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            employees.remove(employee);
            return employee;
        } else throw new EmployeeNotFoundException("Такого пользователя нет в базе");
    }

    public Employee add(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("Такой пользователь уже имеется");
        }
            if (employees.size() == maxEmployeesCount) {
            throw new EmployeeStorageIsFullException("База данных переполнена");
        }
            else {
            employees.add(employee);
            return employee;
        }
    }

    public List<Employee> all() {
            return employees;
    }
}