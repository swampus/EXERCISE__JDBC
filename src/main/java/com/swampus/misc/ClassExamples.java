package com.swampus.misc;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class ClassExamples {

    @PostConstruct
    public void init(){
        System.out.println("HELLO JDBC");
        Connection con;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.
                    getConnection("jdbc:h2:mem" + ":" + "testdb"
                            , "sa", "password");
            System.out.println(con);
            Statement statement = con.createStatement();
            statement.execute(
                    "INSERT INTO EMPLOYEE(name, email, " +
                            "personal_code, tax_category)" +
                            " VALUES('name','e@e.com'," +
                            "'pk10101','B')");

            statement.execute(
                    "INSERT INTO EMPLOYEE(name, email, " +
                            "personal_code, tax_category)" +
                            " VALUES('name123','e@1e.com'," +
                            "'pk123','B')");

            displayEmployees(statement);

            //DELETE EMPLOYEE WHERE personal_code = 'pk123';
            //Prepared Statement
            PreparedStatement preps =
                    con.prepareStatement(
                            "DELETE FROM EMPLOYEE WHERE personal_code = ?");
            preps.setString(1, "pk123");
            preps.execute();

            System.out.println("\n \n");
            displayEmployees(statement);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void displayEmployees(Statement statement) throws SQLException {
        String selectEmployeeSQL = "SELECT name, email, personal_code, " +
                "tax_category FROM EMPLOYEE";

        ResultSet resultSet
                = statement.executeQuery(selectEmployeeSQL);
        int rowNum = 1;
        while(resultSet.next()){
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String personalCode = resultSet.getString("personal_code");
            String taxCategory = resultSet.getString("tax_category");
            System.out.println(rowNum+") " + name + " " + email + " "
                    + personalCode + " " + taxCategory);
            rowNum++;
        }
    }

}
