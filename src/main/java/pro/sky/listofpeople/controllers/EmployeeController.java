package pro.sky.listofpeople.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.sky.listofpeople.exceptions.IncorrectEmployeeDataException;
import pro.sky.listofpeople.interfaces.EmployeeService;
import pro.sky.listofpeople.model.Employee;
import pro.sky.listofpeople.exceptions.EmployeeAlreadyAddedException;
import pro.sky.listofpeople.exceptions.EmployeeNotFoundException;
import pro.sky.listofpeople.exceptions.EmployeeStorageIsFullException;

import java.util.List;

@RestController

@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public String handleException (EmployeeNotFoundException e) {
        return String.format("%s %s", HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(EmployeeStorageIsFullException.class)
    public String handleException (EmployeeStorageIsFullException e) {
        return String.format("%s %s", HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectEmployeeDataException.class)
    public String handleException (IncorrectEmployeeDataException e) {
        return String.format("%s %s", HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmployeeAlreadyAddedException.class)
    public String handleException (EmployeeAlreadyAddedException e) {
        return String.format("%s %s", HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }

    @RequestMapping("/find")
    public List<Employee> find(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return service.find(firstName,lastName);
    }

    @RequestMapping("/remove")
    public List<Employee> remove(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return service.remove(firstName, lastName);
    }

    @RequestMapping("/add")
    public Employee add(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                        @RequestParam("departmentId") int departmentId, @RequestParam("salary") float salary) {
        return service.add(firstName, lastName, departmentId, salary);
    }

    @RequestMapping("/all")
    public List<Employee> all() {
        return service.all();
    }
}

