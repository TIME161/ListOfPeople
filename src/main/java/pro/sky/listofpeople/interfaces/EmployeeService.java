package pro.sky.listofpeople.interfaces;


import pro.sky.listofpeople.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> find(String firstName, String lastName);

    List<Employee> remove(String firstName, String lastName);

    Employee add(String firstName, String lastName, int departmentId, float salary);

    List<Employee> all();

}
