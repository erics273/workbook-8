package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //did I pass e command line arguments in at runtime
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.hca.jdbc.UsingDriverManager <username> <password>");
            System.exit(1);
        }

        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        Scanner myScanner = new Scanner(System.in);

        //Connect to the DB
        Connection connection = null;

        while(true) {


            try {
                // create the connection and prepared statement
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("What do you want to do?");
            System.out.println("\t1) Display all products");
            System.out.println("\t2) Display all customers");
            System.out.println("\t0) Exit");
            System.out.print("Select an option: ");

            switch (myScanner.nextInt()) {
                case 1:
                    displayAllProducts(connection);
                    break;
                case 2:
                    displayAllCustomers(connection);
                    break;
                case 0:
                    System.out.println("Bye Bye!");

                    //clost the connection to the DB
                    if (connection != null) {
                        try {
                            connection.close();
                            System.out.println("DB Connection closed");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    System.exit(0);


                default:
                    System.out.println("InvalidChoice");
            }

        }
    }

    public static void displayAllCustomers(Connection connection){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            //initialze the preparedStatement we created above
            preparedStatement = connection.prepareStatement("" +
                    "SELECT " +
                    "   ContactName, " +
                    "   CompanyName," +
                    "   City," +
                    "   Country," +
                    "   Phone  " +
                    "FROM " +
                    "   Customers " +
                    "ORDER BY " +
                    "   Country");

            // execute the query
            resultSet = preparedStatement.executeQuery();

            // loop thru the results
           printResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void displayAllProducts(Connection connection){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            //initialze the preparedStatement we created above
            preparedStatement = connection.prepareStatement("" +
                    "SELECT " +
                    "   ProductName, " +
                    "   UnitPrice," +
                    "   UnitsInStock " +
                    "FROM " +
                    "   Products " +
                    "ORDER BY " +
                    "   ProductName");

            System.out.println(preparedStatement.toString());

            // execute the query
            resultSet = preparedStatement.executeQuery();

            // loop thru the results
           printResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                    System.out.println("ResultSet closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    System.out.println("Prepared Statement Closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String value = rs.getString(i); // generic, works for most types
                System.out.print(columnName + ": " + value + "  ");
            }
            System.out.println(); // new line after each row
        }
    }


}
