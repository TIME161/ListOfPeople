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
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentSearchException("Департамент не найден"));
    }
    @Override
    public Double getSumSalaryInDepartment(int departmentId)  {
        List<Employee> employeesInDepartment = employeeService.all().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId).toList();
        if (employeesInDepartment.size() == 0) {
            throw new DepartmentSearchException("Департамент не найден");
        }
        return employeesInDepartment.stream().mapToDouble(Employee::getSalary).sum();
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