package com.swampus.misc;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {
    public Connection createConnectionToH2DataBase(String userName,
                                                 String password,
                                                 String databaseUrl,
                                                 String databaseName
    ) {
        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.
                    getConnection(databaseUrl + ":" + databaseName
                            , userName, password);
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
