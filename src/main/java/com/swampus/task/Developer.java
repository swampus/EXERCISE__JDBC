package com.swampus.task;

import com.swampus.misc.Employee;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * Use only prepared statements
 * Connection creation logic you can use from Junior class
 * (prob extract create connection to separate class)
 * if you need to know structure of Employee Table pls explore V1_0_0__init_migration.sql
 */
public class Developer {


    /**
     * Task0
     * Create and return prepared statement;
     * why we need prepared statements ?
     */
    public PreparedStatement preparedStatement() {
        return null;
    }

    /**
     * Task1
     * Check do any employee with tax_category = "B" exists ?
     * return true if yes, false = no
     */
    public Boolean doBTaxPayerExists() {
        return null;
    }

    /**
     * Task2
     * you have provided personal_code and taxCategory
     * update all Employes with provided personal_code set them provided tax_category
     */
    public void updateTaxCategory(String personalCode, String taxCategory) {

    }

    /**
     * Task3
     * Get all Employee with null email and convert returned data to List<Employee> objects
     * and return. List should be ordered by personal_code Alphabetic DESC
     * (In cycle generate employee objects using data from database)
     * Employee class is already done => com.swampus.misc.Employee
     */
    public List<Employee> getAllEmployesWithNullEmail() {
        return null;
    }

    /**
     * Task4
     * You have provided Map<String, String> key:value, where
     * Key is Action to do and Value is employee personal_code
     * If action is "DELETE" you need to delete employee with that personal_code (map.value) from DB
     * if action is another value ignore. (do nothing)
     */
    public void deleteSelecte(Map<String, String> employees) {

    }

    /**
     * Task5
     * insert provided List<Employee> into database
     * and return number of records, that was inserted like Integer
     * ( save all objects )
     */
    public Integer insertEmployees(List<Employee> employees) {
        return null;
    }

}
