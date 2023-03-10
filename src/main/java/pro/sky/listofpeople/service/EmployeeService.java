package pro.sky.listofpeople.service;
import org.springframework.stereotype.Service;
import pro.sky.listofpeople.Employee;
import pro.sky.listofpeople.exceptions.EmployeeAlreadyAddedException;
import pro.sky.listofpeople.exceptions.EmployeeNotFoundException;
import pro.sky.listofpeople.exceptions.EmployeeStorageIsFullException;

import java.util.*;

@Service
public class EmployeeService {
    public String key;

    private final int maxEmployeesCount = 11;
    Map<String,Employee> employeesWithKey = new HashMap<>(Map.of(
            "АрнольдШашков",
            new Employee("Арнольд", "Шашков"),
            "ТарасВласов",
            new Employee("Тарас", "Власов"),
            "ЕгорМатвеев",
            new Employee("Егор", "Матвеев"),
            "РодионГордеев",
            new Employee("Родион", "Гордеев"),
            "ЮстинНиколаев",
            new Employee("Юстин", "Николаев"),
            "ЕваМоисеева",
            new Employee("Ева", "Моисеева"),
            "УльянаКузнецова",
            new Employee("Ульяна", "Кузнецова"),
            "ХаритинаЛогинова",
            new Employee("Харитина", "Логинова"),
            "ЛяляЛукина",
            new Employee("Ляля", "Лукина"),
            "АстраБоброва",
            new Employee("Астра", "Боброва")
    ));

    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        key = firstName + lastName;

        if (employeesWithKey.containsKey(key)) {
            return employee;
        } else throw new EmployeeNotFoundException("Такого пользователя нет в базе");
    }

    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        key = firstName + lastName;

        if (employeesWithKey.containsKey(key)) {
            employeesWithKey.remove(key);
            return employee;
        } else throw new EmployeeNotFoundException("Такого пользователя нет в базе");
    }

     public Employee add(String firstName, String lastName) {
         Employee employee = new Employee(firstName, lastName);
         key = firstName + lastName;

         if (employeesWithKey.containsKey(key)) {
             throw new EmployeeAlreadyAddedException("Такой пользователь уже имеется");
         }
         if (employeesWithKey.size() == maxEmployeesCount) {
             throw new EmployeeStorageIsFullException("База данных переполнена");
         } else {
             employeesWithKey.put(key,employee);
             return employee;
         }
     }

    public Map<String,Employee> all() {
        return employeesWithKey;
    }
}