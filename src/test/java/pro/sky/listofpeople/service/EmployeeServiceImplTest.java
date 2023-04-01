package pro.sky.listofpeople.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pro.sky.listofpeople.exceptions.EmployeeAlreadyAddedException;
import pro.sky.listofpeople.exceptions.EmployeeNotFoundException;
import pro.sky.listofpeople.exceptions.EmployeeStorageIsFullException;
import pro.sky.listofpeople.interfaces.EmployeeService;
import pro.sky.listofpeople.model.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {EmployeeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceImplTest {
    @Autowired
    private EmployeeService employeeService;

    private static Stream<Arguments> namesInList() {
        return Stream.of(
                Arguments.of("Арнольд", "Шашков", 1, 94512),
                Arguments.of("Тарас", "Власов", 2, 22432),
                Arguments.of("Егор", "Матвеев", 3, 27165)
        );
    }

    private static Stream<Arguments> namesNew() {
        return Stream.of(
                Arguments.of("Странный", "Кактус", 1, 94512),
                Arguments.of("Сверепый", "Подстаканник", 2, 22432),
                Arguments.of("Таинственный", "Тапок", 3, 27165)
        );
    }
    private static Stream<Arguments> namesNewForFullTesting() {
        return Stream.of(
                Arguments.of("Первая", "Пустая", 1, 94512),
                Arguments.of("Вторая", "Пустая", 2, 22432),
                Arguments.of("Третья", "Пустая", 1, 94512),
                Arguments.of("Синий", "Подстаканник", 2, 22432),
                Arguments.of("Темный", "Кактус", 1, 94512),
                Arguments.of("Светлый", "Подстаканник", 2, 22432),
                Arguments.of("Таинственный", "Тапок", 3, 27165)
        );
    }

    private static Stream<Arguments> namesNotInList() {
        return Stream.of(
                Arguments.of("Арнольд", "Арнольд"),
                Arguments.of("Тарас", "Тарас"),
                Arguments.of("Егор", "Егор")
        );
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}, department={2}, salary={3}")
    @MethodSource("namesInList")
    void find_success(String firstName, String lastName, int department, float salary) {
        Employee testEmployeer = new Employee(firstName, lastName, department, salary);
        List<Employee> expectedResult = new ArrayList<>();
        expectedResult.add(testEmployeer);
        List<Employee> actualResult = employeeService.find(firstName,lastName);
        assertEquals(expectedResult,actualResult);
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}")
    @MethodSource("namesNotInList")
    void find_withException(String firstName, String lastName){
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.find(firstName,lastName));
        String expectedMessege = "Такого пользователя нет в базе";
        assertEquals(expectedMessege,exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}, department={2}, salary={3}")
    @MethodSource("namesInList")
    void remove_returnMessege(String firstName, String lastName, int department, float salary) {
        Employee testEmployeer = new Employee(firstName, lastName, department, salary);
        List<Employee> expectedResult = new ArrayList<>();
        expectedResult.add(testEmployeer);
        List<Employee> actualResult = employeeService.remove(firstName,lastName);
        assertEquals(expectedResult,actualResult);
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}")
    @MethodSource("namesNotInList")
    void remove_withException(String firstName, String lastName){
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.remove(firstName,lastName));
        String expectedMessege = "Такого пользователя нет в базе";
        assertEquals(expectedMessege,exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}, department={2}, salary={3}")
    @MethodSource("namesNew")
    void add(String firstName, String lastName, int department, float salary) {
        Employee expectedResult = new Employee(firstName, lastName, department, salary);
        Employee actualResult = employeeService.add(firstName,lastName,department,salary);
        assertEquals(expectedResult,actualResult);
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}, department={2}, salary={3}")
    @MethodSource("namesNewForFullTesting")
    void add_withFullException(String firstName, String lastName, int department, float salary){
        Exception exception = assertThrows(EmployeeStorageIsFullException.class,
                () -> employeeService.add(firstName,lastName,department,salary));
        String expectedMessege = "База данных переполнена";
        assertEquals(expectedMessege,exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => firstName={0}, lastName={1}, department={2}, salary={3}")
    @MethodSource("namesInList")
    void add_withException(String firstName, String lastName, int department, float salary){
        Exception exception = assertThrows(EmployeeAlreadyAddedException.class,
                () -> employeeService.add(firstName,lastName,department,salary));
        String expectedMessege = "Такой пользователь уже имеется";
        assertEquals(expectedMessege,exception.getMessage());
    }

    @Test
    void all() {
        List<Employee> expectedResult = new ArrayList<>();
        expectedResult.addAll(employeeService.all());
        List<Employee> actualResult = employeeService.all();
        assertEquals(expectedResult,actualResult);
    }
}