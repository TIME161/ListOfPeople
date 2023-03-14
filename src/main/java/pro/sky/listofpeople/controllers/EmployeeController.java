package pro.sky.listofpeople.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.sky.listofpeople.Employee;
import pro.sky.listofpeople.exceptions.EmployeeAlreadyAddedException;
import pro.sky.listofpeople.exceptions.EmployeeNotFoundException;
import pro.sky.listofpeople.exceptions.EmployeeStorageIsFullException;
import pro.sky.listofpeople.service.EmployeeService;

import java.util.List;

@RestController

@RequestMapping(value = "/employee")
public class EmployeeController {
    EmployeeService service = new EmployeeService();

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

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(EmployeeAlreadyAddedException.class)
    public String handleException (EmployeeAlreadyAddedException e) {
        return String.format("%s %s", HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }

    @RequestMapping("/find")
    public Employee find(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                         @RequestParam("departmentId") int departmentId, @RequestParam("salary") float salary) {
        return service.find(firstName, lastName, departmentId, salary);
    }

    @RequestMapping("/remove")
    public Employee remove(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                           @RequestParam("departmentId") int departmentId, @RequestParam("salary") float salary) {
        return service.remove(firstName, lastName, departmentId, salary);
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

