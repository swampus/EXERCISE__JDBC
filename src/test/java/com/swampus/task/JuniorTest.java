package com.swampus.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JuniorTest {

    private Junior junior = new Junior();

    private Connection getConnection() {
        return junior.createConnectionToDataBase(
                "sa", "password",
                "jdbc:h2:mem", "testdb",
                "org.h2.Driver");
    }

    @Test
    public void createConnectionToDataBase() throws Exception {
        Connection connection = getConnection();
        assertNotNull(connection);
        connection.commit();
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM TESTTABLE;");
        while (res.next()) {
            String coffeeName = res.getString("nnnn");
            assertEquals("12345", coffeeName);
        }
    }

    @Before
    public void setUp() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        st.execute("TRUNCATE TABLE EMPLOYEE;");
        connection.commit();
        connection.close();
    }

    @Test
    public void addRecordIntoTableEmployee() throws Exception {
        junior.addRecordIntoTableEmployee();

        Connection connection = getConnection();
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM EMPLOYEE");
        res.next();
        assertEquals("Dmitry", res.getString("name"));
        assertEquals("test@com.com", res.getString("email"));
        assertEquals("12345689", res.getString("personal_code"));
        assertFalse(res.next());
        connection.close();
    }

    @Test
    public void deleteRecordFromEmployee() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('1');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('2');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('3');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('1111-22222');");
        connection.commit();
        junior.deleteRecordFromEmployee();

        ResultSet res = st.executeQuery("SELECT * FROM EMPLOYEE ORDER BY personal_code");

        res.next();
        assertEquals("1", res.getString("personal_code"));
        res.next();
        assertEquals("2", res.getString("personal_code"));
        res.next();
        assertEquals("3", res.getString("personal_code"));
        assertFalse(res.next());
        connection.close();
    }

    @Test
    public void getRecordsCount() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('1');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('2');");
        connection.commit();

        assertEquals(2, junior.getRecordsCount());
        connection.close();

    }

    @Test
    public void updateEmployees() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('1');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('2');");
        connection.commit();
        junior.updateEmployees("B");

        ResultSet res = st.executeQuery("SELECT tax_category FROM EMPLOYEE ORDER BY personal_code");
        res.next();
        assertEquals("B", res.getString("tax_category"));
        res.next();
        assertEquals("B", res.getString("tax_category"));
        assertFalse(res.next());
        connection.close();
    }


    @Test
    public void getAllPersonalCodeFromEmployee() throws Exception {
        Connection connection = getConnection();
        Statement st = connection.createStatement();
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('1');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('2');");
        st.execute("insert into EMPLOYEE(personal_code) VALUES ('2');");
        connection.commit();
        List<String> set = junior.getAllPersonalCodeFromEmployee();
        assertEquals(2, set.size());
        assertTrue(set.contains("1"));
        assertTrue(set.contains("2"));
        connection.close();
    }

}

