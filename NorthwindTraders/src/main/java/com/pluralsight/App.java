package com.pluralsight;

import java.sql.*;

public class App {


    public static void main(String[] args) {

        try {

            // 1. open a connection to the database
            // use the database URL to point to the correct database

            //this is like opening MySQL workbench and clicking localhost
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");


            // create statement
            // the statement is tied to the open connection

            //like me opening a new query window
            Statement statement = connection.createStatement();

            // define your query

            //like me typing the query in the new query windows
            String query = "SELECT  ProductName FROM Products;";

            // 2. Execute your query

            //this is like me clicking the lightning bolt
            ResultSet results = statement.executeQuery(query);

            // process the results
            //this is a way to view the result set but java doesnt have a spreadsheet view for us
            while (results.next()) {
                //String product = results.getString("ProductName");
                //or
                String product = results.getString(1);
                System.out.println(product);
            }

            // 3. Close the connection

            //closing mysql workbench
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
