
import DatabaseConnection.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ranul
 */
public abstract class Account implements Interest,Taxes {

    private String accountNo;
    private double balance;
    private double bonus;
    private String status;

    public void ChargesCustomer() {
    }

    ;
public void ChargesFamily() {
    }

    ;
public void LostCard() {
    }

    @Override
    public double CalcTax(double income,double balance) // calculate the tax based on income and balance left in a Savings accont
{   double taxAmount;
    
                     if(200000<balance)                   {taxAmount= 0.4*income;}
                else if(100000<balance && 200000>=balance){taxAmount= 0.3*income;}
                else if(50000<balance && 100000>=balance) {taxAmount= 0.2*income;}
                else if(10000<balance && 50000>=balance)  {taxAmount= 0.1*income;}
                else{taxAmount = 0;}
    
    return taxAmount;
}
    
    
    @Override 
    public Boolean taxcheck() //check if taxes are reduced this year
{   
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM");
        LocalDateTime datenow = LocalDateTime.now();
        LocalDateTime taxdate = LocalDateTime.of(0, 4, 1, 0, 0);
         


    
    
    if (dateFormat.format(datenow).equals(dateFormat.format(taxdate))) {
        try {
            conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
            //The select statement
            String sql5="SELECT ansNSAccountNumber FROM dbo.CustomerTransactionWithdrawal  WHERE ansNSAccountNumber IS NOT NULL AND tdTransactionDescriptionID <>'TD000001' AND TransactionDateTime BETWEEN '2019-04-01' AND '2020-04-01' ;";
            ps=conn.prepareStatement(sql5);
            
            //executes the query
            rs=ps.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            }
               
          }  
    System.out.println("Taxes not added");
    return false;
           
}
    
    
    public double getAccountBalance(String accountNo)//get account balance from database;
    {       Connection conn;
                ResultSet rs;
                PreparedStatement ps;
                double amount = -1;
        try {
            switch((accountNo.substring(0,2))){ 
                
                case "25":
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql1="SELECT NSAccountBalance FROM dbo.AccountNormalSavings WHERE NSAccountNumber='?'";
                        ps=conn.prepareStatement(sql1);
                        ps.setString(1,accountNo );
                        //executes the query
                        rs=ps.executeQuery();

                        amount = rs.getDouble(1);
                break;
                
                
                case "45":
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql2="SELECT BSAccountBalance FROM dbo.AccountBonusSavings WHERE BSAccountNumber='?'";
                        ps=conn.prepareStatement(sql2);
                        ps.setString(1,accountNo );
                        //executes the query
                        rs=ps.executeQuery();
                        amount = rs.getDouble(1);
                break;
                
                
                case "75":
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql3="SELECT PSAccountBalance FROM dbo.AccountPremierSavings WHERE PSAccountNumber='?'";
                        ps=conn.prepareStatement(sql3);
                        ps.setString(1,accountNo );
                        //executes the query
                        rs=ps.executeQuery();

                        amount = rs.getDouble(1);
                break;
                
                default:
                        System.out.println("invalid parameters");
                break;
            }
        
        
        return amount;
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    public double calcIncome(String accNo)
{               Connection conn;
                PreparedStatement ps;
                ResultSet rs =null ;
                double income = 0;
    
    try {       switch((accountNo.substring(0,2))){ 
                
                case "25":
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql1="SELECT TransactionAmount FROM dbo.CustomerTransactionDeposit WHERE tdTransactionDescriptionID = 'TD000002' AND ansNSAccountNumber IS NOT NULL AND ansNSAccountNumber = '?' AND TransactionDateTime BETWEEN '2018-04-01' AND '2019-04-01'";
                        ps=conn.prepareStatement(sql1);
                        ps.setString(1,accNo);
                        //executes the query
                        rs=ps.executeQuery();
                break;
                
                
                case "45":
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql2="SELECT TransactionAmount FROM dbo.CustomerTransactionDeposit WHERE tdTransactionDescriptionID = 'TD000002' AND absBSAccountNumber IS NOT NULL AND absBSAccountNumber = '?' AND TransactionDateTime BETWEEN '2018-04-01' AND '2019-04-01'";
                        ps=conn.prepareStatement(sql2);
                        ps.setString(1,accNo);
                        //executes the query
                        rs=ps.executeQuery();
                break;
                
                
                case "75":
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql3="SELECT TransactionAmount FROM dbo.CustomerTransactionDeposit WHERE tdTransactionDescriptionID = 'TD000002' AND apsPSAccountNumber IS NOT NULL AND apsPSAccountNumber = '?' AND TransactionDateTime BETWEEN '2018-04-01' AND '2019-04-01'";
                        ps=conn.prepareStatement(sql3);
                        ps.setString(1,accNo);
                        //executes the query
                        rs=ps.executeQuery();

                        
                break;
                
                default:
                        System.out.println("invalid parameters");
                break;
            }
                
                
                while(rs.next())
                {
                    income =income+ rs.getDouble(1);
                }
                return income;
            } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
}
    @Override
    public void updateDbTaxes_Balance()
    {   try {
        Connection conn;
        PreparedStatement ps;
        ResultSet rsCount,rs1,rs2,rs3,rs4;
        
        int i = 0;
        
                        conn=DriverManager.getConnection(DBConnection.DatabaseConnectionUrlStc());
                        //The select statement
                        String sql1="SELECT BSAccountNumber FROM dbo.AccountBonusSavings";
                        String sql2="SELECT NSAccountNumber FROM dbo.AccountNormalSavings";
                        String sql3="SELECT PSAccountNumber FROM dbo.AccountPremierSavings";
                        String sqlCount = "select(select COUNT(BSAccountNumber) FROM dbo.AccountBonusSavings)+"
                                        + "(select COUNT(NSAccountNumber) FROM dbo.AccountNormalSavings)+"
                                        + "(select COUNT(PSAccountNumber) FROM dbo.AccountPremierSavings)";
                        ps=conn.prepareStatement(sqlCount);
                        rsCount=ps.executeQuery();
                        while(rsCount.next()){i =rsCount.getInt(1);}
                        
                        String[] AccountNo = new String[i];
                        
                        
                        i=0;
                        
                        
                        ps=conn.prepareStatement(sql1);
                        rs1=ps.executeQuery();
                         while (rs1.next()) {
                            String em = rs1.getString("BSAccountNumber");
                            AccountNo[i] = em;
                            i++;
                            }
                         ps=conn.prepareStatement(sql2);
                        rs2=ps.executeQuery();
                         while (rs2.next()) {
                            String em = rs2.getString("NSAccountNumber");
                            AccountNo[i] = em;
                            i++;
                            }
                         ps=conn.prepareStatement(sql3);
                        rs3=ps.executeQuery();
                         while (rs3.next()) {
                            String em = rs3.getString("PSAccountNumber");
                            AccountNo[i] = em;
                            i++;
                            }
                         
                         
                       /*  String sqlTransactionNO = "select LAST(WTransactionNumber) from dbo.CustomerTransactionWithdrawal";
                         ps=conn.prepareStatement(sqlTransactionNO);
                           rs1=ps.executeQuery();
                          int TransactionID;
                           while(rs1.next())
                           {
                               TransactionID =rs1.getInt(1);
                           }*/
                                double Balance=0;
                                double income =0;
                                double taxVal =0;
                                double newBalance = 0;
                                Account Acc1 = new BonusSavings();
                                Account Acc2 = new BasicSavings();
                                Account Acc3 = new PremierSavings();
                           
                         for(String acc:AccountNo)
                         {
                         switch(acc.substring(0,2))
                         {
                           case "45":
                               String sql4="UPDATE dbo.AccountBonusSavings SET BSAccountBalance=? where BSAccountNumber=?";
                                ps=conn.prepareStatement(sql4);
                                
                                
                                Balance=Acc1.getAccountBalance(acc);
                                income =Acc1.calcIncome(acc);
                                taxVal =Acc1.CalcTax(income, Balance);
                                newBalance = Balance-taxVal;
                               
                                ps.setDouble(1,newBalance);
                                ps.setString(2,acc);
                                ps.executeQuery();
                                
                                
                                
                                String sql5="INSERT INTO dbo.CustomerTransactionWithdrawal(TransactionAmount,tdTransactionDescriptionID,ansNSAccountNumber,absBSAccountNumber,apsPSAccountNumber)"
                                        + "values('?','TD000001','?','?','?')";
                                ps.setDouble(1,newBalance);
                                ps.setString(2,null);
                                ps.setString(3,acc);
                                ps.setString(4,null);
                                ps=conn.prepareStatement(sql5);
                                ps.executeQuery();
                           break;
                           
                           
                           case "25":
                                String sql6="UPDATE dbo.AccountNormalSavings SET NSAccountBalance=? where NSAccountNumber=?";
                               ps=conn.prepareStatement(sql6);
                               
                                Balance=Acc2.getAccountBalance(acc);
                                income =Acc2.calcIncome(acc);
                                taxVal =Acc2.CalcTax(income, Balance);
                                newBalance = Balance-taxVal;
                               
                                ps.setDouble(1,newBalance);
                                ps.setString(2,acc);
                                ps.executeQuery();
                                
                                
                                
                                String sql7="INSERT INTO dbo.CustomerTransactionWithdrawal(TransactionAmount,tdTransactionDescriptionID,ansNSAccountNumber,absBSAccountNumber,apsPSAccountNumber)"
                                        + "values('?','TD000001','?','?','?')";
                                ps=conn.prepareStatement(sql7);
                                ps.setDouble(1,newBalance);
                                ps.setString(2,acc);
                                ps.setString(3,null);
                                ps.setString(4,null);
                                ps=conn.prepareStatement(sql7);
                                ps.executeQuery();
                           break;
                           
                           
                           case "75":
                               String sql8="UPDATE dbo.AccountNormalSaving SET NSAccountBalance=? where NSAccountNumber=?";
                               ps=conn.prepareStatement(sql8);
                               Balance=Acc3.getAccountBalance(acc);
                                income =Acc3.calcIncome(acc);
                                taxVal =Acc3.CalcTax(income, Balance);
                                newBalance = Balance-taxVal;
                               
                                ps.setDouble(1,newBalance);
                                ps.setString(2,acc);
                                ps.executeQuery();
                               
                               
                                
                                String sql9="INSERT INTO dbo.CustomerTransactionWithdrawal(TransactionAmount,tdTransactionDescriptionID,ansNSAccountNumber,absBSAccountNumber,apsPSAccountNumber)"
                                        + "values('?','TD000001','?','?','?')";
                                ps=conn.prepareStatement(sql9);
                                ps.setDouble(1,newBalance);
                                ps.setString(2,null);
                                ps.setString(3,null);
                                ps.setString(4,acc);
                                
                                ps.executeQuery();

                           break;
                         }
                    
                       
                         }
        
        
       
            } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
   

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

    public void depositeInterest() {
    }

    ;
    
    @Override
    public double interestDec(double amount) {

        return (amount * 0.2 / 12);

    }

    @Override
    public double interestMonth(double amount) {
        return (amount * 0.1 / 12);
    }

}
