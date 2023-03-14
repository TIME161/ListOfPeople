package pro.sky.listofpeople.service;

import pro.sky.listofpeople.Employee;
import pro.sky.listofpeople.exceptions.DepartmentSearchException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentService {

    private final EmployeeService employeeService = new EmployeeService() ;


    public Employee getEmployeeWithMinSalary(int departmentId) {
        return employeeService.all().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentSearchException("Департамент не найден"));
    }


    public Employee getEmployeeWithMaxSalary(int departmentId) {
        return employeeService.all().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentSearchException("Департамент не найден"));
    }

    public Map<String, List<Employee>> getAll(Integer departmentId) {
        return employeeService.all().stream()
                .filter(employee -> departmentId == null || employee.getDepartmentId() == departmentId)
                .collect(Collectors.groupingBy(
                        employee -> String.valueOf(employee.getDepartmentId()),
                        Collectors.mapping(e -> e, Collectors.toList()))
                );
    }
}
