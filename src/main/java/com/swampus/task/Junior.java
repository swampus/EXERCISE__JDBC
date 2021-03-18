package com.swampus.task;

import java.sql.Connection;
import java.util.List;

/**
 * Task0 must have for all Methods to be done before
 */
public class Junior {


    /**
     * Task0
     * Create connection to database
     * You have all necessary provided as parameters
     */
    public Connection createConnectionToDataBase(String userName,
                                                 String password,
                                                 String databaseUrl,
                                                 String databaseName,
                                                 String driverName) {
        return null;
    }

    /**
     *  for rest --use method from Task0 for create connection
     *  and think how you can know values to provide to that method
     *  (debug Task0)
     */


    /**
     * Task1
     * insert record into table EMPLOYEE
     * name="Dmitry"
     * personal_code="12345689"
     * email="test@com.com"
     */
    public void addRecordIntoTableEmployee() {

    }

    /**
     * Task2
     * delete record from EMPLOYEE where personal_code = "1111-22222";
     */
    public void deleteRecordFromEmployee() {

    }

    /**
     * Task3
     * return how many records in table Employee
     */
    public int getRecordsCount() {
        return 0;
    }

    /**
     * Task4
     * Update employee. Set tax category for all employees
     * taxCategory you can get as param
     * /!\ it looks work and test pass. But think what problem can be in that case ?
     * WHAT IS SQL INJECTION ? What we should use in that case ?
     */
    public void updateEmployees(String taxCategory) {

    }

    /**
     * Task5
     * return all unique personal codes from employee
     * fieldname: personal_code
     */
    public List<String> getAllPersonalCodeFromEmployee() {
        return null;
    }

}
