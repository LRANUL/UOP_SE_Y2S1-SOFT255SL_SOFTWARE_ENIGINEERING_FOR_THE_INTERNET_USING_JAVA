/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ranul
 */
public interface Taxes {
    
    public Boolean taxcheck();
    public double CalcTax(double income,double balance);
    public void updateDbTaxes_Balance();
}
