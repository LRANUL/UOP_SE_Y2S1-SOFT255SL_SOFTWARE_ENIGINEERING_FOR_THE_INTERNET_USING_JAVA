/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */
package DatabaseConnection;

/**
 *
 * @author Lucas.L.H.H
 */

public class DBConnection {
    
    // Non Static method (An object must be created)
    public String DatabaseConnectionUrl(){
        // Sending database connection URL to wherever called
        return "jdbc:sqlserver://localhost:1433;databaseName=quinnincDB;user=quinnincDB_Admin;password=soft255sl;";
    }
    
    // Static (An object creation is not required)
    public static String DatabaseConnectionUrlStc(){
        // Sending database connection URL to wherever called
        return "jdbc:sqlserver://localhost:1433;databaseName=quinnincDB;user=quinnincDB_Admin;password=soft255sl;";
    }
}
