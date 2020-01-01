/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author Lucas.L.H.H
 */
public class NewAccountCreation {
    
    public Boolean insertingNewAccountDetails(String retrievingInsertingCustomerSqlQuery){
        
        // Creating a new object to retrieve database connection url
        DBConnection db = new DBConnection();
        
        // Declaring variable to check is SQL execution was successful or not 
        Boolean insertingAccountDetailsRs = false;
            
        // Inserting customer details to the database, Customer relation
        try (Connection insertingAccountDetailsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement insertingAccountDetailsStmt = insertingAccountDetailsCon.createStatement();) {
            
            // Executing SQL query
            insertingAccountDetailsRs = insertingAccountDetailsStmt.execute(retrievingInsertingCustomerSqlQuery);
            
        }
        // Error handling. Checks for SQL related issues
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Error Occurred in SQL Database Connection",
                "New Customer Registration - ERROR!",
                JOptionPane.ERROR_MESSAGE);              
        }
        
        return insertingAccountDetailsRs;
    
    }
    
    
    
}
