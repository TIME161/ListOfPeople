package pro.sky.listofpeople.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.sky.listofpeople.interfaces.DepartmentService;
import pro.sky.listofpeople.model.Employee;
import pro.sky.listofpeople.exceptions.DepartmentSearchException;


import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DepartmentSearchException.class)
    public String handleException(DepartmentSearchException e) {
        return String.format("%s %s", HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @GetMapping(path = "/{id}/salary/max")
    @ResponseBody
    public Employee maxSalary(@PathVariable(required = false) Integer id) {
        return departmentService.getEmployeeWithMaxSalary(id);
    }

    @GetMapping(path = "/{id}/salary/min")
    @ResponseBody
    public Employee minSalary(@PathVariable(required = false) Integer id) {
        return departmentService.getEmployeeWithMinSalary(id);
    }

    @GetMapping(path = "/{id}/salary/sum")
    @ResponseBody
    public Double sumSalary(@PathVariable(required = false) Integer id) {
        return departmentService.getSumSalaryInDepartment(id);
    }

    @GetMapping(path = "/employees")
    public Map<String, List<Employee>> allInDepartment() {
        return departmentService.getAll(null);
    }

    @GetMapping("/{id}/employees")
    @ResponseBody
    public Map<String, List<Employee>> allByDepartmentId(@PathVariable(required = false) Integer id) {
        return departmentService.getAll(id);
    }
}
