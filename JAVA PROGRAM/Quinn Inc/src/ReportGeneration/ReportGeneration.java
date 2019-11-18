/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportGeneration;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import DatabaseConnection.DBConnection;
import LocalTimeAndDate.LocalTimeAndDate;

/**
 *
 * @author Lucas.L.H.H
 */

public class ReportGeneration{
    
    // Creating new object to retreive current date and time
    LocalTimeAndDate ltad;
    
    // Creating new object for database connection url
    DBConnection db;
    
    
    // Default Constructor
    public ReportGeneration(){
        // Creating new object to retrieve current date and time
        ltad = new LocalTimeAndDate();
        
        // Creating new object to retrieve database connection url
        db = new DBConnection(); 
    }
    
    public String generateManualDailyCTReport(String selectedDate, String selectdDateSql){
        // Creating a new document A4 sized
        Document dailyCustomerTransactionsPdfDocument = new Document(PageSize.A4);

        System.out.println("PDF Document is Created");
        
        System.out.println(selectedDate);
        
        try {
            // Creating PDF document to assigned pathway
            PdfWriter.getInstance(dailyCustomerTransactionsPdfDocument, new FileOutputStream("../Reports/"
                    + "Daily_Customer_Transaction_Record_Reports/Manual/Manual_Daily_Customer_Transaction_Records_'" + selectedDate + "'.pdf"));
            // Assigning PDF document author
            dailyCustomerTransactionsPdfDocument.addAuthor("Quinn_INC");
            // Assigning PDF document title
            dailyCustomerTransactionsPdfDocument.addTitle("Transaction Report");
            // Setting all four margins in PDF document
            dailyCustomerTransactionsPdfDocument.setMargins(10, 10, 10, 10);
            // Opening newly created PDF document
            dailyCustomerTransactionsPdfDocument.open();

            System.out.println("PDF Document is Open");

            // Retrieving Quinn_Inc header image to PDF document
            Image documentHeaderImg = Image.getInstance("src/Resources/Daily_Customer_Transaction_Report_Header_V1.jpg");

            // Fixing the image scale
            documentHeaderImg.scaleAbsolute(585f, 100f);

            // Adding header image to PDF document
            dailyCustomerTransactionsPdfDocument.add(documentHeaderImg);

            // Setting number of coloumns and size of each column
            float[] dailyCTWidths = {6, 8, 6, 6, 10};

            // Creating a table for records
            PdfPTable dailyCTTable = new PdfPTable(dailyCTWidths);

            // Setting total size of table width
            dailyCTTable.setWidthPercentage(87);

            // Creating dtable cell
            PdfPCell dailyCTTableCell;

            // Adding style to table headers
            Font header_style = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            // Table Headers
            String accountNumber = "ACCOUNT NUMBER";
            dailyCTTableCell = new PdfPCell(new Phrase(accountNumber, header_style));
            dailyCTTable.addCell(dailyCTTableCell);

            String transactionType = "TRANSACTION TYPE";
            dailyCTTableCell = new PdfPCell(new Phrase(transactionType, header_style));
            dailyCTTable.addCell(dailyCTTableCell);

            String accountType = "ACCOUNT TYPE";
            dailyCTTableCell = new PdfPCell(new Phrase(accountType, header_style));
            dailyCTTable.addCell(dailyCTTableCell);

            String amount = "AMOUNT";
            dailyCTTableCell = new PdfPCell(new Phrase(amount, header_style));
            dailyCTTable.addCell(dailyCTTableCell);

            String dateTime = "DATE TIME";
            dailyCTTableCell = new PdfPCell(new Phrase(dateTime, header_style));
            dailyCTTable.addCell(dailyCTTableCell);

            // Retrieving data from database and assigning to each cell
            try (Connection dataRetrievalCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement dataRetrievalSTMT = dataRetrievalCon.createStatement();) {

                // Assigning SQL query
                String dataRetrievalSqlQuery = "SELECT account_number, transaction_type, account_type, amount, date_time FROM customer_transaction "
                + "WHERE convert(nvarchar(50), date_time,126) LIKE '" + selectdDateSql + "%' ";
                
                // Executing same SQL query that was used before for record verification
                ResultSet dataRetrievalRs = dataRetrievalSTMT.executeQuery(dataRetrievalSqlQuery);

                // Iterating through each tuple in database and assigning to table cell
                while (dataRetrievalRs.next()) {
                    accountNumber = dataRetrievalRs.getString("ACCOUNT_NUMBER");
                    dailyCTTableCell = new PdfPCell(new Phrase(accountNumber));
                    dailyCTTable.addCell(dailyCTTableCell);

                    transactionType = dataRetrievalRs.getString("TRANSACTION_TYPE");
                    dailyCTTableCell = new PdfPCell(new Phrase(transactionType));
                    dailyCTTable.addCell(dailyCTTableCell);

                    accountType = dataRetrievalRs.getString("ACCOUNT_TYPE");
                    dailyCTTableCell = new PdfPCell(new Phrase(accountType));
                    dailyCTTable.addCell(dailyCTTableCell);

                    amount = "£ " + dataRetrievalRs.getString("AMOUNT");
                    dailyCTTableCell = new PdfPCell(new Phrase(amount));
                    dailyCTTable.addCell(dailyCTTableCell);

                    dateTime = dataRetrievalRs.getString("DATE_TIME");
                    dailyCTTableCell = new PdfPCell(new Phrase(dateTime));
                    dailyCTTable.addCell(dailyCTTableCell);
                }

                // Adding customer transaction table to PDF document
                dailyCustomerTransactionsPdfDocument.add(dailyCTTable);
            }
            // Error handling. Checking for any SQL related errors.
            catch (SQLException SQLEx) {
                System.out.println("Error found: " + SQLEx);
            }

            // Retrieving Quinn_Inc footer image to PDF document
            Image documentFooterImg = Image.getInstance("src/Resources/Customer_Transaction_Report_Footer_V1.jpg");

            // Fixing the image scale
            documentFooterImg.scaleAbsolute(570f, 30f);

            // Fixing position of image
            documentFooterImg.setAbsolutePosition(10f, 10f);

            // Adding image to PDF document
            dailyCustomerTransactionsPdfDocument.add(documentFooterImg);

            // Retrieving current date and time from localhost to show report generated date and time
            // Creating paragraph to show report generated date and time
            Paragraph exportDateAndTime = new Paragraph("Report Generated Period: " + ltad.retrieveLocalTimeAndDate());

            // Setting report generated date and time to center alignment
            exportDateAndTime.setAlignment(Element.ALIGN_CENTER);

            // Adding report generated date and time to PDF document
            dailyCustomerTransactionsPdfDocument.add(exportDateAndTime);

        }
        // Error handling. Checking for all possible errors
        catch (Throwable reportGenerationEx) {
            System.out.println("Error found: " + reportGenerationEx);
        }
        finally {
            // Closing PDF document
            dailyCustomerTransactionsPdfDocument.close();
            System.out.println("PDF Document is Closed");

            // Displaying message box showing confirmation message
            JOptionPane.showMessageDialog(null,
                "Daily Customer Transaction Records Report has been successfully generated.",
                "REPORT GENERATED CONFIRMATION",
                JOptionPane.INFORMATION_MESSAGE);   
        }
        
        // Returning value to show report generated confirmation
        return "Manual Daily Customer Transaction Report Generated";
    }
    
    
    
    public String generateManualMonthlyCTReport(String selectedMonthSqlCompatible){
     
        // retrieving current date from localhost
        String monthlyLocalDate = ltad.retrieveLocalDate();

        System.out.println("Date: " + monthlyLocalDate);
        
        // Creating new document
        Document monthlyCustomerTransactionReport = new Document(PageSize.A4);

        System.out.println("Document is created");

        try {
            // Setting pathway for new document
            PdfWriter.getInstance(monthlyCustomerTransactionReport, new FileOutputStream("../Reports/"
                    + "Monthly_Customer_Transaction_Record_Reports/Manual/Manual_Monthly_Customer_Transaction_Records_'" + monthlyLocalDate + "'.pdf"));
            
            monthlyCustomerTransactionReport.addAuthor("Quinn_INC");
            monthlyCustomerTransactionReport.addTitle("Monthly Customer Transaction Report");
            monthlyCustomerTransactionReport.setMargins(25, 10, 10, 10);

            // Opening document
            monthlyCustomerTransactionReport.open();

            System.out.println("Document is open");

            // Retrieving header image from pathway
            Image quinnIncMHeader = Image.getInstance("src/Resources/Monthly_Transaction_Report_Header_V1.jpg");

            // Setting image scale to fit document
            quinnIncMHeader.scaleAbsolute(555f, 110f);

            // Adding image to document
            monthlyCustomerTransactionReport.add(quinnIncMHeader);

            // Retriving customer account number from customer_trasaction relation
            try (Connection retrievingCNCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    Statement retrievingCNStmt = retrievingCNCon.createStatement();) {

                // Assigning SQL query
                String retrievingCNSqlQuery = "SELECT DISTINCT account_number FROM customer_transaction "
                        + "WHERE convert(nvarchar(50), date_time,126) LIKE '_____" + selectedMonthSqlCompatible + "%' ";

                // Executing SQL query
                ResultSet retrievingCNRs = retrievingCNStmt.executeQuery(retrievingCNSqlQuery);

                // Assigning customer number and retrieving other details
                while (retrievingCNRs.next()) {
                    String customerAccountNumberCT = retrievingCNRs.getString("ACCOUNT_NUMBER");

                    System.out.println(customerAccountNumberCT);

                    // Assigning column width for customer details table
                    float[] customerDetailsTableColumnWidths = {5, 7};

                    // Creating customer details table
                    PdfPTable customerDetailsTable = new PdfPTable(customerDetailsTableColumnWidths);

                    // Setting table width
                    customerDetailsTable.setWidthPercentage(60);

                    // Setting customer details table to left alignment
                    customerDetailsTable.setHorizontalAlignment(Element.ALIGN_LEFT);

                    // Setting space before table placement
                    customerDetailsTable.setSpacingBefore(10);

                    // Setting space after table placement
                    customerDetailsTable.setSpacingAfter(10);

                    // Creating table cells
                    PdfPCell customerDetailsTableCell;

                    // Setting column header style
                    Font headerStyle = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

                    // Customer details table column header
                    String accountNumber = "ACCOUNT NUMBER";
                    customerDetailsTableCell = new PdfPCell(new Phrase(accountNumber, headerStyle));
                    customerDetailsTable.addCell(customerDetailsTableCell);

                    // Customer details record data
                    // Assigning customer account number
                    customerDetailsTableCell = new PdfPCell(new Phrase(customerAccountNumberCT));
                    customerDetailsTable.addCell(customerDetailsTableCell);

                    // Customer details table column header
                    String customerName = "CUSTOMER NAME";
                    customerDetailsTableCell = new PdfPCell(new Phrase(customerName, headerStyle));
                    customerDetailsTable.addCell(customerDetailsTableCell);

                    // Customer details record data
                    // Retriving customer names from customer relation in the database
                    try (Connection customerNamesCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            Statement customerNamesStmt = customerNamesCon.createStatement();) {

                        // Assigning SQL query
                        String customerNamesSqlQuery = "SELECT first_name, middle_name, last_name FROM customer "
                                + "WHERE account_number = '" + customerAccountNumberCT + "' ";

                        // Executing SQL query
                        ResultSet customerNamesRs = customerNamesStmt.executeQuery(customerNamesSqlQuery);

                        // Iterating of returning data
                        while (customerNamesRs.next()) {
                            customerName = "First Name:      " + customerNamesRs.getString("FIRST_NAME")
                                    + "\nMiddle Name:   " + customerNamesRs.getString("MIDDLE_NAME")
                                    + "\nLast Name:      " + customerNamesRs.getString("LAST_NAME");
                            customerDetailsTableCell = new PdfPCell(new Phrase(customerName));
                            customerDetailsTable.addCell(customerDetailsTableCell);
                        }
                    } 
                    // Error handling. Checking for any SQL related errors.
                    catch (SQLException SqlEx) {
                        System.out.println("Error found: " + SqlEx);
                    }

                    // Customer details table column header
                    String accountType = "ACCOUNT TYPE";
                    customerDetailsTableCell = new PdfPCell(new Phrase(accountType, headerStyle));
                    customerDetailsTable.addCell(customerDetailsTableCell);

                    // Customer details record data
                    // Retriving customer type from customer relation in the database
                    try (Connection customerTypeCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            Statement customerTypeStmt = customerTypeCon.createStatement();) {

                        // Assigning SQL query
                        String customerTypeSqlQuery = "SELECT acc_type FROM customer "
                                + "WHERE account_number = '" + customerAccountNumberCT + "' ";

                        // Executing SQL query
                        ResultSet customerTypeRs = customerTypeStmt.executeQuery(customerTypeSqlQuery);

                        // Iterating returning data
                        while (customerTypeRs.next()) {
                            accountType = customerTypeRs.getString("ACC_TYPE");
                            customerDetailsTableCell = new PdfPCell(new Phrase(accountType));
                            customerDetailsTable.addCell(customerDetailsTableCell);
                        }
                    } 
                    // Error handling. Checking for any SQL related errors.
                    catch (SQLException SqlEx) {
                        System.out.println("Error found: " + SqlEx);
                    }

                    // Adding customer details table to document 
                    monthlyCustomerTransactionReport.add(customerDetailsTable);

                    System.out.println("Customer Details Table Complete");


                    // Assigning column widths for transaction details table
                    float[] transactionDetailsTableColumnWidths = {6, 8, 7, 8};

                    // Creating transaction details table
                    PdfPTable transactionDetailsTable = new PdfPTable(transactionDetailsTableColumnWidths);

                    // Setting table width
                    transactionDetailsTable.setWidthPercentage(87);

                    // Setting space after table placement
                    transactionDetailsTable.setSpacingAfter(5);

                    // Creating table cells
                    PdfPCell transactionDetailsTableCell;

                    // Creating column headers
                    String transactionNumber = "TRANSACTION NUMBER";
                    transactionDetailsTableCell = new PdfPCell(new Phrase(transactionNumber, headerStyle));
                    transactionDetailsTable.addCell(transactionDetailsTableCell);

                    String transactionType = "TRANSACTION TYPE";
                    transactionDetailsTableCell = new PdfPCell(new Phrase(transactionType, headerStyle));
                    transactionDetailsTable.addCell(transactionDetailsTableCell);

                    String transactionAmount = "TRANSACTION AMOUNT";
                    transactionDetailsTableCell = new PdfPCell(new Phrase(transactionAmount, headerStyle));
                    transactionDetailsTable.addCell(transactionDetailsTableCell);

                    String dateTime = "DATE TIME";
                    transactionDetailsTableCell = new PdfPCell(new Phrase(dateTime, headerStyle));
                    transactionDetailsTable.addCell(transactionDetailsTableCell);

                    // Transaction details record data
                    // Retriving transaction details from customer_transaction relation in the database
                    try (Connection transactionDetailsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            Statement transactionDetailsStmt = transactionDetailsCon.createStatement();) {

                        // Assigning SQL query
                        String transactionDetailsSqlQuery = "SELECT transaction_number, transaction_type, amount, date_time "
                                + "FROM customer_transaction "
                                + "WHERE account_number = '" + customerAccountNumberCT + "' ";

                        // Executing SQL query
                        ResultSet transactionDetailsRs = transactionDetailsStmt.executeQuery(transactionDetailsSqlQuery);

                        // Itegrating returning data
                        while (transactionDetailsRs.next()) {
                            transactionNumber = transactionDetailsRs.getString("TRANSACTION_NUMBER");
                            transactionDetailsTableCell = new PdfPCell(new Phrase(transactionNumber));
                            transactionDetailsTable.addCell(transactionDetailsTableCell);

                            transactionType = transactionDetailsRs.getString("TRANSACTION_TYPE");
                            transactionDetailsTableCell = new PdfPCell(new Phrase(transactionType));
                            transactionDetailsTable.addCell(transactionDetailsTableCell);

                            transactionAmount = transactionDetailsRs.getString("AMOUNT");
                            transactionDetailsTableCell = new PdfPCell(new Phrase(transactionAmount));
                            transactionDetailsTable.addCell(transactionDetailsTableCell);

                            dateTime = transactionDetailsRs.getString("DATE_TIME");
                            transactionDetailsTableCell = new PdfPCell(new Phrase(dateTime));
                            transactionDetailsTable.addCell(transactionDetailsTableCell);
                        }
                    } 
                    // Error handling. Checking for any SQL related errors.
                    catch (SQLException SqlEx) {
                        System.out.println("Error found: " + SqlEx);
                    }

                    // Adding transaction details table to document
                    monthlyCustomerTransactionReport.add(transactionDetailsTable);

                    Paragraph horizontalLine = new Paragraph("-----------------------------------------------------------------"
                            + "-------------------------------------------------------------------");

                    monthlyCustomerTransactionReport.add(horizontalLine);

                    System.out.println("Transaction Details Table Complete");
                }

                // Retrieving footer image from pathway
                Image quinnIncMFooter = Image.getInstance("src/Resources/Customer_Transaction_Report_Footer_V1.jpg");

                // Setting image scale to fit document
                quinnIncMFooter.scaleAbsolute(575f, 40f);

                // Setting image placement in document
                quinnIncMFooter.setAbsolutePosition(10f, 10f);

                // Adding image to document
                monthlyCustomerTransactionReport.add(quinnIncMFooter);

                // Retrieving current date and time, setting as report generation date and time 
                Paragraph reportGeneration = new Paragraph("Report Generated Period: " + ltad.retrieveLocalTimeAndDate());

                // Setting parapgraph to center alignment of the document
                reportGeneration.setAlignment(Element.ALIGN_CENTER);

                // Adding paragraph to document
                monthlyCustomerTransactionReport.add(reportGeneration);

                // Closing document
                monthlyCustomerTransactionReport.close();

                System.out.println("Document is closed");
            } 
            // Error handling. Checking for any SQL related errors.
            catch (SQLException SqlEx) {
                System.out.println("Error found: " + SqlEx);
            }
        } 
        // Error handling. Checking for any possible errors
        catch (Throwable ThrowableEx) {
            System.out.println("Error found: " + ThrowableEx);
        }

        finally {
            // Displaying message box showing confirmation message
            JOptionPane.showMessageDialog(null,
                "Monthly Customer Transaction Records Report has been successfully generated.",
                "REPORT GENERATED CONFIRMATION",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Returning value to show report generated confirmation
        return "Manual Monthly Customer Transaction Report Generated";
    }  
}
