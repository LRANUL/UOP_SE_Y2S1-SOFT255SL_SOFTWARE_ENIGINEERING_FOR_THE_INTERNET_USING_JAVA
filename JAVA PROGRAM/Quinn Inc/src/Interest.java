/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */
/**
 * This is interface providing functionality for Interest calculation
 * by allowing multiple inheritance with the Account class
 * Interest Calculation done by H.V.L.H.
 * @author ranul
 */
public interface Interest {

    
    public double interestMonthly(double amount, String accountType);
    
    public double interestDecember(double amount, String accountType);
    
}
