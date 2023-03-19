package pro.sky.listofpeople.service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.listofpeople.exceptions.IncorrectEmployeeDataException;
import pro.sky.listofpeople.model.Employee;
import pro.sky.listofpeople.exceptions.EmployeeAlreadyAddedException;
import pro.sky.listofpeople.exceptions.EmployeeNotFoundException;
import pro.sky.listofpeople.exceptions.EmployeeStorageIsFullException;

import java.util.*;

@Service
public class EmployeeService {
    public String key;

    private final int maxEmployeesCount = 11;

    List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("Арнольд","Шашков", 1, 94512),
            new Employee("Тарас","Власов", 2, 22432),
            new Employee("Егор","Матвеев", 3, 27165),
            new Employee("Родион","Гордеев", 4, 70342),
            new Employee("Юстин","Николаев", 5, 61339),
            new Employee("Ева","Моисеева", 1, 25723),
            new Employee("Ульяна","Кузнецова", 3, 33706),
            new Employee("Харитина","Логинова", 4, 41129),
            new Employee("Ляля","Лукина", 2, 40886),
            new Employee("Астра","Боброва", 5, 11446)
    ));

    public Employee find(String firstName, String lastName, int departmentId, float salary) {
        checkCorrectFirstLastName(firstName,lastName);
        Employee employee = new Employee(firstName, lastName, departmentId, salary);
        if (employees.contains(employee)) {

            return employee;
        } else throw new EmployeeNotFoundException("Такого пользователя нет в базе");
    }

    public Employee remove(String firstName, String lastName, int departmentId, float salary) {
        checkCorrectFirstLastName(firstName,lastName);
        Employee employee = new Employee(firstName, lastName, departmentId, salary);
        if (employees.contains(employee)) {
            employees.remove(employee);

            return employee;
        } else throw new EmployeeNotFoundException("Такого пользователя нет в базе");
    }

    public Employee add(String firstName, String lastName, int departmentId, float salary) {
        checkCorrectFirstLastName(firstName,lastName);
        Employee employee = new Employee(firstName, lastName, departmentId, salary);
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

    private void checkCorrectFirstLastName(String first, String last) {
        checkCorrectDataEmployee(first);
        checkCorrectDataEmployee(last);
    }
    private void checkCorrectDataEmployee(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IncorrectEmployeeDataException("Имя или фамилия не заполнены");
        }
        if (!name.equals(StringUtils.capitalize(StringUtils.lowerCase(name)))) {
            throw new IncorrectEmployeeDataException("Имя или фамилия должны начинаться с заглавной буквы");
        }
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectEmployeeDataException("Имя или фамилия содержат не только буквы");
        }
    }
}