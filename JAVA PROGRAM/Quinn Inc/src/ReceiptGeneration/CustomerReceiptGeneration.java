/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */
package ReceiptGeneration;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DatabaseConnection.DBConnection;
import LocalTimeAndDate.LocalTimeAndDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas.L.H.H
 */

public class CustomerReceiptGeneration {
    
     // Creating new object to retreive current date and time
    LocalTimeAndDate ltad;
    
    // Creating new object for database connection url
    DBConnection db;
    

    // Default Constructor
    public CustomerReceiptGeneration(){
        // Creating new object to retrieve current date and time
        ltad = new LocalTimeAndDate();

        // Creating new object to retrieve database connection url
        db = new DBConnection(); 
    }
    

    public String CRGeneration(int accountNumber, int ctransactionNumber, String transactionType, String transactionDateTime){
    
        // Retrieving current time from localhost
        String localTime = ltad.retrieveLocalTimeWith12HourClock();
        
        System.out.println("Current Time: " + localTime);
        
        // Retrieving current date and time from localhost
        String localDateTime = ltad.retrieveLocalDateTimeCReceipt();
        
        System.out.println("Current Date and Time: " + localDateTime);
        
        // Retrieving current date from localhost
        String localDate = ltad.retrieveLocalDate();
        
        // Creating a new document
        Document customerReceipt = new Document(PageSize.A4);
        
        System.out.println("Document is created");
 
        try {

            // Setting the pathway for the document
            // If transaction type is deposit the pathway is different
            if("Deposit".equals(transactionType)){
                PdfWriter.getInstance(customerReceipt, new FileOutputStream("../Receipts/Customer_Receipts/Deposit_Receipts/"
                        + "Customer_Transaction_Receipt_'"+ ctransactionNumber +"'_'"+ transactionDateTime +"'.pdf"));

            }
            // If transaction type is withdrawal the pathway is different
            if("Withdrawal".equals(transactionType)){
                PdfWriter.getInstance(customerReceipt, new FileOutputStream("../Receipts/Customer_Receipts/Withdrawal_Receipts/"
                        + "Customer_Transaction_Receipt_'"+ ctransactionNumber +"'_'"+ transactionDateTime +"'.pdf"));

            }  

            customerReceipt.addAuthor("Quinn_INC");
            customerReceipt.addTitle("Transaction Report");
            // Left, Right, Top, Bottom
            customerReceipt.setMargins(70, 70, 10, 10);//10

            // Opening newly created document
            customerReceipt.open();

            System.out.println("Document is open");

            // Setting a border to the document
            // Left , Right, Top, Bottom
            Rectangle pageBorder = new Rectangle(24,24,570,820);

            pageBorder.setBorder(Rectangle.BOX);

            // Setting the thickness of the border line
            pageBorder.setBorderWidth(2); 

            // Adding border to the document 
            customerReceipt.add(pageBorder);


            // Retrieving quinn inc logo from pathway
            Image quinnIncLogoImage = Image.getInstance("src/Resources/Quinn_Inc_Logo.jpg");

            // Adjusting scale of image to fit document
            // Width and Height
            quinnIncLogoImage.scaleAbsolute(160f, 100f);

            // Adjusting position of image in the document
            // x and y coordinates from the lower left corner
            quinnIncLogoImage.setAbsolutePosition(380f, 690f);  

            // Adding the image to the document
            customerReceipt.add(quinnIncLogoImage);


            // Assigning style for the header title
            Font headerStyle = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);

            // Adding header title to document
            String headerTitleText = "\n\n       Bank Receipt";

            Paragraph headerTitle = new Paragraph(headerTitleText, headerStyle);

            customerReceipt.add(headerTitle);


            // Assigning default text style
            Font defaultText = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

            // Adding transaction type header text, current time header and value to the document
            String localTimeTextRight = "Time: " + localTime;

            String transactionTypeHeaderLeft = "\n\n\nTransaction Type: ";

            Chunk pageSlitterTop = new Chunk(new VerticalPositionMark());

            Paragraph transactionTypeHeaderAndLocalDate = new Paragraph(transactionTypeHeaderLeft,defaultText);

            transactionTypeHeaderAndLocalDate.add(new Chunk(pageSlitterTop));

            transactionTypeHeaderAndLocalDate.add(localTimeTextRight);

            customerReceipt.add(transactionTypeHeaderAndLocalDate);



            String transactionTypeValueLeft = transactionType;

            // Adding transaction type value, current date header and value to the document
            String localDateTextRight = "Date: " + localDate;

            Chunk pageSlitterBottom = new Chunk(new VerticalPositionMark());

            Paragraph transactionTypeAndLocalDate = new Paragraph(transactionTypeValueLeft,defaultText);

            transactionTypeAndLocalDate.add(new Chunk(pageSlitterBottom));

            transactionTypeAndLocalDate.add(localDateTextRight);

            customerReceipt.add(transactionTypeAndLocalDate);


            // Adding horizontal line 1 to the document
            String horizontalLineSuper1 = "\n-----------------------------------------------------------"
                    + "------------------------------------------------------";

            Paragraph horizontalLinePSuper1 = new Paragraph(horizontalLineSuper1);

            customerReceipt.add(horizontalLinePSuper1);


            // Adding horizontal line 2 to the document
            String horizontalLineSub = "      ----------------------------------------------------------"
                    + "------------------------------------------";

            Paragraph horizontalLinePSub= new Paragraph(horizontalLineSub);

            customerReceipt.add(horizontalLinePSub);


            // Adding customer details header text to the document
            Paragraph customerDetailsText = new Paragraph("         Customer Details", (new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));

            customerReceipt.add(customerDetailsText);


            // Adding account number header and value to the document
            Paragraph cdAccountNumber = new Paragraph("             Account Number:                " + accountNumber,defaultText);

            customerReceipt.add(cdAccountNumber);

            String transactionAmountDB = "";

            // Checking if the transactionType is 'Deposit'
            if(transactionType == "Deposit"){
                // Retriving transaction details from the database
                try (Connection retrievingTransactionAmountCon = DriverManager.getConnection(db.DatabaseConnectionUrl());

                        Statement retrievingTransactionAmountStmt = retrievingTransactionAmountCon.createStatement();) {

                    // Assinging SQL query
                    String retrievingTransactionAmountSqlQuery = "SELECT TransactionAmount FROM CustomerTransactionDeposit WHERE "
                            + "DTransactionNumber = '"+ transactionAmountDB +"'";

                    // Executing SQL query
                    ResultSet retrievingTransactionAmountRs = retrievingTransactionAmountStmt.executeQuery(retrievingTransactionAmountSqlQuery);

                    // Assigning customer details to the variables
                    while (retrievingTransactionAmountRs.next()) {
                        transactionAmountDB = "             " + retrievingTransactionAmountRs.getString(1);
                    }

                } 
                // Error handling. Checks for SQL related errors
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                        "Error Occurred in SQL Database Connection",
                        "Customer Receipt - ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                }                       
            }
            
            // Checking if the transactionType is 'Withdrawal'
            if(transactionType == "Withdrawal"){
                // Retriving transaction details from the database
                try (Connection retrievingTransactionAmountCon = DriverManager.getConnection(db.DatabaseConnectionUrl());

                        Statement retrievingTransactionAmountStmt = retrievingTransactionAmountCon.createStatement();) {

                    // Assinging SQL query
                    String retrievingTransactionAmountSqlQuery = "SELECT TransactionAmount FROM CustomerTransactionWithdrawal WHERE "
                            + "WTransactionNumber = '"+ transactionAmountDB +"'";

                    // Executing SQL query
                    ResultSet retrievingTransactionAmountRs = retrievingTransactionAmountStmt.executeQuery(retrievingTransactionAmountSqlQuery);

                    // Assigning customer details to the variables
                    while (retrievingTransactionAmountRs.next()) {
                        transactionAmountDB = "             " + retrievingTransactionAmountRs.getString(1);
                    }
                } 
                // Error handling. Checks for SQL related errors
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                        "Error Occurred in SQL Database Connection",
                        "Customer Receipt - ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                }                       
            }
            
            // Extracting the first two characters in the accountNumber varialbe to identify the account type
            String accountNumberString = Integer.toString(accountNumber);
            String accountNumberTwoCharacters = "";

            if(accountNumberString.length() > 8){
                accountNumberTwoCharacters = accountNumberString.substring(0,2);
            }
            else{
                System.out.println("Length of Account Number is Invalid");
            }
            
            String cPassportNumberDB = "";
            
            // Checking the type of account and retrieving the data from the proper relation
            // Account Type: AccountNormalSavings
            if(accountNumberTwoCharacters == "25"){
                // Retriving customer passport number from the database, AccountNormalSavings relation
                try (Connection retrievingPassportNumberCon = DriverManager.getConnection(db.DatabaseConnectionUrl());

                        Statement retrievingPassportNumberStmt = retrievingPassportNumberCon.createStatement();) {

                    // Assinging SQL query
                    String retrievingPassportNumberSqlQuery = "SELECT cPassportNumber FROM AccountNormalSavings WHERE "
                            + "NSAccountNumber = '"+ accountNumber +"'";

                    // Executing SQL query
                    ResultSet retrievingPassportNumberRs = retrievingPassportNumberStmt.executeQuery(retrievingPassportNumberSqlQuery);

                    // Assigning customer passport number to the variable
                    while (retrievingPassportNumberRs.next()) {
                        cPassportNumberDB =  "             " + retrievingPassportNumberRs.getString(1);
                    }
                } 
                // Error handling. Checks for SQL related errors
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);

                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                        "Error Occurred in SQL Database Connection",
                        "Customer Receipt - ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                }     
            }

            // Account Type: AccountBonusSavings
            if(accountNumberTwoCharacters == "45"){
                // Retriving customer passport number from the database, AccountBonusSavings relation
                try (Connection retrievingPassportNumberCon = DriverManager.getConnection(db.DatabaseConnectionUrl());

                        Statement retrievingPassportNumberStmt = retrievingPassportNumberCon.createStatement();) {

                    // Assinging SQL query
                    String retrievingPassportNumberSqlQuery = "SELECT cPassportNumber FROM AccountBonusSavings WHERE "
                            + "BSAccountNumber = '"+ accountNumber +"'";

                    // Executing SQL query
                    ResultSet retrievingPassportNumberRs = retrievingPassportNumberStmt.executeQuery(retrievingPassportNumberSqlQuery);

                    // Assigning customer passport number to the variable
                    while (retrievingPassportNumberRs.next()) {
                        cPassportNumberDB =  "             " + retrievingPassportNumberRs.getString(1);
                    }
                } 
                // Error handling. Checks for SQL related errors
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                        "Error Occurred in SQL Database Connection",
                        "Customer Receipt - ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                }     
            }

            // Account Type: AccountPremierSavings
            if(accountNumberTwoCharacters == "75"){
                // Retriving customer passport number from the database, AccountPremierSavings relation
                try (Connection retrievingPassportNumberCon = DriverManager.getConnection(db.DatabaseConnectionUrl());

                        Statement retrievingPassportNumberStmt = retrievingPassportNumberCon.createStatement();) {

                    // Assinging SQL query
                    String retrievingPassportNumberSqlQuery = "SELECT cPassportNumber FROM AccountPremierSavings WHERE "
                            + "PSAccountNumber = '"+ accountNumber +"'";

                    // Executing SQL query
                    ResultSet retrievingPassportNumberRs = retrievingPassportNumberStmt.executeQuery(retrievingPassportNumberSqlQuery);

                    // Assigning customer passport number to the variable
                    while (retrievingPassportNumberRs.next()) {
                        cPassportNumberDB =  "             " + retrievingPassportNumberRs.getString(1);
                    }
                } 
                // Error handling. Checks for SQL related errors
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                        "Error Occurred in SQL Database Connection",
                        "Customer Receipt - ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                }     
            }         
            
            String cFirstNameDB = "";
            String cMiddleNameDB = "";
            String cLastNameDB = "";

            // Retriving customer details from the database
            try (Connection retrievingTransactionAmountCon = DriverManager.getConnection(db.DatabaseConnectionUrl());

                    Statement retrievingTransactionAmountStmt = retrievingTransactionAmountCon.createStatement();) {

                // Assinging SQL query
                String retrievingTransactionAmountSqlQuery = "SELECT FirstName, MiddleName, LastName FROM Customer WHERE "
                        + "PassportNumber = '"+ cPassportNumberDB +"'";

                // Executing SQL query
                ResultSet retrievingTransactionAmountRs = retrievingTransactionAmountStmt.executeQuery(retrievingTransactionAmountSqlQuery);

                // Assigning customer details to the variables
                while (retrievingTransactionAmountRs.next()) {
                    cFirstNameDB =  "             " + retrievingTransactionAmountRs.getString(1);
                    cMiddleNameDB = "             " + retrievingTransactionAmountRs.getString(2);
                    cLastNameDB = "             " + retrievingTransactionAmountRs.getString(3);
                }
            } 
            // Error handling. Checks for SQL related errors
            catch (SQLException SqlEx) {
                System.out.println("Error found: " + SqlEx);
                // Displaying message box showing error message
                JOptionPane.showMessageDialog(null,
                    "Error Occurred in SQL Database Connection",
                    "Customer Receipt - ERROR!",
                    JOptionPane.ERROR_MESSAGE);
            }       
            

            // Adding account type header and value to the document
            Paragraph cdAccountTypeP = new Paragraph("             Account Type:         " + transactionType,defaultText);

            customerReceipt.add(cdAccountTypeP);


            // Adding first name header and value to the document
            Paragraph cdFirstNameP = new Paragraph("             First Name:             " + cMiddleNameDB,defaultText);

            customerReceipt.add(cdFirstNameP);


            // Adding middle name header and value to the document
            Paragraph cdMiddleNameP = new Paragraph("             Middle Name:         " + cLastNameDB,defaultText);

            customerReceipt.add(cdMiddleNameP);


            // Adding last name header and value to the document
            Paragraph cdLastNameP = new Paragraph("             Last Name:             " + cLastNameDB,defaultText);

            customerReceipt.add(cdLastNameP);


            // Adding horizontal line 3 to the document
            customerReceipt.add(horizontalLinePSub);


            // Adding transaction details header to the document
            Paragraph transactionDetailsText = new Paragraph("          Transaction Details", (new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));

            customerReceipt.add(transactionDetailsText);


            // Adding tranaction number header and value to the document
            Paragraph transactionNumber = new Paragraph("             Transaction Number:                " + ctransactionNumber,defaultText);

            customerReceipt.add(transactionNumber);

            
            // Adding transaction amount header and value to the document
            Paragraph transactionAmountHeaderP = new Paragraph("             Transaction Amount:                " + transactionAmountDB,defaultText);

            customerReceipt.add(transactionAmountHeaderP);


            // Adding horizontal line 4 to the document
            customerReceipt.add(horizontalLinePSub);

            // Adding horizontal line 5 to the document
            String horizontalLineSuper2 = "-----------------------------------------------------------"
                    + "------------------------------------------------------";

            Paragraph horizontalLinePSuper2 = new Paragraph(horizontalLineSuper2);

            customerReceipt.add(horizontalLinePSuper2);


            // Adding thank you statement to the document
            Paragraph thankingText = new Paragraph("\nThank You for using Quinn Bank",defaultText);

            thankingText.setAlignment(Element.ALIGN_CENTER);

            customerReceipt.add(thankingText);


        } 
        catch (Exception ExceptionEx) {
            System.out.println("Error found: " + ExceptionEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Error Occurred - Exception",
                "Customer Receipt - ERROR!",
                JOptionPane.ERROR_MESSAGE);
        } 
        finally {
            // Closing the document
            customerReceipt.close();
            
            System.out.println("Document Closed");
        }
        
        // Returning value to show receipt generated confirmation
        return "Customer Transaction Receipt Generated";
    }
  
}
