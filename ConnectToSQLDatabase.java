import java.sql.*;
import java.util.Scanner;

public class ConnectToSQLDatabase {

    public static void main(String[] args) throws SQLException {

        // Connecting to the database
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");


        // Creating a statement object
        Statement statement = con.createStatement();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Succesfully connected to Databse http://localhost/phpmyadmin/");


        // Taking inputs for database name, table name, number of table attributes, and attribute names and datatypes from the user
        System.out.println("Enter the Database name:");
        String databaseName = scanner.nextLine();
        System.out.println("Enter the Table name:");
        String tableName = scanner.nextLine();
        System.out.println("Enter the number of Table Attributes:");
        int numberOfAttributes = scanner.nextInt();


        String[] attributeNames = new String[numberOfAttributes];
        String[] attributeDataTypes = new String[numberOfAttributes];
        int[] attributeLimits = new int[numberOfAttributes];


        for (int i = 0; i < numberOfAttributes; i++) {
            System.out.println("Enter the name of Attribute " + (i + 1) + ":");
            attributeNames[i] = scanner.next();
            System.out.println("Enter the data type of Attribute " + (i + 1) + ":");
            attributeDataTypes[i] = scanner.next();
            System.out.println("Enter the limit of Attribute " + (i + 1) + ":");
            attributeLimits[i] = scanner.nextInt();
        }

        String createDbQuery = "CREATE DATABASE "+databaseName+";";
        statement.execute(createDbQuery);
        String useDatabase = "USE "+databaseName;
        statement.execute(useDatabase);

        // Creating a table in the database
        String createTableQuery = "CREATE TABLE " + tableName + " (";

        for (int i = 0; i < numberOfAttributes; i++) {
            createTableQuery += attributeNames[i] + " " + attributeDataTypes[i] + "(" + attributeLimits[i] + ")";
            if (i < numberOfAttributes - 1) {
                createTableQuery += ",";
            }
        }
        createTableQuery += ")";
        statement.executeUpdate(createTableQuery);


        // Getting input for the table from the user
        String[] inputValues = new String[numberOfAttributes];

        for (int i = 0; i < numberOfAttributes; i++) {
            System.out.println("Enter the value for Attribute " + attributeNames[i] + ":");
            inputValues[i] = scanner.next();
        }

        // Inserting the input into the table
        String insertQuery = "INSERT INTO " + tableName + " (";

        for (int i = 0; i < numberOfAttributes; i++) {
            insertQuery += attributeNames[i];
            if (i < numberOfAttributes - 1) {
                insertQuery += ",";
            }
        }
        insertQuery += ") VALUES (";

        for (int i = 0; i < numberOfAttributes; i++) {
            insertQuery += "'" + inputValues[i] + "'";
            if (i < numberOfAttributes - 1) {
                insertQuery += ",";
            }
        }

        insertQuery += ")";
        statement.executeUpdate(insertQuery);

        System.out.println("Successfully inserted the data into the table!");
    }
}
