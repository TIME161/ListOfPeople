package pro.sky.listofpeople.exceptions;

public class EmployeeStorageIsFullException extends RuntimeException {
    public EmployeeStorageIsFullException(String message) {
        super(message);
    }
}
