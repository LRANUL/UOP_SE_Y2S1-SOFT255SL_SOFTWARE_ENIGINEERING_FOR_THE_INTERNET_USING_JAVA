/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

/**
 *
 * @author Lucas.L.H.H
 */

public class DBConnection {
    public String DatabaseConnectionUrl(){
        // Sending database connection URL to wherever called
        return "jdbc:sqlserver://localhost:1433;databaseName=quinnincDB;user=quinnincDB_Admin;password=soft255sl;";
    }
}
