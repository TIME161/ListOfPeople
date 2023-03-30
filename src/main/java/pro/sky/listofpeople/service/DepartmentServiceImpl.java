package pro.sky.listofpeople.service;

import org.springframework.stereotype.Service;
import pro.sky.listofpeople.interfaces.DepartmentService;
import pro.sky.listofpeople.interfaces.EmployeeService;
import pro.sky.listofpeople.model.Employee;
import pro.sky.listofpeople.exceptions.DepartmentSearchException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee getEmployeeWithMinSalary(int departmentId) {
        return employeeService.all().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentSearchException("Департамент не найден"));
    }

    @Override
    public Employee getEmployeeWithMaxSalary(int departmentId) {
        return employeeService.all().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentSearchException("Департамент не найден"));
    }

    public Double getSumSalaryInDepartment(int departmentId) {
        double sumSolary = 0;
        List<Employee> employeesInDepartment = employeeService.all().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId).toList();
        for (Employee employees : employeesInDepartment) {
            sumSolary += employees.getSalary();
        }
        if (sumSolary > 0) {return sumSolary;
        } else throw new DepartmentSearchException("Департамент не найден");
    }

    @Override
    public Map<String, List<Employee>> getAll(Integer departmentId) {
        return employeeService.all().stream()
                .filter(employee -> departmentId == null || employee.getDepartmentId() == departmentId)
                .collect(Collectors.groupingBy(
                        employee -> String.valueOf(employee.getDepartmentId()),
                        Collectors.mapping(e -> e, Collectors.toList()))
                );
    }
}