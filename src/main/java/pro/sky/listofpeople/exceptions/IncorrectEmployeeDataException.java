package pro.sky.listofpeople.exceptions;

public class IncorrectEmployeeDataException extends RuntimeException {
    public IncorrectEmployeeDataException(String message) {
        super(message);
    }
}