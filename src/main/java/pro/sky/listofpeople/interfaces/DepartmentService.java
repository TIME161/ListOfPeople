package pro.sky.listofpeople.interfaces;

import pro.sky.listofpeople.model.Employee;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Employee getEmployeeWithMinSalary(int departmentId);
    Employee getEmployeeWithMaxSalary(int departmentId);
    Map<String, List<Employee>> getAll(Integer departmentId);
    Double getSumSalaryInDepartment(int departmentId);
}