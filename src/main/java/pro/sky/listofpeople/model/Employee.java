package pro.sky.listofpeople.model;
import java.util.Objects;

public class Employee {
    private  final String firstname;
    private final String lastname;
    private final int departmentId;
    private final float salary;

    public Employee(String firstname, String lastname, int departmentId, float salary) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public float getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstname.equals(employee.firstname) && lastname.equals(employee.lastname);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "lastname='" + firstname + '\'' +
                ", firstname='" + lastname + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}