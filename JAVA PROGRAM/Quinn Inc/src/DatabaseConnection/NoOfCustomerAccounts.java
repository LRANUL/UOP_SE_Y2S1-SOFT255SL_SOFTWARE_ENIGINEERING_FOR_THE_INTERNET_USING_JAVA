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

import DatabaseConnection.DBConnection;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas.L.H.H
 */
public class NoOfCustomerAccounts {
    
    public int calculateNoOfExistingCustomerAccounts(String userEnteredPassportNumber){ 
        // Calculating the total number of existing customer accounts for each customer
        // Because one customer can only have five accounts inaddition to all the account types
        
        
        // Creating a new object to retrieve the database connection URL
        DBConnection db = new DBConnection();
        
        // Retrieving the number of exixting account from each account type
        
        // Declaring variable to store the number of customer accounts
        int countNormalSavingsDB = 0;
        
            // Number of existing accounts in account type, Normal Savings
            // CNS - Count Normal Savings
        try (Connection retrievingCNSCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement retrievingCNSStmt = retrievingCNSCon.createStatement();) {

            // Assigning SQL query
            String retrievingCNSSqlQuery = "SELECT COUNT(NSAccountNumber) AS 'CountNormalSavingsAccount' FROM "
                    + "AccountNormalSavings WHERE cPassportNumber = "+ userEnteredPassportNumber;

            // Executing SQL query
            ResultSet retrievingCNSRs = retrievingCNSStmt.executeQuery(retrievingCNSSqlQuery);

            if (retrievingCNSRs.next()) {
                countNormalSavingsDB = retrievingCNSRs.getInt(1);
            }
        } 
        // Error handling. Checks for SQL related issues
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Error Occurred in SQL Connection",
                "Number of Customer Accounts Identification - ERROR!",
                JOptionPane.ERROR_MESSAGE);    
        }
        
        
        // Declaring variable to store the number of customer accounts
        int countBonusSavingsDB = 0;
        
            // Number of existing accounts in account type, Bonus Savings
            // CBS - Count Bonus Savings
        try (Connection retrievingCBSCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement retrievingCBSStmt = retrievingCBSCon.createStatement();) {

            // Assigning SQL query
            String retrievingCBSSqlQuery = "SELECT COUNT(BSAccountNumber) AS 'CountBonusSavingsAccount' FROM "
                    + "AccountBonusSavings WHERE cPassportNumber = "+ userEnteredPassportNumber;

            // Executing SQL query
            ResultSet retrievingCBSRs = retrievingCBSStmt.executeQuery(retrievingCBSSqlQuery);

            if (retrievingCBSRs.next()) {
                countBonusSavingsDB = retrievingCBSRs.getInt(1);
            }
        } // Error handling. Checks for SQL related issues
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Error Occurred in SQL Connection",
                "Number of Customer Accounts Identification - ERROR!",
                JOptionPane.ERROR_MESSAGE);
        }
        
        
        // Declaring variable to store the number of customer accounts
        int countPremierSavingsDB = 0;
        
            // Number of existing accounts in account type, Premier Savings
            // CPS - Count Premier Savings
        try (Connection retrievingCPSCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement retrievingCPSStmt = retrievingCPSCon.createStatement();) {

            // Assigning SQL query
            String retrievingCPSSqlQuery = "SELECT COUNT(PSAccountNumber) AS 'CountPremierSavingsAccount' FROM "
                    + "AccountPremierSavings WHERE cPassportNumber = "+ userEnteredPassportNumber;

            // Executing SQL query
            ResultSet retrievingCPSRs = retrievingCPSStmt.executeQuery(retrievingCPSSqlQuery);

            if (retrievingCPSRs.next()) {
                countPremierSavingsDB = retrievingCPSRs.getInt(1);
            }
        } // Error handling. Checks for SQL related issues
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Error Occurred in SQL Connection",
                "Number of Customer Accounts Identification - ERROR!",
                JOptionPane.ERROR_MESSAGE);
        }
        
        
        // Calculating the total number of customer accounts
        int totalCountOfAccounts = (countNormalSavingsDB + countBonusSavingsDB + countPremierSavingsDB);
        
        // Returning the total number of customer accounts to the place where this method was called.
        return totalCountOfAccounts;
    }
}
