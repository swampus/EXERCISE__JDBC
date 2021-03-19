package com.swampus.task;

import com.swampus.misc.ConnectionManager;
import com.swampus.misc.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeveloperTest {

    private Developer developer = new Developer();
    private ConnectionManager connectionManager = new ConnectionManager();

    private Connection connection;

    private Connection getConnection() {
        return connectionManager.createConnectionToH2DataBase(
                "sa", "password",
                "jdbc:h2:mem", "testdb");
    }

    @Before
    public void setUp() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        st.execute("TRUNCATE TABLE EMPLOYEE;");
        connection.commit();
        connection.close();
        this.connection = getConnection();
    }

    @Test
    public void preparedStatement() throws Exception {
        assertNotNull(developer.preparedStatement());
    }

    @Test
    public void doBTaxPayerExists() throws Exception {
        this.connection = getConnection();
        connection.createStatement()
                .execute("INSERT INTO EMPLOYEE(name, tax_category, personal_code)" +
                        " VALUES ('A', 'B', '0')");
        connection.createStatement()
                .execute("INSERT INTO EMPLOYEE(name, tax_category, personal_code)" +
                        " VALUES ('D', 'B', '1')");
        connection.commit();
        connection.close();

        assertTrue(developer.doBTaxPayerExists());

        connection = getConnection();
        connection.createStatement()
                .execute("TRUNCATE TABLE EMPLOYEE;");
        connection.createStatement()
                .execute("INSERT INTO EMPLOYEE(name, tax_category, personal_code)" +
                        " VALUES ('D', 'C', 'zzz')");
        connection.close();

        assertFalse(developer.doBTaxPayerExists());
    }

    @Test
    public void updateTaxCategory() throws Exception {
        connection = getConnection();
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, tax_category) " +
                "VALUES('1','A') ");
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, tax_category) " +
                "VALUES('2','D') ");
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, tax_category) " +
                "VALUES('1','C') ");
        connection.commit();
        connection.close();
        developer.updateTaxCategory("1", "B");
        connection = getConnection();
        ResultSet res = connection.createStatement().executeQuery("SELECT * FROM EMPLOYEE ORDER BY personal_code");
        res.next();
        assertEquals("1", res.getString("personal_code"));
        assertEquals("B", res.getString("tax_category"));
        res.next();
        assertEquals("1", res.getString("personal_code"));
        assertEquals("B", res.getString("tax_category"));
        res.next();
        assertEquals("2", res.getString("personal_code"));
        assertEquals("D", res.getString("tax_category"));
        assertFalse(res.next());


    }

    @Test
    public void getAllEmployesWithNullEmail() throws Exception {
        connection = getConnection();
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, email) " +
                " VALUES('1','A') ");
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, email) " +
                " VALUES('2',null) ");
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, email) " +
                " VALUES('3',null) ");
        connection.close();

        List<Employee> employeeList = developer.getAllEmployesWithNullEmail();
        assertEquals(2, employeeList.size());
        assertEquals("2", employeeList.get(0).getPersonalCode());
        assertEquals("3", employeeList.get(1).getPersonalCode());

    }

    @Test
    public void deleteSelecte() throws Exception {
        Map<String, String> employees = new HashMap<>();
        employees.put("1", "N");
        employees.put("2", "AAA");
        employees.put("3", "DELETE");

        connection = getConnection();
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, email) " +
                "VALUES('1','A') ");
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, email) " +
                "VALUES('2',null) ");
        connection.createStatement().execute("INSERT INTO EMPLOYEE(personal_code, email) " +
                "VALUES('3',null) ");
        connection.close();

        developer.deleteSelecte(employees);
        connection = getConnection();
        ResultSet res = connection.createStatement().executeQuery("SELECT * FROM EMPLOYEE ORDER BY personal_code");
        res.next();
        assertEquals("1", res.getString("personal_code"));
        res.next();
        assertEquals("2", res.getString("personal_code"));
        assertFalse(res.next());


    }

    @Test
    public void insertEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("E");
        employee.setName("Name");
        employee.setTaxCategory("B");
        employee.setPersonalCode("1");

        Employee employee2 = new Employee();
        employee2.setEmail("Eeeeee");
        employee2.setName("Surname");
        employee2.setTaxCategory("B");
        employee2.setPersonalCode("2");

        List<Employee> employees = new LinkedList<>();
        employees.add(employee);
        employees.add(employee2);

        developer.insertEmployees(employees);
        connection = getConnection();

        ResultSet res = connection.createStatement().executeQuery("SELECT * FROM EMPLOYEE ORDER BY personal_code");
        res.next();
        assertEquals("1", res.getString("personal_code"));
        assertEquals("B", res.getString("tax_category"));
        assertEquals("E", res.getString("email"));
        assertEquals("Name", res.getString("name"));
        res.next();
        assertEquals("2", res.getString("personal_code"));
        assertEquals("B", res.getString("tax_category"));
        assertEquals("Eeeeee", res.getString("email"));
        assertEquals("Surname", res.getString("name"));
        assertFalse(res.next());

        connection.close();

    }
}