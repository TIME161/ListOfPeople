package pro.sky.listofpeople.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.listofpeople.exceptions.*;
import pro.sky.listofpeople.interfaces.EmployeeService;
import pro.sky.listofpeople.model.Employee;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final int maxEmployeesCount = 13;

     public List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("Арнольд", "Шашков", 1, 94512),
            new Employee("Тарас", "Власов", 2, 22432),
            new Employee("Егор", "Матвеев", 3, 27165),
            new Employee("Родион", "Гордеев", 4, 70342),
            new Employee("Юстин", "Николаев", 5, 61339),
            new Employee("Ева", "Моисеева", 1, 25723),
            new Employee("Ульяна", "Кузнецова", 3, 33706),
            new Employee("Харитина", "Логинова", 4, 41129),
            new Employee("Ляля", "Лукина", 2, 40886),
            new Employee("Астра", "Боброва", 5, 11446)
    ));

    public List<Employee> find(String firstName, String lastName) {
        checkCorrectFirstLastName(firstName, lastName);
        List<Employee> findEmployeer = employees.stream().filter(employee -> firstName.equals(employee.getFirstname())
                && lastName.equals(employee.getLastname())).collect(Collectors.toList());
        if (findEmployeer.isEmpty()) {
            throw new EmployeeNotFoundException("Такого пользователя нет в базе");
        } else return findEmployeer;
    }

    public List<Employee> remove(String firstName, String lastName) {
        checkCorrectFirstLastName(firstName, lastName);
        List<Employee> findEmployeerForRemove = employees.stream().filter(employee -> firstName.equals(employee.getFirstname())
                && lastName.equals(employee.getLastname())).collect(Collectors.toList());
        if (findEmployeerForRemove.isEmpty()) {
            throw new EmployeeNotFoundException("Такого пользователя нет в базе");
        } else {
            employees.removeAll(findEmployeerForRemove);
            return findEmployeerForRemove;
        }
    }

    public Employee add(String firstName, String lastName, int departmentId, float salary) {
        checkCorrectFirstLastName(firstName, lastName);
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