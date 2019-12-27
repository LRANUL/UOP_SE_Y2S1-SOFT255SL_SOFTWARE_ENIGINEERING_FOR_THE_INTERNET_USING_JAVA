/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas.L.H.H
 */
public class NewAccountNumberGeneration {
    
    public int retrievingPreviousAccountNumber(String accountNumberColoumnNameSQL, String accountNumberTableSQL){
        
        // Variable declaration to store previous account number that is retrieved from the database
        int previousAccountNumberDB = 0;
        
        // Variable declaration to store the new account number 
        int newAccountNumberDB = 0;
        
        // Creating a new object to retrieve database connection url
        DBConnection db = new DBConnection();
        
        // Retrieving the previous generated account number from the database
        try (Connection retrievingPreviousAccountNumberCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement retrievingPreviousAccountNumberStmt = retrievingPreviousAccountNumberCon.createStatement();) {

            // Assigning SQL query
            String retrievingPreviousAccountNumberSqlQuery = "SELECT TOP 1 "+ accountNumberColoumnNameSQL + " FROM "+ accountNumberTableSQL +" ORDER BY "+ accountNumberColoumnNameSQL +" DESC";

            // Executing SQL query
            ResultSet retrievingPreviousAccountNumberRs = retrievingPreviousAccountNumberStmt.executeQuery(retrievingPreviousAccountNumberSqlQuery);

            if (retrievingPreviousAccountNumberRs.next()) {
                previousAccountNumberDB = retrievingPreviousAccountNumberRs.getInt(1);
            }
        }
        // Error handling. Checks for SQL related issues
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Error Occurred in SQL Connection",
                "New Customer Account Number Generation - ERROR!",
                JOptionPane.ERROR_MESSAGE);              
        }
        
        newAccountNumberDB = previousAccountNumberDB + 1;
        
        return newAccountNumberDB;
        
    }
    
}
