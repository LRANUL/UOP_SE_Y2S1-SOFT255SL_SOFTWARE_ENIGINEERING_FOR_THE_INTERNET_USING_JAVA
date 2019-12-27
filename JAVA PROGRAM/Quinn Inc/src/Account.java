/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ranul
 */
public abstract class  Account implements Interest {
private String accountNo;
private double balance;
private double bonus;
private String status;


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }
    
    
    public abstract void debit();
    public abstract void credit();
    

    public void depositeInterest(){};
    
    @Override
    public double interestDec(double amount) {
        
        return (amount*0.2/12);
        
    }
    
    @Override
    public double interestMonth(double amount) {
        return (amount*0.1/12);
    }

}