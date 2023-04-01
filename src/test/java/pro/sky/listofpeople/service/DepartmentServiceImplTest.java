package pro.sky.listofpeople.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pro.sky.listofpeople.exceptions.DepartmentSearchException;
import pro.sky.listofpeople.interfaces.DepartmentService;
import pro.sky.listofpeople.interfaces.EmployeeService;
import pro.sky.listofpeople.model.Employee;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = {DepartmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DepartmentServiceImplTest {
    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private EmployeeService employeeService;

    private static Stream<Arguments> minMaxNumbersGive() {
        return Stream.of(
                Arguments.of(1, 50, 30),
                Arguments.of(1, 100, 50),
                Arguments.of(1, 176, 22)
        );
    }

    private static Stream<Arguments> departmentGive() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3)
        );
    }


    @ParameterizedTest(name = "{index} => departmentId={0}, maxSalary={1}, minSalary={2}")
    @MethodSource("minMaxNumbersGive")
    void getEmployeeWithMinSalary_success(Integer departmentId, float maxSalary, float minSalary) {
        Employee employeeWithMaxSalary = new Employee("Сергей", "Фамильный", departmentId, maxSalary);
        Employee employeeWithMinSalary = new Employee("Артур", "Мобильный", departmentId, minSalary);
        List<Employee> employees = List.of(employeeWithMaxSalary, employeeWithMinSalary);
        when(employeeService.all()).thenReturn(employees);
        Employee actualEmployee = departmentService.getEmployeeWithMinSalary(departmentId);
        assertEquals(employeeWithMinSalary, actualEmployee);
        assertTrue(minSalary < maxSalary);
    }

    @ParameterizedTest(name = "{index} => departmentId={0}")
    @MethodSource("departmentGive")
    void getEmployeeWithMinSalary_withDepartmentSearchException(int departmentId) {
        when(employeeService.all()).thenReturn(Collections.emptyList());
        String expectedMessage = "Департамент не найден";
        Exception exception = assertThrows(
                DepartmentSearchException.class,
                () -> departmentService.getEmployeeWithMinSalary(departmentId)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => departmentId={0}, maxSalary={1}, minSalary={2}")
    @MethodSource("minMaxNumbersGive")
    void getEmployeeWithMaxSalary_success(Integer departmentId, float maxSalary, float minSalary) {
        Employee employeeWithMaxSalary = new Employee("Сергей", "Фамильный", departmentId, maxSalary);
        Employee employeeWithMinSalary = new Employee("Артур", "Мобильный", departmentId, minSalary);
        List<Employee> employees = List.of(employeeWithMaxSalary, employeeWithMinSalary);
        when(employeeService.all()).thenReturn(employees);
        Employee actualEmployee = departmentService.getEmployeeWithMaxSalary(departmentId);
        assertEquals(employeeWithMaxSalary, actualEmployee);
        assertTrue(minSalary < maxSalary);
    }

    @ParameterizedTest(name = "{index} => departmentId={0}")
    @MethodSource("departmentGive")
    void getEmployeeWithMaxSalary_withDepartmentSearchException(int departmentId) {
        when(employeeService.all()).thenReturn(Collections.emptyList());
        String expectedMessage = "Департамент не найден";
        Exception exception = assertThrows(
                DepartmentSearchException.class,
                () -> departmentService.getEmployeeWithMaxSalary(departmentId)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => departmentId={0}")
    @MethodSource("departmentGive")
    void getAll_success(int departmentId) {
        List<Employee> employees = new ArrayList<>();
        Employee depOneFirst = new Employee("Артур", "Артуров", 1, 15000);
        Employee depOneSecond = new Employee("Сергей", "Петров", 1, 2000);
        Employee depTwoFirst = new Employee("Петр", "Носов", 2, 150000);
        Employee depTwoSecond = new Employee("Алексей", "Работкин", 2, 9000);
        Employee depTwoThird = new Employee("Мария", "Петрова", 2, 17000);
        Employee depThreeFirst = new Employee("Аркадий", "Турин", 3, 11000);
        Employee depThreeSecond = new Employee("Марго", "Моми", 3, 6000);
        Employee depThreeThird = new Employee("Евгений", "Зинин", 3, 54000);

        employees.add(depOneFirst);
        employees.add(depOneSecond);
        employees.add(depTwoFirst);
        employees.add(depTwoSecond );
        employees.add(depTwoThird);
        employees.add(depThreeFirst);
        employees.add(depThreeSecond);
        employees.add(depThreeThird);

        //Подготовка ожидаемого результата
        when(employeeService.all()).thenReturn(employees);
        Map<String, List<Employee>> expectedResult = new HashMap<>();

        if (departmentId == 1){
            expectedResult.put(String.valueOf(departmentId), List.of(depOneFirst, depOneSecond));
        } else if (departmentId == 2){
            expectedResult.put(String.valueOf(departmentId), List.of(depTwoFirst, depTwoSecond,depTwoThird));
        } else if (departmentId == 3) {
            expectedResult.put(String.valueOf(departmentId), List.of(depThreeFirst, depThreeSecond,depThreeThird));
        }

        //Начало теста
        Map<String, List<Employee>> actualResult = departmentService.getAll(departmentId);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @NullSource
    void getAll_departmentIdIsNull(Integer departmentId) {
        List<Employee> employees = new ArrayList<>();
        Employee depOneFirst = new Employee("Артур", "Артуров", 1, 15000);
        Employee depOneSecond = new Employee("Сергей", "Петров", 1, 2000);
        Employee depTwoFirst = new Employee("Петр", "Носов", 2, 150000);
        Employee depTwoSecond = new Employee("Алексей", "Работкин", 2, 9000);
        Employee depTwoThird = new Employee("Мария", "Петрова", 2, 17000);
        Employee depThreeFirst = new Employee("Аркадий", "Турин", 3, 11000);
        Employee depThreeSecond = new Employee("Марго", "Моми", 3, 6000);
        Employee depThreeThird = new Employee("Евгений", "Зинин", 3, 54000);

        employees.add(depOneFirst);
        employees.add(depOneSecond);
        employees.add(depTwoFirst);
        employees.add(depTwoSecond);
        employees.add(depTwoThird);
        employees.add(depThreeFirst);
        employees.add(depThreeSecond);
        employees.add(depThreeThird);

        when(employeeService.all()).thenReturn(employees);
        Map<String, List<Employee>> expectedResult = new HashMap<>();
        expectedResult.put("1", List.of(depOneFirst, depOneSecond));
        expectedResult.put("2", List.of(depTwoFirst, depTwoSecond, depTwoThird));
        expectedResult.put("3", List.of(depThreeFirst, depThreeSecond, depThreeThird));

        Map<String, List<Employee>> actualResult = departmentService.getAll(departmentId);
        assertFalse(actualResult.isEmpty());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest(name = "{index} => departmentId={0}")
    @MethodSource("departmentGive")
    void getAll_emptyResult(Integer departmentId) {
        when(employeeService.all()).thenReturn(Collections.emptyList());

        Map<String, List<Employee>> actualResult = departmentService.getAll(departmentId);
        assertTrue(actualResult.isEmpty());
    }
}