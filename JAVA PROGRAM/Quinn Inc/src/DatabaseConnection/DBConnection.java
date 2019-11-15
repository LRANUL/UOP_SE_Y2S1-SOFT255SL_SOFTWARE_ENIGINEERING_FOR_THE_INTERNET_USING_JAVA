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
        return "jdbc:sqlserver://localhost:1433;databaseName=quinn_inc;user=quinn_inc_admin;password=soft255sl;";
    }
}
