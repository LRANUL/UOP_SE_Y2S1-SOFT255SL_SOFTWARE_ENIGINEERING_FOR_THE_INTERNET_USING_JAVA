/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */

/**
 * This is interface providing functionality for taxes calculation
 * by allowing multiple inheritance with the Account class
 * Interest Calculation done by M.I.C.B.
 * @author ranul
 */
public interface Taxes {
    
    public Boolean taxcheck();
    public double CalcTax(double income,double balance);
    public void updateDbTaxes_Balance();
}
