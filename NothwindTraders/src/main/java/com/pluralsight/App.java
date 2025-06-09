package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {

        try {

            //load the MySQL driver not needed for java 4+
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. open a connection to the database use the database URL to point to the correct database
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");

            // create statement (should use preparedStatement)
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();

            // define your query
            String query = "SELECT ProductName FROM Products";

            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);

            // process the results
            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }

            // 3. Close the connection
            connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


