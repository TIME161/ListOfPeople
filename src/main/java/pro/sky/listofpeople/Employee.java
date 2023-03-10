package pro.sky.listofpeople;
import java.util.Objects;

public class Employee {
    private  final String firstname;
    private final String lastname;

    public Employee( String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
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