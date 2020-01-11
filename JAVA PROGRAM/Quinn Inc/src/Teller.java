/**
* SOFT255SL COURSEWORK C1 T1
* Team No:1 
* Team Name: TEAM QUINN 
* Project: Bank Management System.
 */
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * When the asterisk '*' is included, it retrieves all the classes from the package
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;

import javax.swing.table.DefaultTableModel;

import DatabaseConnection.*;
import LocalTimeAndDate.LocalTimeAndDate;
import ReportGeneration.ReportGeneration;
import ReceiptGeneration.CustomerReceiptGeneration;

import java.util.regex.*;
import javax.swing.SwingConstants;

/**
 * This form carries out the highest number of tasks, such as withdrawal and deposits
 * done by W.A.D.N.N and M.I.C.B, Monthly Interest done by R.P.L and H.V.L.H, Receipt Generation, Report Generation
 * and Customer Account Creation done by H.V.L.H
 * @author ranul
 */
public class Teller extends javax.swing.JFrame {
    
    // Creating new object to calculate the number of existing customer accounts available
    NoOfCustomerAccounts noca;
    
    // Creating new object to insert account details
    NewAccountCreation anc;

    // Declaring variable to retrieve user selected account type from the list
    String userSelectedAccountType = "";

    // Declaring global variable to assign value in the parameterized constructor
    String systemLoginIDGlobal = "";
    
    // Declaring variable to store the tellerID that is retrieved from the database
    String tellerIDDB = "";

    // Variable declaraton for assigning the new account number. 
    // Variable declared here because now it is accessaible within the whole code block
    int newAccountNumber = 0;

    // Creating a new object to call retrievingPreviousAccountNumber method
    NewAccountNumberGeneration ang;

    // Creating new table model for customer transaction records table in daily reports tab
    DefaultTableModel customerTransactionRecordsTableModel;

    // Creating new table model for customer details record table in monthly reports tab
    DefaultTableModel customerDetailsRecordTableModel;

    // Creating new table model for transaction details records table in monthly reports tab
    DefaultTableModel transactionDetailsRecordsTableModel;

    // Creating new object to retreive current date and time
    LocalTimeAndDate ltad;

    // Creating new object for database connection url
    DBConnection db;

    // Creating new object to call procedures, report generating
    ReportGeneration rg;

    // Creating new object to call procedure, receipt generating
    CustomerReceiptGeneration crg;

    /**
     * Creates new form Teller
     */
    public Teller() { // Default constructor
        initComponents();
        date.setText(java.time.LocalDate.now().toString());
        
        // Creating new object to calculate the number of existing customer accounts available
        noca = new NoOfCustomerAccounts();       
        
        
        // Setting text field to right text alignment
        txtMonthlyIncomeRate.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Assigning a default value to the text field
        txtMonthlyIncomeRate.setText("0.00");
        

        // Making the account number textfield in 'NEW Customer' panel not editable by the user
        txtAccountNumber.setEditable(false);

        // Creating new object to retrieve current date and time
        ltad = new LocalTimeAndDate();

        // Creating new object to insert account details
        anc = new NewAccountCreation();

        // Creating a new object to call retrievingPreviousAccountNumber method
        ang = new NewAccountNumberGeneration();

        // Creating new object to retrieve database connection url
        db = new DBConnection();

        // Creating new object to call procedures, report generating
        rg = new ReportGeneration();

        // Creating new object to call procedure, receipt generating
        crg = new CustomerReceiptGeneration();

        // Setting current time
        jlbl_localTime.setText(ltad.retrieveLocalTimeWith12HourClock());

        // Setting current date
        jlbl_localDate.setText(ltad.retrieveLocalDate());

        // Involving 'Monthly Report' panel in 'Customer Transaction Reports' panel
        // Assigning values to month chioce  
        cmbMonthlyReportMonth.add("");
        cmbMonthlyReportMonth.add("January");
        cmbMonthlyReportMonth.add("February");
        cmbMonthlyReportMonth.add("March");
        cmbMonthlyReportMonth.add("April");
        cmbMonthlyReportMonth.add("May");
        cmbMonthlyReportMonth.add("June");
        cmbMonthlyReportMonth.add("July");
        cmbMonthlyReportMonth.add("August");
        cmbMonthlyReportMonth.add("September");
        cmbMonthlyReportMonth.add("October");
        cmbMonthlyReportMonth.add("November");
        cmbMonthlyReportMonth.add("December");

        // Assigning table model to the customer details record table
        customerDetailsRecordTableModel = (DefaultTableModel) jtb_customerDetailsRecord.getModel();

        // Assigning columns header name for customer details record table
        customerDetailsRecordTableModel.addColumn("Account Number");
        customerDetailsRecordTableModel.addColumn("Customer First Name:");
        customerDetailsRecordTableModel.addColumn("Customer Middle Name");
        customerDetailsRecordTableModel.addColumn("Customer Last Name");
        customerDetailsRecordTableModel.addColumn("Account Type");

        // Assigning table model to the transaction details records table
        transactionDetailsRecordsTableModel = (DefaultTableModel) jtb_transactionDetailsRecords.getModel();

        // Assigning columns header name for transaction details records table
        transactionDetailsRecordsTableModel.addColumn("Transaction Number");
        transactionDetailsRecordsTableModel.addColumn("Account Number");
        transactionDetailsRecordsTableModel.addColumn("Transaction Type");
        transactionDetailsRecordsTableModel.addColumn("Transaction Amount");
        transactionDetailsRecordsTableModel.addColumn("Date Time");

        // Involving daily reports tab
        // Assigning table model to the customer transaction records table
        customerTransactionRecordsTableModel = (DefaultTableModel) jtb_customerTransactionRecords.getModel();

        // Assigning columns header name for customer transaction records table
        customerTransactionRecordsTableModel.addColumn("Account Number");
        customerTransactionRecordsTableModel.addColumn("Transaction Type");
        customerTransactionRecordsTableModel.addColumn("Account Type");
        customerTransactionRecordsTableModel.addColumn("Amount");
        customerTransactionRecordsTableModel.addColumn("Date Time");

        // Automatic daily customer transaction report generation
        // Retrieving current date
        String localDate = ltad.retrieveLocalDate();

        System.out.println("Date: " + localDate);

        // Retrieving current date for SQL query, different format (yyyy-MM-dd)
        String localDateSqlQuery = ltad.retrieveLocalDateSqlQuery();

        System.out.println("Date (SQL format): " + localDateSqlQuery);

        // Retrieving current time only hour and minute
        String localTimeHourMin = ltad.retrieveLocalTimeHourMin();

        // Converting localTimeHourMin to float to check for the relavent conditions
        float localTimeHourMinFloat = Float.valueOf(localTimeHourMin);

        System.out.println("Time (Float Data Type): " + localTimeHourMinFloat);

        if ((localTimeHourMinFloat >= 00.00) & (localTimeHourMinFloat <= 23.59)) { // Condition checking if the current time is inbetween 00.00 to 23.59
            if ((localTimeHourMinFloat >= 10.00) & (localTimeHourMinFloat <= 14.00)) { // Condition checking if current time is inbetween 10.00 to 14.00

                // checking if the daily report was already generated for the same day
                File checkFileExistence = new File("../Reports/Daily_Customer_Transaction_Record_Reports/Automatic/"
                        + "Automatic_Daily_Customer_Transaction_Records_'" + localDate + "'.pdf");

                boolean checkFileExistenceResult = checkFileExistence.exists();

                System.out.println("Automatic Daily Report Exist: " + checkFileExistenceResult);

                // If report is not exisiting, a new one will be generated
                if (checkFileExistenceResult == false) {
                    // Creating a new document
                    Document autoDailyCTDocument = new Document(PageSize.A4);

                    System.out.println("Document is created");

                    try {
                        // Checking if any records are available in the database for this month
                        try (Connection checkingDataCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement checkingDataStmt = checkingDataCon.createStatement();) {

                            // Assigning SQL query
                            String checkingDataSqlQuery = "SELECT account_number, transaction_type, account_type, amount, date_time FROM customer_transaction WHERE "
                                    + "convert(nvarchar(50), date_time,126) LIKE '" + localDateSqlQuery + "%' ";

                            // Executing SQL query
                            ResultSet checkingDataRs = checkingDataStmt.executeQuery(checkingDataSqlQuery);

                            //If any record data was not returned
                            if (checkingDataRs.next() == false) {

                                // Assigning pathway for the document
                                PdfWriter.getInstance(autoDailyCTDocument, new FileOutputStream("../Reports/Daily_Customer_Transaction_Record_Reports/Automatic/"
                                        + "Automatic_Daily_Customer_Transaction_Records_'" + localDate + "'.pdf"));

                                autoDailyCTDocument.addAuthor("Quinn_INC");
                                autoDailyCTDocument.addTitle("Transaction Report");
                                autoDailyCTDocument.setMargins(10, 10, 10, 10);

                                autoDailyCTDocument.open();
                                System.out.println("Document is open");

                                // Retrieving header image from pathway
                                Image quinnIncHeaderImage = Image.getInstance("src/Resources/Daily_Customer_Transaction_Report_Header_V1.jpg");

                                // Setting image scale to fit document
                                quinnIncHeaderImage.scaleAbsolute(585f, 100f);

                                // Adding image to document
                                autoDailyCTDocument.add(quinnIncHeaderImage);

                                // Assigning column width size for table
                                float[] autoCTTableColumnWidths = {6, 8, 6, 6, 10};

                                // Creating new table
                                PdfPTable autoCTTable = new PdfPTable(autoCTTableColumnWidths);

                                // Assigning total width of the table
                                autoCTTable.setWidthPercentage(87);

                                // Creating new object for each cell in the table
                                PdfPCell autoCTTableCell;

                                // Adding font style for column headers in table
                                Font headerStyle = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

                                // Column headers
                                String accountNumber = "ACCOUNT NUMBER";
                                autoCTTableCell = new PdfPCell(new Phrase(accountNumber, headerStyle));
                                autoCTTable.addCell(autoCTTableCell);

                                String transactionType = "TRANSACTION TYPE";
                                autoCTTableCell = new PdfPCell(new Phrase(transactionType, headerStyle));
                                autoCTTable.addCell(autoCTTableCell);

                                String accountType = "ACCOUNT TYPE";
                                autoCTTableCell = new PdfPCell(new Phrase(accountType, headerStyle));
                                autoCTTable.addCell(autoCTTableCell);

                                String amount = "AMOUNT";
                                autoCTTableCell = new PdfPCell(new Phrase(amount, headerStyle));
                                autoCTTable.addCell(autoCTTableCell);

                                String dateTime = "DATE TIME";
                                autoCTTableCell = new PdfPCell(new Phrase(dateTime, headerStyle));
                                autoCTTable.addCell(autoCTTableCell);

                                // Adding table to document
                                autoDailyCTDocument.add(autoCTTable);

                                // Assigning paragraph to show message in document
                                Paragraph noRecordAvailable = new Paragraph("No Records are Available for this day");

                                noRecordAvailable.setAlignment(Element.ALIGN_CENTER);

                                autoDailyCTDocument.add(noRecordAvailable);

                                // Creating new object to retrieve current date and time
                                ltad = new LocalTimeAndDate();

                                // Creating parapgraph to represent the report generated date and time
                                Paragraph exportDateAndTime = new Paragraph("Report Generated Period: " + ltad.retrieveLocalTimeAndDate());

                                // Setting paragraph to the center alignment of the document
                                exportDateAndTime.setAlignment(Element.ALIGN_CENTER);

                                // dding paragraph to the document
                                autoDailyCTDocument.add(exportDateAndTime);

                                // Retrieving footer image from pathway
                                Image quinnIncFooterImage = Image.getInstance("src/Resources/Customer_Transaction_Report_Footer_V1.jpg");

                                // Setting image scale to fit document
                                quinnIncFooterImage.scaleAbsolute(570f, 30f);

                                // Setting image to an appropriate place in document
                                quinnIncFooterImage.setAbsolutePosition(10f, 10f);

                                // Adding image to the document
                                autoDailyCTDocument.add(quinnIncFooterImage);
                            } // If any record data was returned
                            else {
                                // Assigning pathway for the document
                                PdfWriter.getInstance(autoDailyCTDocument, new FileOutputStream("../Reports/Daily_Customer_Transaction_Record_Reports/"
                                        + "Automatic/Automatic_Daily_Customer_Transaction_Records_'" + localDate + "'.pdf"));

                                autoDailyCTDocument.addAuthor("Quinn_INC");
                                autoDailyCTDocument.addTitle("Transaction Report");
                                autoDailyCTDocument.setMargins(10, 10, 10, 10);

                                autoDailyCTDocument.open();
                                System.out.println("Document is open");

                                // Retrieving header image from pathway
                                Image quinnIncHeaderImage = Image.getInstance("src/Resources/Daily_Customer_Transaction_Report_Header_V1.jpg");

                                // Setting image scale to fit document
                                quinnIncHeaderImage.scaleAbsolute(585f, 100f);

                                // Adding image to document
                                autoDailyCTDocument.add(quinnIncHeaderImage);

                                // Assigning column width size for table
                                float[] autoCTTableColumnWidths = {6, 8, 6, 6, 10};

                                // Creating new table
                                PdfPTable autoCTTable = new PdfPTable(autoCTTableColumnWidths);

                                // Assigning total width of the table
                                autoCTTable.setWidthPercentage(87);

                                // Creating new object for each cell in the table
                                PdfPCell autoCTTableCell;

                                // Retriving record data from database
                                try (Connection retrievingDataCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                        Statement retrievingDataStmt = retrievingDataCon.createStatement();) {

                                    // Executing SQL query, uses the same SQL query as used in checkingDataSqlQuery
                                    ResultSet retrievingDataRs = retrievingDataStmt.executeQuery(checkingDataSqlQuery);

                                    // Adding font style for column headers in table
                                    Font header_style = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

                                    // Column headers
                                    String accountNumber = "ACCOUNT NUMBER";
                                    autoCTTableCell = new PdfPCell(new Phrase(accountNumber, header_style));
                                    autoCTTable.addCell(autoCTTableCell);

                                    String transactionType = "TRANSACTION TYPE";
                                    autoCTTableCell = new PdfPCell(new Phrase(transactionType, header_style));
                                    autoCTTable.addCell(autoCTTableCell);

                                    String accountType = "ACCOUNT TYPE";
                                    autoCTTableCell = new PdfPCell(new Phrase(accountType, header_style));
                                    autoCTTable.addCell(autoCTTableCell);

                                    String amount = "AMOUNT";
                                    autoCTTableCell = new PdfPCell(new Phrase(amount, header_style));
                                    autoCTTable.addCell(autoCTTableCell);

                                    String dateTime = "DATE TIME";
                                    autoCTTableCell = new PdfPCell(new Phrase(dateTime, header_style));
                                    autoCTTable.addCell(autoCTTableCell);

                                    // All the returning data is iterated and assigned to each cell accordingly
                                    while (retrievingDataRs.next()) {
                                        accountNumber = retrievingDataRs.getString("ACCOUNT_NUMBER");
                                        autoCTTableCell = new PdfPCell(new Phrase(accountNumber));
                                        autoCTTable.addCell(autoCTTableCell);

                                        transactionType = retrievingDataRs.getString("TRANSACTION_TYPE");
                                        autoCTTableCell = new PdfPCell(new Phrase(transactionType));
                                        autoCTTable.addCell(autoCTTableCell);

                                        accountType = retrievingDataRs.getString("ACCOUNT_TYPE");
                                        autoCTTableCell = new PdfPCell(new Phrase(accountType));
                                        autoCTTable.addCell(autoCTTableCell);

                                        amount = "£ " + retrievingDataRs.getString("AMOUNT");
                                        autoCTTableCell = new PdfPCell(new Phrase(amount));
                                        autoCTTable.addCell(autoCTTableCell);

                                        dateTime = retrievingDataRs.getString("DATE_TIME");
                                        autoCTTableCell = new PdfPCell(new Phrase(dateTime));
                                        autoCTTable.addCell(autoCTTableCell);
                                    }

                                    // Adding table to document
                                    autoDailyCTDocument.add(autoCTTable);
                                } // Error handling. Handles any SQL related errors.
                                catch (SQLException SqlEx) {
                                    System.out.println("Error found: " + SqlEx);
                                    // Displaying message box showing error message
                                    JOptionPane.showMessageDialog(null,
                                            "Error Occurred in SQL Connection",
                                            "Automatic Daily Customer Transaction Report Generation - ERROR!",
                                            JOptionPane.ERROR_MESSAGE);
                                }

                                // Retrieving footer image from pathway
                                Image quinnIncFooterImage = Image.getInstance("src/Resources/Customer_Transaction_Report_Footer_V1.jpg");

                                // Setting image scale to fit document
                                quinnIncFooterImage.scaleAbsolute(570f, 30f);

                                // Setting image to an appropriate place in document
                                quinnIncFooterImage.setAbsolutePosition(10f, 10f);

                                // Adding image to the document
                                autoDailyCTDocument.add(quinnIncFooterImage);

                                // Creating new object to retrieve current date and time
                                ltad = new LocalTimeAndDate();

                                // Creating parapgraph to represent the report generated date and time
                                Paragraph exportDateAndTime = new Paragraph("Report Generated Period: " + ltad.retrieveLocalTimeAndDate());

                                // Setting paragraph to the center alignment of the document
                                exportDateAndTime.setAlignment(Element.ALIGN_CENTER);

                                // dding paragraph to the document
                                autoDailyCTDocument.add(exportDateAndTime);
                            }
                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Unable to verify data. Error found: " + SqlEx);
                            // Displaying message box showing error message
                            JOptionPane.showMessageDialog(null,
                                    "Error Occurred in SQL Connection",
                                    "Automatic Daily Customer Transaction Report Generation - ERROR!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } // Error handling. Handles any possible errors.
                    catch (Exception ExceptionEx) {
                        System.out.println("Error found: " + ExceptionEx);
                        // Displaying message box showing error message
                        JOptionPane.showMessageDialog(null,
                                "Error Occurred in Exception",
                                "Automatic Daily Customer Transaction Report Generation - ERROR!",
                                JOptionPane.ERROR_MESSAGE);
                    } finally {
                        autoDailyCTDocument.close();
                        System.out.println("Document Closed");
                    }
                }
                // Assigning string to show automate report generation status
                lbl_status.setText("Report is Up To Date");
            } else {
                // Assigning string to show automate report generation status
                lbl_status.setText("Report Generation Pending, Awaiting Till Default Time");
            }
        }

        // Automatic monthly customer transaction report generation
        // retrieving current date from localhost
        String monthlylocalDate = ltad.retrieveLocalDate();

        System.out.println("Date: " + monthlylocalDate);

        // retrieving current month from localhost
        String monthlylocalMonth = ltad.retrieveLocalMonth();

        System.out.println("Month: " + monthlylocalMonth);

        // retrieving current day from localhost
        String localDay = ltad.retrieveLocalDay();

        System.out.println("Day: " + localDay);

        int localDayInt = Integer.parseInt(localDay);

        if ((localDayInt >= 1) & (localDayInt <= 31)) { // Condition to check if the cuurent day is inbetween 1 and 31
            if ((localDayInt >= 1) || (localDayInt <= 22)) { // Condition to check is the day is inbetween 1 and 22, this is the report generation time period
                // Condition has two days is becasue one of these days could be a non-working day, and application won't be used, hence the report won't be generated

                // checking if monthly report is already generated
                File checkFileExistence = new File("../Reports/Monthly_Customer_Transaction_Record_Reports/Automatic/"
                        + "Automatic_Monthly_Customer_Transaction_Records_'" + monthlylocalDate + "'.pdf");
                boolean checkFileExistenceResult = checkFileExistence.exists();

                System.out.println("Automatic Monthly Report Exist: " + checkFileExistenceResult);

                // If the report doesn't already exist
                if (checkFileExistenceResult == false) {

                    // Creating new document
                    Document monthlyCustomerTransactionReport = new Document(PageSize.A4);

                    System.out.println("Document is created");

                    // Declaring new variable to check if there are new deposit records available for this month
                    Boolean newDepositRecordAvailability = false;

                    // Checking if any deposit records are available in the database for this month
                    try (Connection checkingDTransactionsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            Statement checkingDTransactionStmt = checkingDTransactionsCon.createStatement();) {

                        // Assigning SQL query
                        String checkingDTransactionSqlQuery = "SELECT * FROM CustomerTransactionDeposit WHERE "
                                + "convert(nvarchar(50), TransactionDateTime,126) LIKE '_____" + monthlylocalMonth + "%' ";

                        // Executing SQL query
                        ResultSet checkingDTransactionsRs = checkingDTransactionStmt.executeQuery(checkingDTransactionSqlQuery);

                        System.out.println("Transactions Record Exist: " + checkingDTransactionsRs);

                        // If no values are returned
                        if (checkingDTransactionsRs.next() == false) {

                            newDepositRecordAvailability = false;

                        } // If any values are returned
                        else {

                            newDepositRecordAvailability = true;

                        }
                    } // Error handling. Handles any SQL related errors.
                    catch (SQLException SqlEx) {
                        System.out.println("Error found: " + SqlEx);
                        // Displaying message box showing error message
                        JOptionPane.showMessageDialog(null,
                                "Error Occurred in SQL Connection",
                                "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    // Declaring new variable to check if there are new withdrawal records available for this month
                    Boolean newWithdrawalRecordAvailability = false;

                    // Checking if any deposit records are available in the database for this month
                    try (Connection checkingWTransactionsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            Statement checkingWTransactionStmt = checkingWTransactionsCon.createStatement();) {

                        // Assigning SQL query
                        String checkingWTransactionSqlQuery = "SELECT FROM CustomerTransactionWithdrawal WHERE "
                                + "convert(nvarchar(50), TransactionDateTime,126) LIKE '_____" + monthlylocalMonth + "%' ";

                        // Executing SQL query
                        ResultSet checkingWTransactionsRs = checkingWTransactionStmt.executeQuery(checkingWTransactionSqlQuery);

                        System.out.println("Transactions Record Exist: " + checkingWTransactionsRs);

                        // If no values are returned
                        if (checkingWTransactionsRs.next() == false) {

                            newWithdrawalRecordAvailability = false;

                        } // If any values are returned
                        else {

                            newDepositRecordAvailability = true;

                        }
                    } // Error handling. Handles any SQL related errors.
                    catch (SQLException SqlEx) {
                        System.out.println("Error found: " + SqlEx);
                        // Displaying message box showing error message
                        JOptionPane.showMessageDialog(null,
                                "Error Occurred in SQL Connection",
                                "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    // Checking if deposit or withdrawal records are available for this month
                    // If there are no records available
                    if (newDepositRecordAvailability == false || newWithdrawalRecordAvailability == false) {
                        try {
                            // Setting pathway for new document
                            PdfWriter.getInstance(monthlyCustomerTransactionReport, new FileOutputStream("../Reports/Monthly_Customer_Transaction_Record_Reports/Automatic/"
                                    + "Automatic_Monthly_Customer_Transaction_Records_'" + monthlylocalDate + "'.pdf"));

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
                            Font headerStyle = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

                            // Customer details table column header
                            String accountNumber = "ACCOUNT NUMBER";
                            customerDetailsTableCell = new PdfPCell(new Phrase(accountNumber, headerStyle));
                            customerDetailsTable.addCell(customerDetailsTableCell);

                            // Customer details record data
                            String accountNumberNotFound = " No Records are Available for this Month ";
                            customerDetailsTableCell = new PdfPCell(new Phrase(accountNumberNotFound));
                            customerDetailsTable.addCell(customerDetailsTableCell);

                            // Customer details table column header
                            String customerName = "CUSTOMER NAME";
                            customerDetailsTableCell = new PdfPCell(new Phrase(customerName, headerStyle));
                            customerDetailsTable.addCell(customerDetailsTableCell);

                            // Customer details record data
                            String customerNameNotFound = " No Records are Available for this Month ";
                            customerDetailsTableCell = new PdfPCell(new Phrase(customerNameNotFound));
                            customerDetailsTable.addCell(customerDetailsTableCell);

                            // Customer details table column header
                            String accountType = "ACCOUNT TYPE";
                            customerDetailsTableCell = new PdfPCell(new Phrase(accountType, headerStyle));
                            customerDetailsTable.addCell(customerDetailsTableCell);

                            // Customer details record data
                            String accountTypeNotFound = " No Records are Available for this Month ";
                            customerDetailsTableCell = new PdfPCell(new Phrase(accountTypeNotFound));
                            customerDetailsTable.addCell(customerDetailsTableCell);

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

                            // Adding transaction details table to document
                            monthlyCustomerTransactionReport.add(transactionDetailsTable);

                            // Assigning paragraph to show message in document
                            Paragraph noRecordAvailable = new Paragraph("No Records are Available for this Month");

                            noRecordAvailable.setAlignment(Element.ALIGN_CENTER);

                            // Assigning paragraph to show message in document
                            monthlyCustomerTransactionReport.add(noRecordAvailable);

                            Paragraph horizontalLine = new Paragraph("-----------------------------------------------------------------"
                                    + "-------------------------------------------------------------------");

                            monthlyCustomerTransactionReport.add(horizontalLine);

                            System.out.println("Transaction Details Table Complete");

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
                        } // Error handling. Catches any possible errors
                        catch (Exception ExceptionEx) {
                            System.out.println("Error found: " + ExceptionEx);
                            // Displaying message box showing error message
                            JOptionPane.showMessageDialog(null,
                                    "Error Occurred in Exception",
                                    "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } // If there are records available
                    else if (newDepositRecordAvailability == true || newWithdrawalRecordAvailability == true) {

                        try {
                            // Setting pathway for new document
                            PdfWriter.getInstance(monthlyCustomerTransactionReport, new FileOutputStream("../Reports/Monthly_Customer_Transaction_Record_Reports/"
                                    + "Automatic/Automatic_Monthly_Customer_Transaction_Records_'" + monthlylocalDate + "'.pdf"));

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

                            // Retrieving customer account number from the database
                            try (Connection retrievingCNCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                    Statement retrievingCNStmt = retrievingCNCon.createStatement();) {

                                // Assigning SQL query
                                String retrievingCNSqlQuery = "SELECT DISTINCT account_number FROM customer_transaction "
                                        + "WHERE convert(nvarchar(50), date_time,126) LIKE '_____" + monthlylocalMonth + "%' ";

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
                                    Font headerStyle = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

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
                                    } // Error handling. Handles any SQL related errors.
                                    catch (SQLException SqlEx) {
                                        System.out.println("Error found: " + SqlEx);
                                        // Displaying message box showing error message
                                        JOptionPane.showMessageDialog(null,
                                                "Error Occurred in SQL Connection",
                                                "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                                JOptionPane.ERROR_MESSAGE);
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
                                    } // Error handling. Handles any SQL related errors.
                                    catch (SQLException SqlEx) {
                                        System.out.println("Error found: " + SqlEx);
                                        // Displaying message box showing error message
                                        JOptionPane.showMessageDialog(null,
                                                "Error Occurred in SQL Connection",
                                                "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                                JOptionPane.ERROR_MESSAGE);
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
                                    } // Error handling. Handles any SQL related errors.
                                    catch (SQLException SqlEx) {
                                        System.out.println("Error found: " + SqlEx);
                                        // Displaying message box showing error message
                                        JOptionPane.showMessageDialog(null,
                                                "Error Occurred in SQL Connection",
                                                "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                                JOptionPane.ERROR_MESSAGE);
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
                            } // Error handling. Handles any SQL related errors.
                            catch (SQLException SqlEx) {
                                System.out.println("Error found: " + SqlEx);
                                // Displaying message box showing error message
                                JOptionPane.showMessageDialog(null,
                                        "Error Occurred in SQL Connection",
                                        "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } // Error handling. Handles any possible errors.
                        catch (Exception ExceptionEx) {
                            System.out.println("Error found: " + ExceptionEx);
                            // Displaying message box showing error message
                            JOptionPane.showMessageDialog(null,
                                    "Error Occurred in Exception",
                                    "Automatic Monthly Customer Transaction Report Generation - ERROR!",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    }

                }
                // Assigning string to show automate report generation status
                lbl_mstatus.setText("Report is Up To Date");
            } else {
                // Assigning string to show automate report generation status
                lbl_mstatus.setText("Report Generation Pending, Awaiting Till Default Time");
            }
        }
    }

    public Teller(String systemLoginIDTeller) { // Parameterized constructor, used to pass variable values among classes
        this(); // This statement allows for the default constructor to run
        systemLoginIDGlobal = systemLoginIDTeller;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        logout = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        NewUser = new javax.swing.JTabbedPane();
        savingsAcc_pnl = new javax.swing.JTabbedPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        dOption_pnl = new javax.swing.JPanel();
        ClearSA_Btn = new javax.swing.JButton();
        CheckSA_Btn = new javax.swing.JButton();
        SubmitSA = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        Deposit_pnl = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        dAccount = new javax.swing.JLabel();
        DAccno_Txt = new javax.swing.JTextField();
        holderName_Txt = new javax.swing.JTextField();
        dName_Lbl = new javax.swing.JLabel();
        dBalance_Lbl = new javax.swing.JLabel();
        currentBalance_Txt = new javax.swing.JTextField();
        ACCType_Txt = new javax.swing.JTextField();
        dType_Lbl = new javax.swing.JLabel();
        dAmmount_Lbl = new javax.swing.JLabel();
        DEPAmount_Txt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        bonusINFO_tarea = new javax.swing.JTextArea();
        FinalDeposit_Txt = new javax.swing.JTextField();
        total_Lbl = new javax.swing.JLabel();
        DepositBonus = new javax.swing.JButton();
        jcb_autoPreviewReceipt = new javax.swing.JCheckBox();
        jSplitPane5 = new javax.swing.JSplitPane();
        wOption_pnl = new javax.swing.JPanel();
        ClearSA1 = new javax.swing.JButton();
        CheckSA1 = new javax.swing.JButton();
        btnWithdrawalSubmit = new javax.swing.JButton();
        withdrawal_pnl = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        wACCT = new javax.swing.JLabel();
        txtWithdrawalAccountNo = new javax.swing.JTextField();
        WHolder_Txt = new javax.swing.JTextField();
        wName_Lbl = new javax.swing.JLabel();
        wBalance_Lbl = new javax.swing.JLabel();
        DCurrentBalance_Txt = new javax.swing.JTextField();
        Dwithdraw_Txt = new javax.swing.JTextField();
        WDRL_Lbl = new javax.swing.JLabel();
        jcb_autoPreviewReceiptWithdrawal = new javax.swing.JCheckBox();
        mInterest_pnl = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        date = new javax.swing.JLabel();
        day_Lbl = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        interestINFO_tarea = new javax.swing.JTextArea();
        FetchAcc_Btn = new javax.swing.JButton();
        reports_pnl = new javax.swing.JTabbedPane();
        dailyReport_jsp = new javax.swing.JSplitPane();
        rOptions_jbl = new javax.swing.JPanel();
        btn_retrieveRecords = new javax.swing.JButton();
        btn_generateReport = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        dReport_pnl = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_autoStatus = new javax.swing.JLabel();
        jdp_selectDate = new org.jdesktop.swingx.JXDatePicker();
        jcb_autoPreviewReport = new javax.swing.JCheckBox();
        lbl_customerTransactionRecords = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtb_customerTransactionRecords = new javax.swing.JTable();
        lbl_automaticlDailyCT = new javax.swing.JLabel();
        lbl_manualDailyCT1 = new javax.swing.JLabel();
        lbl_selectDate1 = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        monthlyReport_jsp = new javax.swing.JSplitPane();
        rOptions_jbl1 = new javax.swing.JPanel();
        btn_monthlyRetrieveRecords = new javax.swing.JButton();
        btn_monthlyGenerateReport = new javax.swing.JButton();
        btn_monthlyClear = new javax.swing.JButton();
        dReport_pnl1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lbl_mautoStatus1 = new javax.swing.JLabel();
        jcb_monthlyAutoPreviewReport = new javax.swing.JCheckBox();
        lbl_mcustomerDetailsRecord = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtb_customerDetailsRecord = new javax.swing.JTable();
        lbl_mautomaticlDailyCT1 = new javax.swing.JLabel();
        lbl_mmanualDailyCT2 = new javax.swing.JLabel();
        lbl_selectMonth = new javax.swing.JLabel();
        lbl_mstatus = new javax.swing.JLabel();
        cmbMonthlyReportMonth = new java.awt.Choice();
        lbl_mtransactionDetailsRecords = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtb_transactionDetailsRecords = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        nCustomer_pnl = new javax.swing.JPanel();
        lblAccountType = new javax.swing.JLabel();
        FName_Lbl = new javax.swing.JLabel();
        lblAccountNumber = new javax.swing.JLabel();
        lblPhoneNumber1 = new javax.swing.JLabel();
        lblInitialDeposit = new javax.swing.JLabel();
        lblEmailAddress1 = new javax.swing.JLabel();
        txtMName = new javax.swing.JTextField();
        lblLName = new javax.swing.JLabel();
        txtLName = new javax.swing.JTextField();
        listAccountType = new javax.swing.JComboBox<>();
        txtEmailAddress1 = new javax.swing.JTextField();
        txtInitialDeposit = new javax.swing.JTextField();
        txtAccountNumber = new javax.swing.JTextField();
        txtPhoneNumber1 = new javax.swing.JTextField();
        btnGenerateAccountNumber = new javax.swing.JButton();
        btnCreateAccount = new javax.swing.JButton();
        lblPassportNumber = new javax.swing.JLabel();
        txtPassportNumber = new javax.swing.JTextField();
        lblMName = new javax.swing.JLabel();
        lblFName = new javax.swing.JLabel();
        FName_Lbl3 = new javax.swing.JLabel();
        lblEmailAddress2 = new javax.swing.JLabel();
        txtEmailAddress2 = new javax.swing.JTextField();
        lblPhoneNumber2 = new javax.swing.JLabel();
        txtPhoneNumber2 = new javax.swing.JTextField();
        lblImportantAsterisk1 = new javax.swing.JLabel();
        lblImportantAsterisk2 = new javax.swing.JLabel();
        lblImportantAsterisk3 = new javax.swing.JLabel();
        lblImportantAsterisk4 = new javax.swing.JLabel();
        lblImportantAsterisk5 = new javax.swing.JLabel();
        lblImportantAsterisk6 = new javax.swing.JLabel();
        lblImportantAsterisk7 = new javax.swing.JLabel();
        lblImportantAsterisk8 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        lblLaneAddress = new javax.swing.JLabel();
        lblCity = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        lblImportantAsterisk9 = new javax.swing.JLabel();
        txtLaneAddress = new javax.swing.JTextField();
        lblImportantAsterisk11 = new javax.swing.JLabel();
        lblAccountStatus = new javax.swing.JLabel();
        listAccountStatus = new javax.swing.JComboBox<>();
        lblImportantAsterisk10 = new javax.swing.JLabel();
        lblFNameValidator = new javax.swing.JLabel();
        lblMNameValidator = new javax.swing.JLabel();
        lblLNameValidator = new javax.swing.JLabel();
        lblPassportNumberValidator = new javax.swing.JLabel();
        lblEmailAddress1Validator = new javax.swing.JLabel();
        lblEmailAddress2Validator = new javax.swing.JLabel();
        lblPhoneNumber1Validator = new javax.swing.JLabel();
        lblPhoneNumber2Validator = new javax.swing.JLabel();
        lblLaneAddressValidator = new javax.swing.JLabel();
        lblCityValidator = new javax.swing.JLabel();
        lblInitialDepositValidator = new javax.swing.JLabel();
        txtFName = new javax.swing.JTextField();
        txtMonthlyIncomeRate = new javax.swing.JTextField();
        lblMonthlyInvoiceRate = new javax.swing.JLabel();
        lblImportantAsterisk12 = new javax.swing.JLabel();
        lblMonthlyIncomeRateValidator = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jlbl_localTime = new javax.swing.JLabel();
        jlbl_localDate = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu1.setText("Menu");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jMenu1.add(logout);

        jMenuBar1.add(jMenu1);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 800));

        jPanel1.setBackground(new java.awt.Color(18, 63, 72));

        NewUser.setBackground(new java.awt.Color(255, 204, 255));

        savingsAcc_pnl.setBackground(new java.awt.Color(255, 255, 204));

        dOption_pnl.setBackground(new java.awt.Color(0, 123, 146));

        ClearSA_Btn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearSA_Btn.setText("Clear");
        ClearSA_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ClearSA_BtnMouseEntered(evt);
            }
        });
        ClearSA_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearSA_BtnActionPerformed(evt);
            }
        });

        CheckSA_Btn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CheckSA_Btn.setText("Check");
        CheckSA_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CheckSA_BtnMouseEntered(evt);
            }
        });

        SubmitSA.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        SubmitSA.setText("Submit");
        SubmitSA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SubmitSAMouseEntered(evt);
            }
        });
        SubmitSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitSAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dOption_pnlLayout = new javax.swing.GroupLayout(dOption_pnl);
        dOption_pnl.setLayout(dOption_pnlLayout);
        dOption_pnlLayout.setHorizontalGroup(
            dOption_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClearSA_Btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(CheckSA_Btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(SubmitSA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dOption_pnlLayout.setVerticalGroup(
            dOption_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dOption_pnlLayout.createSequentialGroup()
                .addComponent(ClearSA_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(CheckSA_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
                .addComponent(SubmitSA, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(dOption_pnl);

        Deposit_pnl.setBackground(new java.awt.Color(0, 123, 146));

        jPanel4.setBackground(new java.awt.Color(18, 63, 72));

        dAccount.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        dAccount.setForeground(new java.awt.Color(255, 255, 255));
        dAccount.setText("Account No:");

        DAccno_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        DAccno_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAccno_TxtActionPerformed(evt);
            }
        });

        holderName_Txt.setEditable(false);
        holderName_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        holderName_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holderName_TxtActionPerformed(evt);
            }
        });

        dName_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        dName_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        dName_Lbl.setText("Holder Name:");

        dBalance_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        dBalance_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        dBalance_Lbl.setText("Current Balance: ");

        currentBalance_Txt.setEditable(false);
        currentBalance_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        currentBalance_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentBalance_TxtActionPerformed(evt);
            }
        });

        ACCType_Txt.setEditable(false);
        ACCType_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        ACCType_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ACCType_Txt.setText("BONUS SAVINGS");
        ACCType_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACCType_TxtActionPerformed(evt);
            }
        });

        dType_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        dType_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        dType_Lbl.setText("Account Type");

        dAmmount_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        dAmmount_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        dAmmount_Lbl.setText("Deposit Amount:");

        DEPAmount_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        DEPAmount_Txt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        DEPAmount_Txt.setText("0.00");
        DEPAmount_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEPAmount_TxtActionPerformed(evt);
            }
        });

        bonusINFO_tarea.setEditable(false);
        bonusINFO_tarea.setColumns(20);
        bonusINFO_tarea.setFont(new java.awt.Font("Courier New", 0, 16)); // NOI18N
        bonusINFO_tarea.setRows(5);
        bonusINFO_tarea.setText("QUINN BANK DAILY DEPOSIT BONUS - DECEMBER / APRIL (SESONAL))\n\nNormal Savings : 1.25%\nBonus Savings : 3%\nPremier Savings : 7%");
        jScrollPane1.setViewportView(bonusINFO_tarea);

        FinalDeposit_Txt.setEditable(false);
        FinalDeposit_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        FinalDeposit_Txt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        FinalDeposit_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalDeposit_TxtActionPerformed(evt);
            }
        });

        total_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        total_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        total_Lbl.setText("Final Total:");

        DepositBonus.setBackground(java.awt.SystemColor.activeCaption);
        DepositBonus.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        DepositBonus.setText("Bonus Calculate");
        DepositBonus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DepositBonusMouseEntered(evt);
            }
        });
        DepositBonus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepositBonusActionPerformed(evt);
            }
        });

        jcb_autoPreviewReceipt.setForeground(new java.awt.Color(255, 255, 255));
        jcb_autoPreviewReceipt.setText("Auto Preview Receipt");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dBalance_Lbl)
                            .addComponent(dAccount))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DAccno_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(currentBalance_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 289, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DepositBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FinalDeposit_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(71, 71, 71))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jcb_autoPreviewReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(total_Lbl)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dName_Lbl)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(dAmmount_Lbl))
                                .addComponent(dType_Lbl))
                            .addGap(4, 4, 4)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DEPAmount_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(holderName_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ACCType_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(612, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dAccount)
                    .addComponent(DAccno_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dBalance_Lbl)
                    .addComponent(currentBalance_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FinalDeposit_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DepositBonus)
                .addGap(17, 17, 17)
                .addComponent(jcb_autoPreviewReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dName_Lbl)
                        .addComponent(holderName_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(58, 58, 58)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dType_Lbl)
                        .addComponent(ACCType_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dAmmount_Lbl)
                        .addComponent(DEPAmount_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(139, 139, 139)
                    .addComponent(total_Lbl)
                    .addContainerGap(236, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout Deposit_pnlLayout = new javax.swing.GroupLayout(Deposit_pnl);
        Deposit_pnl.setLayout(Deposit_pnlLayout);
        Deposit_pnlLayout.setHorizontalGroup(
            Deposit_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Deposit_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Deposit_pnlLayout.setVerticalGroup(
            Deposit_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Deposit_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Deposit_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Deposit_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel5);

        savingsAcc_pnl.addTab("Deposits", jSplitPane1);

        wOption_pnl.setBackground(new java.awt.Color(0, 123, 146));

        ClearSA1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearSA1.setText("Clear");
        ClearSA1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ClearSA1MouseEntered(evt);
            }
        });

        CheckSA1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CheckSA1.setText("Check");
        CheckSA1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CheckSA1MouseEntered(evt);
            }
        });

        btnWithdrawalSubmit.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnWithdrawalSubmit.setText("Submit");
        btnWithdrawalSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnWithdrawalSubmitMouseEntered(evt);
            }
        });
        btnWithdrawalSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWithdrawalSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wOption_pnlLayout = new javax.swing.GroupLayout(wOption_pnl);
        wOption_pnl.setLayout(wOption_pnlLayout);
        wOption_pnlLayout.setHorizontalGroup(
            wOption_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClearSA1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(CheckSA1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnWithdrawalSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        wOption_pnlLayout.setVerticalGroup(
            wOption_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wOption_pnlLayout.createSequentialGroup()
                .addComponent(ClearSA1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(CheckSA1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
                .addComponent(btnWithdrawalSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane5.setLeftComponent(wOption_pnl);

        withdrawal_pnl.setBackground(new java.awt.Color(0, 123, 146));

        jPanel7.setBackground(new java.awt.Color(18, 63, 72));

        wACCT.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        wACCT.setForeground(new java.awt.Color(255, 255, 255));
        wACCT.setText("Account No:");

        txtWithdrawalAccountNo.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtWithdrawalAccountNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWithdrawalAccountNoActionPerformed(evt);
            }
        });

        WHolder_Txt.setEditable(false);
        WHolder_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        WHolder_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WHolder_TxtActionPerformed(evt);
            }
        });

        wName_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        wName_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        wName_Lbl.setText("Holder Name:");

        wBalance_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        wBalance_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        wBalance_Lbl.setText("Current Balance: ");

        DCurrentBalance_Txt.setEditable(false);
        DCurrentBalance_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        DCurrentBalance_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DCurrentBalance_TxtActionPerformed(evt);
            }
        });

        Dwithdraw_Txt.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        Dwithdraw_Txt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        Dwithdraw_Txt.setText("0.00");
        Dwithdraw_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dwithdraw_TxtActionPerformed(evt);
            }
        });

        WDRL_Lbl.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        WDRL_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        WDRL_Lbl.setText("Withdraw Amount:");

        jcb_autoPreviewReceiptWithdrawal.setForeground(new java.awt.Color(255, 255, 255));
        jcb_autoPreviewReceiptWithdrawal.setText("Auto Preview Receipt");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wACCT)
                            .addComponent(wName_Lbl))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWithdrawalAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(WHolder_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(wBalance_Lbl)
                        .addGap(27, 27, 27)
                        .addComponent(DCurrentBalance_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(WDRL_Lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Dwithdraw_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(517, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcb_autoPreviewReceiptWithdrawal, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wACCT)
                    .addComponent(txtWithdrawalAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wName_Lbl)
                    .addComponent(WHolder_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wBalance_Lbl)
                    .addComponent(DCurrentBalance_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WDRL_Lbl)
                    .addComponent(Dwithdraw_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
                .addComponent(jcb_autoPreviewReceiptWithdrawal, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout withdrawal_pnlLayout = new javax.swing.GroupLayout(withdrawal_pnl);
        withdrawal_pnl.setLayout(withdrawal_pnlLayout);
        withdrawal_pnlLayout.setHorizontalGroup(
            withdrawal_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawal_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        withdrawal_pnlLayout.setVerticalGroup(
            withdrawal_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(withdrawal_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane5.setRightComponent(withdrawal_pnl);

        savingsAcc_pnl.addTab("Withdrawals", jSplitPane5);

        NewUser.addTab("Savings Account", savingsAcc_pnl);

        mInterest_pnl.setBackground(new java.awt.Color(255, 204, 255));

        jPanel6.setBackground(new java.awt.Color(0, 123, 146));

        jPanel3.setBackground(new java.awt.Color(18, 63, 72));

        date.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                datePropertyChange(evt);
            }
        });

        day_Lbl.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        day_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        day_Lbl.setText("Today:");

        interestINFO_tarea.setEditable(false);
        interestINFO_tarea.setColumns(20);
        interestINFO_tarea.setFont(new java.awt.Font("MS Gothic", 1, 18)); // NOI18N
        interestINFO_tarea.setRows(5);
        interestINFO_tarea.setText("BUILDING ECONOMY TOGETHER, EMPOWERING LIVES - QUINN BANK (MANUAL INTEREST PROCESSING SYSTEM)\n\nInterest Rates: \tNormal Savings Account  - 1.25%\n\t\t\tBonus Savings Account   - 3.25%\n\t\t\tPremier Savings Account - 12.97%\n\nDate: 22th every month (Double Interst on December Season)\n\n*Finance department verifies all interests and finals them by 28th.");
        jScrollPane2.setViewportView(interestINFO_tarea);

        FetchAcc_Btn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        FetchAcc_Btn.setText("FETCH ALL ACCOUNTS");
        FetchAcc_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FetchAcc_BtnMouseEntered(evt);
            }
        });
        FetchAcc_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FetchAcc_BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(FetchAcc_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(510, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(day_Lbl)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(847, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(FetchAcc_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(day_Lbl)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(575, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mInterest_pnl.addTab("HOME", jPanel6);

        NewUser.addTab("Monthly Interests", mInterest_pnl);

        reports_pnl.setBackground(new java.awt.Color(204, 204, 255));

        rOptions_jbl.setBackground(new java.awt.Color(0, 123, 146));

        btn_retrieveRecords.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_retrieveRecords.setText("<html>\n<p>Retrieve <br> Records</p>\n</html>");
        btn_retrieveRecords.setActionCommand("Retrieve \nRecords");
        btn_retrieveRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retrieveRecordsActionPerformed(evt);
            }
        });

        btn_generateReport.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_generateReport.setText("<html>\n<p> Generate Report</p>\n</html>");
        btn_generateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateReportActionPerformed(evt);
            }
        });

        btn_clear.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_clearMouseEntered(evt);
            }
        });
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rOptions_jblLayout = new javax.swing.GroupLayout(rOptions_jbl);
        rOptions_jbl.setLayout(rOptions_jblLayout);
        rOptions_jblLayout.setHorizontalGroup(
            rOptions_jblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rOptions_jblLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btn_retrieveRecords)
            .addComponent(btn_generateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        rOptions_jblLayout.setVerticalGroup(
            rOptions_jblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rOptions_jblLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(btn_retrieveRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(btn_generateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 302, Short.MAX_VALUE)
                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dailyReport_jsp.setLeftComponent(rOptions_jbl);

        dReport_pnl.setBackground(new java.awt.Color(0, 123, 146));

        jPanel2.setBackground(new java.awt.Color(18, 63, 72));
        jPanel2.setToolTipText("");
        jPanel2.setMaximumSize(new java.awt.Dimension(3270, 3210));
        jPanel2.setPreferredSize(new java.awt.Dimension(1100, 535));

        lbl_autoStatus.setForeground(new java.awt.Color(255, 255, 255));
        lbl_autoStatus.setText("Automatic Report Generation Status: ");

        jcb_autoPreviewReport.setForeground(new java.awt.Color(255, 255, 255));
        jcb_autoPreviewReport.setText("Auto Preview Report");

        lbl_customerTransactionRecords.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_customerTransactionRecords.setForeground(new java.awt.Color(255, 255, 255));
        lbl_customerTransactionRecords.setText("Customer Transaction Records:");

        jtb_customerTransactionRecords.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jtb_customerTransactionRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jtb_customerTransactionRecords);

        lbl_automaticlDailyCT.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbl_automaticlDailyCT.setForeground(new java.awt.Color(255, 255, 255));
        lbl_automaticlDailyCT.setText("Automatic Daily Customer Transaction Report");

        lbl_manualDailyCT1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbl_manualDailyCT1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_manualDailyCT1.setText("Manual Daily Customer Transaction Report");

        lbl_selectDate1.setFont(new java.awt.Font("Yu Gothic UI", 0, 11)); // NOI18N
        lbl_selectDate1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_selectDate1.setText("Select a Date:");

        lbl_status.setBackground(new java.awt.Color(255, 255, 255));
        lbl_status.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_status.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_customerTransactionRecords)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(116, 116, 116)
                                        .addComponent(jdp_selectDate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(154, 154, 154)
                                .addComponent(jcb_autoPreviewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lbl_automaticlDailyCT))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lbl_autoStatus)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(lbl_manualDailyCT1)
                    .addContainerGap(549, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addComponent(lbl_selectDate1)
                    .addContainerGap(861, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jdp_selectDate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_autoPreviewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_customerTransactionRecords)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lbl_automaticlDailyCT)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbl_autoStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(lbl_manualDailyCT1)
                    .addContainerGap(560, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(lbl_selectDate1)
                    .addContainerGap(519, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout dReport_pnlLayout = new javax.swing.GroupLayout(dReport_pnl);
        dReport_pnl.setLayout(dReport_pnlLayout);
        dReport_pnlLayout.setHorizontalGroup(
            dReport_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dReport_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                .addContainerGap())
        );
        dReport_pnlLayout.setVerticalGroup(
            dReport_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dReport_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );

        dailyReport_jsp.setRightComponent(dReport_pnl);

        reports_pnl.addTab("Daily Report", dailyReport_jsp);

        rOptions_jbl1.setBackground(new java.awt.Color(0, 123, 146));

        btn_monthlyRetrieveRecords.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_monthlyRetrieveRecords.setText("<html> <p>Retrieve <br> Records</p> </html>");
        btn_monthlyRetrieveRecords.setActionCommand("Retrieve \nRecords");
        btn_monthlyRetrieveRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_monthlyRetrieveRecordsActionPerformed(evt);
            }
        });

        btn_monthlyGenerateReport.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_monthlyGenerateReport.setText("<html>\n<p> Generate Report</p>\n</html>");
        btn_monthlyGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_monthlyGenerateReportActionPerformed(evt);
            }
        });

        btn_monthlyClear.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_monthlyClear.setText("Clear");
        btn_monthlyClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_monthlyClearMouseEntered(evt);
            }
        });
        btn_monthlyClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_monthlyClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rOptions_jbl1Layout = new javax.swing.GroupLayout(rOptions_jbl1);
        rOptions_jbl1.setLayout(rOptions_jbl1Layout);
        rOptions_jbl1Layout.setHorizontalGroup(
            rOptions_jbl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rOptions_jbl1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_monthlyClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btn_monthlyRetrieveRecords)
            .addComponent(btn_monthlyGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        rOptions_jbl1Layout.setVerticalGroup(
            rOptions_jbl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rOptions_jbl1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(btn_monthlyRetrieveRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(btn_monthlyGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 302, Short.MAX_VALUE)
                .addComponent(btn_monthlyClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        monthlyReport_jsp.setLeftComponent(rOptions_jbl1);

        dReport_pnl1.setBackground(new java.awt.Color(0, 123, 146));

        jPanel8.setBackground(new java.awt.Color(18, 63, 72));
        jPanel8.setToolTipText("");
        jPanel8.setMaximumSize(new java.awt.Dimension(3270, 3210));
        jPanel8.setPreferredSize(new java.awt.Dimension(1100, 535));

        lbl_mautoStatus1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mautoStatus1.setText("Automatic Report Generation Status: ");

        jcb_monthlyAutoPreviewReport.setForeground(new java.awt.Color(255, 255, 255));
        jcb_monthlyAutoPreviewReport.setText("Auto Preview Report");

        lbl_mcustomerDetailsRecord.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_mcustomerDetailsRecord.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mcustomerDetailsRecord.setText("Customer Details:");

        jtb_customerDetailsRecord.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jtb_customerDetailsRecord.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jtb_customerDetailsRecord);

        lbl_mautomaticlDailyCT1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbl_mautomaticlDailyCT1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mautomaticlDailyCT1.setText("Automatic Monthly Customer Transaction Report");

        lbl_mmanualDailyCT2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbl_mmanualDailyCT2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mmanualDailyCT2.setText("Manual Monthly Customer Transaction Report");

        lbl_selectMonth.setFont(new java.awt.Font("Yu Gothic UI", 0, 11)); // NOI18N
        lbl_selectMonth.setForeground(new java.awt.Color(255, 255, 255));
        lbl_selectMonth.setText("Select a Month:");

        lbl_mstatus.setBackground(new java.awt.Color(255, 255, 255));
        lbl_mstatus.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_mstatus.setForeground(new java.awt.Color(255, 255, 255));

        cmbMonthlyReportMonth.setFont(new java.awt.Font("Lucida Sans", 1, 16)); // NOI18N

        lbl_mtransactionDetailsRecords.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_mtransactionDetailsRecords.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mtransactionDetailsRecords.setText("Transaction Details:");

        jtb_transactionDetailsRecords.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jtb_transactionDetailsRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jtb_transactionDetailsRecords);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 841, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(205, 205, 205)
                            .addComponent(cmbMonthlyReportMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(193, 193, 193)
                            .addComponent(jcb_monthlyAutoPreviewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addComponent(lbl_mautomaticlDailyCT1))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(lbl_mautoStatus1)
                            .addGap(18, 18, 18)
                            .addComponent(lbl_mstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(76, 76, 76)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 841, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_mtransactionDetailsRecords)
                                .addComponent(lbl_mcustomerDetailsRecord)))))
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(lbl_mmanualDailyCT2)
                    .addContainerGap(517, Short.MAX_VALUE)))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addComponent(lbl_selectMonth)
                    .addContainerGap(852, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcb_monthlyAutoPreviewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMonthlyReportMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(lbl_mcustomerDetailsRecord)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_mtransactionDetailsRecords)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_mautomaticlDailyCT1)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbl_mautoStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lbl_mstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(lbl_mmanualDailyCT2)
                    .addContainerGap(560, Short.MAX_VALUE)))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(lbl_selectMonth)
                    .addContainerGap(519, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout dReport_pnl1Layout = new javax.swing.GroupLayout(dReport_pnl1);
        dReport_pnl1.setLayout(dReport_pnl1Layout);
        dReport_pnl1Layout.setHorizontalGroup(
            dReport_pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dReport_pnl1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        dReport_pnl1Layout.setVerticalGroup(
            dReport_pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dReport_pnl1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );

        monthlyReport_jsp.setRightComponent(dReport_pnl1);

        reports_pnl.addTab("Monthly Report", monthlyReport_jsp);

        NewUser.addTab("Customer Transaction Reports", reports_pnl);

        jPanel16.setBackground(new java.awt.Color(0, 123, 146));
        jPanel16.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel16ComponentShown(evt);
            }
        });

        nCustomer_pnl.setBackground(new java.awt.Color(18, 63, 72));

        lblAccountType.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblAccountType.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountType.setText("Account Type");

        FName_Lbl.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        FName_Lbl.setForeground(new java.awt.Color(255, 255, 255));
        FName_Lbl.setText("Account Details ");

        lblAccountNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblAccountNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountNumber.setText("Account Number");

        lblPhoneNumber1.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblPhoneNumber1.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneNumber1.setText("Phone Number 1     +44");

        lblInitialDeposit.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblInitialDeposit.setForeground(new java.awt.Color(255, 255, 255));
        lblInitialDeposit.setText("Initial Deposit");

        lblEmailAddress1.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblEmailAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblEmailAddress1.setText("Email Address 1");

        txtMName.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtMName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMNameKeyReleased(evt);
            }
        });

        lblLName.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblLName.setForeground(new java.awt.Color(255, 255, 255));
        lblLName.setText("Last Name");

        txtLName.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtLName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLNameKeyReleased(evt);
            }
        });

        listAccountType.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        listAccountType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECT ACCOUNT TYPE--", "NORMAL SAVINGS", "BONUS SAVINGS", "PREMIER SAVINGS" }));
        listAccountType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAccountTypeActionPerformed(evt);
            }
        });

        txtEmailAddress1.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtEmailAddress1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailAddress1KeyReleased(evt);
            }
        });

        txtInitialDeposit.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtInitialDeposit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtInitialDeposit.setText("0.00");
        txtInitialDeposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInitialDepositKeyReleased(evt);
            }
        });

        txtAccountNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N

        txtPhoneNumber1.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtPhoneNumber1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneNumber1KeyReleased(evt);
            }
        });

        btnGenerateAccountNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        btnGenerateAccountNumber.setText("Generate");
        btnGenerateAccountNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateAccountNumberActionPerformed(evt);
            }
        });

        btnCreateAccount.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        btnCreateAccount.setText("Create Account");
        btnCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountActionPerformed(evt);
            }
        });

        lblPassportNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblPassportNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblPassportNumber.setText("Passport Number");

        txtPassportNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtPassportNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPassportNumberKeyReleased(evt);
            }
        });

        lblMName.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblMName.setForeground(new java.awt.Color(255, 255, 255));
        lblMName.setText("Middle Name");

        lblFName.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblFName.setForeground(new java.awt.Color(255, 255, 255));
        lblFName.setText("First Name");

        FName_Lbl3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        FName_Lbl3.setForeground(new java.awt.Color(255, 255, 255));
        FName_Lbl3.setText("Customer Details ");

        lblEmailAddress2.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblEmailAddress2.setForeground(new java.awt.Color(255, 255, 255));
        lblEmailAddress2.setText("Email Address 2");

        txtEmailAddress2.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtEmailAddress2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailAddress2KeyReleased(evt);
            }
        });

        lblPhoneNumber2.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblPhoneNumber2.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneNumber2.setText("Phone Number 2     +44");

        txtPhoneNumber2.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtPhoneNumber2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneNumber2KeyReleased(evt);
            }
        });

        lblImportantAsterisk1.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk1.setText("*");

        lblImportantAsterisk2.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk2.setText("*");

        lblImportantAsterisk3.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk3.setText("*");

        lblImportantAsterisk4.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk4.setText("*");

        lblImportantAsterisk5.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk5.setText("*");

        lblImportantAsterisk6.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk6.setText("*");

        lblImportantAsterisk7.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk7.setText("*");

        lblImportantAsterisk8.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk8.setText("*");

        btnReset.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        btnReset.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblLaneAddress.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblLaneAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblLaneAddress.setText("Lane Address");

        lblCity.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblCity.setForeground(new java.awt.Color(255, 255, 255));
        lblCity.setText("City");

        txtCity.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCityKeyReleased(evt);
            }
        });

        lblImportantAsterisk9.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk9.setText("*");

        txtLaneAddress.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtLaneAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLaneAddressKeyReleased(evt);
            }
        });

        lblImportantAsterisk11.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk11.setText("*");

        lblAccountStatus.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblAccountStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountStatus.setText("Account Status");

        listAccountStatus.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        listAccountStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECT ACCOUNT STATUS--", "ACTIVE", "ONHOLD", "DISABLED" }));
        listAccountStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAccountStatusActionPerformed(evt);
            }
        });

        lblImportantAsterisk10.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk10.setText("*");

        lblFNameValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblFNameValidator.setForeground(new java.awt.Color(255, 255, 255));

        lblMNameValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblMNameValidator.setForeground(new java.awt.Color(255, 255, 255));

        lblLNameValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblLNameValidator.setForeground(new java.awt.Color(255, 255, 255));

        lblPassportNumberValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblPassportNumberValidator.setForeground(new java.awt.Color(255, 255, 255));

        lblEmailAddress1Validator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblEmailAddress1Validator.setForeground(new java.awt.Color(255, 255, 255));

        lblEmailAddress2Validator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblEmailAddress2Validator.setForeground(new java.awt.Color(255, 255, 255));

        lblPhoneNumber1Validator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblPhoneNumber1Validator.setForeground(new java.awt.Color(255, 255, 255));

        lblPhoneNumber2Validator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblPhoneNumber2Validator.setForeground(new java.awt.Color(255, 255, 255));

        lblLaneAddressValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblLaneAddressValidator.setForeground(new java.awt.Color(255, 255, 255));

        lblCityValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblCityValidator.setForeground(new java.awt.Color(255, 255, 255));

        lblInitialDepositValidator.setForeground(new java.awt.Color(255, 255, 255));

        txtFName.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtFName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFNameKeyReleased(evt);
            }
        });

        txtMonthlyIncomeRate.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        txtMonthlyIncomeRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMonthlyIncomeRateKeyReleased(evt);
            }
        });

        lblMonthlyInvoiceRate.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        lblMonthlyInvoiceRate.setForeground(new java.awt.Color(255, 255, 255));
        lblMonthlyInvoiceRate.setText("Monthly Income Rate");

        lblImportantAsterisk12.setForeground(new java.awt.Color(255, 0, 51));
        lblImportantAsterisk12.setText("*");

        lblMonthlyIncomeRateValidator.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblMonthlyIncomeRateValidator.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout nCustomer_pnlLayout = new javax.swing.GroupLayout(nCustomer_pnl);
        nCustomer_pnl.setLayout(nCustomer_pnlLayout);
        nCustomer_pnlLayout.setHorizontalGroup(
            nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(FName_Lbl3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addComponent(lblPhoneNumber2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPhoneNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(lblPhoneNumber2Validator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(6, 6, 6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createSequentialGroup()
                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createSequentialGroup()
                                                .addComponent(lblLaneAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtLaneAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblImportantAsterisk11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                .addComponent(lblCity, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(76, 76, 76)
                                                .addComponent(txtCity)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblImportantAsterisk9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(8, 8, 8)))
                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblCityValidator, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                            .addComponent(lblLaneAddressValidator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(32, 32, 32))
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addComponent(lblMonthlyInvoiceRate)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMonthlyIncomeRate, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblImportantAsterisk12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblMonthlyIncomeRateValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addComponent(lblMName, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtFName, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMName, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addComponent(lblLName)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtLName, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                .addComponent(lblImportantAsterisk2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblLNameValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lblImportantAsterisk1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblMNameValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lblFNameValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFName, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(lblEmailAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(txtEmailAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblEmailAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblPassportNumber))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                        .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblImportantAsterisk3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblPassportNumberValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                        .addComponent(txtEmailAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblImportantAsterisk4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(lblEmailAddress1Validator, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                                            .addComponent(lblEmailAddress2Validator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                                .addComponent(lblPhoneNumber1)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtPhoneNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblImportantAsterisk5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPhoneNumber1Validator, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                        .addComponent(lblAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(listAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createSequentialGroup()
                                        .addComponent(lblInitialDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblInitialDepositValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtInitialDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblImportantAsterisk7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblImportantAsterisk6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addComponent(lblAccountNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(txtAccountNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGenerateAccountNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblImportantAsterisk8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(btnReset)
                                .addGap(41, 41, 41)
                                .addComponent(btnCreateAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(FName_Lbl)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addComponent(lblAccountStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(listAccountStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImportantAsterisk10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        nCustomer_pnlLayout.setVerticalGroup(
            nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblImportantAsterisk6)
                            .addComponent(txtInitialDeposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInitialDeposit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInitialDepositValidator))
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FName_Lbl3)
                            .addComponent(FName_Lbl))
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(listAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblAccountType)
                                    .addComponent(lblImportantAsterisk7)))
                            .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, nCustomer_pnlLayout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblFName)))
                                .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblImportantAsterisk1)
                                        .addComponent(lblFNameValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMNameValidator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMName)))
                        .addGap(17, 17, 17)))
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAccountNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAccountNumber)
                            .addComponent(btnGenerateAccountNumber)
                            .addComponent(lblImportantAsterisk8)))
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblLName)
                            .addComponent(txtLName)
                            .addComponent(lblImportantAsterisk2)
                            .addComponent(lblLNameValidator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, nCustomer_pnlLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPassportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPassportNumber)))
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblImportantAsterisk3)
                                    .addComponent(lblPassportNumberValidator, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEmailAddress1)
                                    .addComponent(txtEmailAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmailAddress1Validator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblImportantAsterisk4)))))
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImportantAsterisk10)
                            .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(listAccountStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAccountStatus)))))
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmailAddress2)
                            .addComponent(txtEmailAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblEmailAddress2Validator, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblPhoneNumber1Validator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblPhoneNumber1)
                                    .addComponent(txtPhoneNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblImportantAsterisk5))
                        .addGap(18, 18, 18)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPhoneNumber2Validator, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPhoneNumber2)
                                .addComponent(txtPhoneNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLaneAddress)
                            .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblLaneAddressValidator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtLaneAddress)
                                .addComponent(lblImportantAsterisk11, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(18, 18, 18)
                        .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nCustomer_pnlLayout.createSequentialGroup()
                                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCity))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
                            .addComponent(lblCityValidator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCreateAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReset)
                        .addComponent(lblImportantAsterisk9)))
                .addGap(18, 18, 18)
                .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImportantAsterisk12)
                    .addGroup(nCustomer_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMonthlyInvoiceRate)
                        .addComponent(txtMonthlyIncomeRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMonthlyIncomeRateValidator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nCustomer_pnl, javax.swing.GroupLayout.PREFERRED_SIZE, 1134, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nCustomer_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        NewUser.addTab("NEW Customer", jPanel16);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/QUINN_LOGO.gif"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Bodoni MT", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("QUINN BANK");

        jLabel4.setFont(new java.awt.Font("Script MT Bold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Guarding Day & Night");

        jlbl_localTime.setFont(new java.awt.Font("Leelawadee UI", 1, 16)); // NOI18N
        jlbl_localTime.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_localTime.setText("Time");

        jlbl_localDate.setFont(new java.awt.Font("Leelawadee UI", 1, 16)); // NOI18N
        jlbl_localDate.setForeground(new java.awt.Color(255, 255, 255));
        jlbl_localDate.setText("Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NewUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1159, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addGap(250, 250, 250)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbl_localDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlbl_localTime, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbl_localTime, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbl_localDate)))
                .addGap(18, 18, 18)
                .addComponent(NewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jMenuBar2.setBackground(new java.awt.Color(18, 63, 72));
        jMenuBar2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jMenuBar2MouseDragged(evt);
            }
        });
        jMenuBar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuBar2MousePressed(evt);
            }
        });

        jMenu2.setText("File");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenu2MouseEntered(evt);
            }
        });

        jMenuItem1.setText("Manager Access");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Logout");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar2.add(jMenu2);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void WHolder_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WHolder_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_WHolder_TxtActionPerformed

    private void DCurrentBalance_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DCurrentBalance_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DCurrentBalance_TxtActionPerformed

    private void txtWithdrawalAccountNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWithdrawalAccountNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWithdrawalAccountNoActionPerformed

    private void Dwithdraw_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dwithdraw_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dwithdraw_TxtActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed

    }//GEN-LAST:event_logoutActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        //Closing Form and Reopening the Login Screeen
        Access a1 = new Access();
        a1.setVisible(true);
        //closes the login form prevent unnecessary tab creation
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void datePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_datePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_datePropertyChange

    private void FetchAcc_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FetchAcc_BtnActionPerformed
        
        // Passing the tellerID within the parameterized constructor while creating the new object
        TellerFrameMonthlyInterests frameMonthlyInterest = new TellerFrameMonthlyInterests(tellerIDDB); 
        frameMonthlyInterest.setVisible(true);
        // Closes the 'teller' frame
        this.setVisible(false);
        
        
        
        
        
    }//GEN-LAST:event_FetchAcc_BtnActionPerformed

    private void ClearSA_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearSA_BtnActionPerformed
        // TODO add your handling code here:
        DAccno_Txt.setText("");
        holderName_Txt.setText("");
        currentBalance_Txt.setText("");
        ACCType_Txt.setText("");
        DEPAmount_Txt.setText("0.00");
        FinalDeposit_Txt.setText("");
    }//GEN-LAST:event_ClearSA_BtnActionPerformed

    private void jMenu2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_jMenu2MouseEntered

    private void ClearSA_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearSA_BtnMouseEntered
        // TODO add your handling code here:
        ClearSA_Btn.setToolTipText("Clear All Fields.");
    }//GEN-LAST:event_ClearSA_BtnMouseEntered

    private void CheckSA_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckSA_BtnMouseEntered
        // TODO add your handling code here:
        CheckSA_Btn.setToolTipText("Query Bank Systems for Records.");
    }//GEN-LAST:event_CheckSA_BtnMouseEntered

    private void SubmitSAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitSAMouseEntered
        // TODO add your handling code here:
        SubmitSA.setToolTipText("Authorise the Deposit Transaction Request to Bank Systems");
    }//GEN-LAST:event_SubmitSAMouseEntered

    private void jMenuItem2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseEntered
        // TODO add your handling code here:
        jMenuItem2.setToolTipText("Click to Logout the System");
    }//GEN-LAST:event_jMenuItem2MouseEntered

    private void ClearSA1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearSA1MouseEntered
        // TODO add your handling code here:
        ClearSA1.setToolTipText("Clear All Fields.");
    }//GEN-LAST:event_ClearSA1MouseEntered

    private void CheckSA1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckSA1MouseEntered
        // TODO add your handling code here:
        CheckSA1.setToolTipText("Query Bank Systems for Records.");
    }//GEN-LAST:event_CheckSA1MouseEntered

    private void FetchAcc_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FetchAcc_BtnMouseEntered
        // TODO add your handling code here:
        FetchAcc_Btn.setToolTipText("Prepare All customer accounts for Interest Addition Process.");
    }//GEN-LAST:event_FetchAcc_BtnMouseEntered

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed

        // Sets date picker to null value (resets)
        jdp_selectDate.setDate(null);

        // Resets auto preview report check box
        jcb_autoPreviewReport.setSelected(false);

        // Removing all the exiting records in the customer transaction table
        int ctrCountRemove = customerTransactionRecordsTableModel.getRowCount();
        for (int i = ctrCountRemove - 1; i >= 0; i--) {
            customerTransactionRecordsTableModel.removeRow(i);
        }

    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_clearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_clearMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_clearMouseEntered

    private void btn_generateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generateReportActionPerformed

        // Daily customer transaction report generation source code
        try {
            // Retrieving user selected date
            Date retrievingSelectedDate = jdp_selectDate.getDate();
            DateFormat retrievingSelectedDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String selectedDate = retrievingSelectedDateFormat.format(retrievingSelectedDate);
            System.out.println("Selected Date: " + selectedDate);

            // Retrieving user selected date
            Date retrievingSelectedDateSql = jdp_selectDate.getDate();
            DateFormat retrievingSelectedDateSqlFormat = new SimpleDateFormat("yyyy-MM-dd");
            String selectedDateSql = retrievingSelectedDateSqlFormat.format(retrievingSelectedDateSql);
            System.out.println("Selected Date: " + selectedDateSql);

            // Checking if any any records are available in the database from the selected date
            try (Connection verificationCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    Statement verificationStmt = verificationCon.createStatement();) {

                // Assigning SQL query
                String verificationSqlQuery = "SELECT account_number, transaction_type, account_type, amount, date_time FROM customer_transaction "
                        + "WHERE convert(nvarchar(50), date_time,126) LIKE '" + selectedDateSql + "%' ";

                // Executing SQL query
                ResultSet verificationResult = verificationStmt.executeQuery(verificationSqlQuery);

                // Checking if any results were returned
                if (verificationResult.next() == false) {
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                            "No records are available in the database for the selected date."
                            + "\nPlease select a different date",
                            "ERROR !",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("No Records found in Database for Selected Date");
                } else {
                    // Retrieving records from database and inserting into table
                    try {
                        // Removing all the exiting records in the customer transaction table and repopulating with new records
                        int ctrCountRemove = customerTransactionRecordsTableModel.getRowCount();
                        for (int i = ctrCountRemove - 1; i >= 0; i--) {
                            customerTransactionRecordsTableModel.removeRow(i);
                        }

                        System.out.println("Selected Date: " + selectedDate);

                        // Retrieving records from the database for the selected date
                        try (Connection retrieveCTRCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement retrieveCTRstmt = retrieveCTRCon.createStatement();) {

                            // Executing SQL query
                            ResultSet retrieveCTRResult = retrieveCTRstmt.executeQuery(verificationSqlQuery);

                            // Creating new table rows and inserting record data into them
                            while (retrieveCTRResult.next()) {
                                customerTransactionRecordsTableModel.insertRow(customerTransactionRecordsTableModel.getRowCount(), new Object[]{retrieveCTRResult.getString(1),
                                    retrieveCTRResult.getString(2), retrieveCTRResult.getString(3), retrieveCTRResult.getString(4), retrieveCTRResult.getString(5)});
                            }
                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Error found: " + SqlEx);
                        }
                    } // Error handling. Checking for index out of range in array when removing the exisitng records from the table
                    catch (ArrayIndexOutOfBoundsException ctrRecordsRemovalEx) {
                        System.out.println("Error: " + ctrRecordsRemovalEx);
                    }

                    // Message box to verify if the user wants to generate report according to the data shown in the table
                    int userChoice = JOptionPane.showConfirmDialog(null, "Report will be generated according to data shown in the table."
                            + "\nDo you want to continue? ", "Verification", JOptionPane.YES_NO_OPTION);

                    // If uers select 'Yes' option
                    if (userChoice == 0) {
                        // checking if manual daily customer transaction report was already generated for this day
                        File checkFileExistence = new File("../Reports/Daily_Customer_Transaction_Record_Reports/Manual/Manual_Daily_Customer_Transaction_Records_'" + selectedDate + "'.pdf");

                        boolean checkFileExistenceResult = checkFileExistence.exists();

                        System.out.println(checkFileExistenceResult);

                        // If a report is already existing with the same filename
                        if (checkFileExistenceResult == true) {

                            System.out.println("Manual Daily Customer Transaction Report is already created for this day");

                            // Message box to verify if the user wants to generate report according to the data shown in the table
                            int checkFileExistenceUserChoice = JOptionPane.showConfirmDialog(null, "Manual Daily Customer Transaction Report was already generated for this day."
                                    + "\nDo you want to continue?  (This action will overwrite the exisitng report)", "WARNING!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                            // If the user selects 'Yes' option
                            if (checkFileExistenceUserChoice == 0) {
                                // Report generation if user selectes 'Yes'
                                // Calling method to generate manual daily report
                                // The implementation is placed in another class, becuase the same implementation will repeat twice. 
                                // Without repeating the same procedure twice we have implemented the procedure into a diffenent class and called it twice.
                                String reportGenerationStatus = rg.generateManualDailyCTReport(selectedDate, selectedDateSql);

                                System.out.println("Manual Daily Report Generation Status: " + reportGenerationStatus);

                                // Automatically opening the PDF document after generation in user's default PDF opening application
                                // Checks if auto preview report check box is selected
                                if (jcb_autoPreviewReport.isSelected()) {
                                    // Opening PDF document
                                    File report = new File("../Reports/Daily_Customer_Transaction_Record_Reports/Manual/Manual_Daily_Customer_Transaction_Records_'" + selectedDate + "'.pdf");
                                    try {
                                        Desktop.getDesktop().open(report);
                                    } // Error handling. Handles any data input and output errors.
                                    catch (IOException IoEx) {
                                        Logger.getLogger(Teller.class.getName()).log(Level.SEVERE, null, IoEx);
                                    }
                                }
                            }
                        } // If there is no report already exisitng
                        else {
                            // Report generation if a report with same filename doesn't exist
                            // Calling method to generate manual daily report
                            // The implementation is placed in another class, becuase the same implementation will repeat twice. 
                            // Without repeating the same procedure twice we have implemented the procedure into a diffenent class and called it twice.
                            String reportGenerationStatus = rg.generateManualDailyCTReport(selectedDate, selectedDateSql);

                            System.out.println("Manual Daily Report Generation Status: " + reportGenerationStatus);

                            // Automatically opening the PDF document after generation in user's default PDF opening application
                            // Checks if auto preview report check box is selected
                            if (jcb_autoPreviewReport.isSelected()) {
                                // Opening PDF document
                                File report = new File("../Reports/Daily_Customer_Transaction_Record_Reports/Manual/Manual_Daily_Customer_Transaction_Records_'" + selectedDate + "'.pdf");
                                try {
                                    Desktop.getDesktop().open(report);
                                } // Error handling. Handles any data input and output errors.
                                catch (IOException IoEx) {
                                    Logger.getLogger(Teller.class.getName()).log(Level.SEVERE, null, IoEx);
                                }
                            }
                        }
                    }
                }
            } // Error handling. Handles any SQL related errors.
            catch (SQLException SqlEx) {
                System.out.println("Error found: " + SqlEx);
            }
        } // Error handling. Checking if a date is selected before user clicks on 'Genearate' button
        catch (NullPointerException NullValueEx) {
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                    "Date not selected. Please select a date",
                    "ERROR !",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Date Not Selected. Error: " + NullValueEx);
        }
    }//GEN-LAST:event_btn_generateReportActionPerformed

    private void btn_retrieveRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retrieveRecordsActionPerformed

        // Retrieving daily customer transaction records from database source code
        try { // Checking if the user has selected a date from the datepicker
            try {
                // Removing all the exiting records in the customer transaction table and repopulating with new records
                int ctrCountRemove = customerTransactionRecordsTableModel.getRowCount();
                for (int i = ctrCountRemove - 1; i >= 0; i--) {
                    customerTransactionRecordsTableModel.removeRow(i);
                }

                // Assigning user selected date to a string variable
                Date userSelectedDate = jdp_selectDate.getDate();
                DateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String selectedDate = oDateFormat.format(userSelectedDate);

                System.out.println("Selected Date: " + selectedDate);

                //checking if there are any recrds in the database for the selected date
                try (Connection checkingCTRCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        Statement checkingCTRstmt = checkingCTRCon.createStatement();) {

                    // SQL query to retrieve relavent record data
                    String checkingCTRSqlQuery = "SELECT account_number, transaction_type, account_type, amount, date_time FROM customer_transaction WHERE "
                            + "convert(nvarchar(50), date_time,126) LIKE '" + selectedDate + "%' ";

                    // Executing SQL query
                    ResultSet checkingCTRResult = checkingCTRstmt.executeQuery(checkingCTRSqlQuery);

                    // Checking if any values were returned
                    if (checkingCTRResult.next() == false) {
                        // Displaying message box showing error message
                        JOptionPane.showMessageDialog(null,
                                "No records are available in the database for the selected date."
                                + "\nPlease select a different date",
                                "ERROR !",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("No Records found in Database for Selected Date");
                    } // If values were returned
                    else {
                        // Retriving record data from the database for the selected date and inserting into table
                        try (Connection retrieveCTRCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement retrieveCTRstmt = retrieveCTRCon.createStatement();) {

                            // Executing SQL query
                            ResultSet retrieveCTRResult = retrieveCTRstmt.executeQuery(checkingCTRSqlQuery);

                            // Creating new table rows and inserting record data into them
                            while (retrieveCTRResult.next()) {
                                customerTransactionRecordsTableModel.insertRow(customerTransactionRecordsTableModel.getRowCount(), new Object[]{retrieveCTRResult.getString(1),
                                    retrieveCTRResult.getString(2), retrieveCTRResult.getString(3), retrieveCTRResult.getString(4), retrieveCTRResult.getString(5)});
                            }

                            // Displaying message box showing confirmation message
                            JOptionPane.showMessageDialog(null,
                                    "Customer Tranasaction Records for Selected Date are shown in table below. "
                                    + "\n Please verify records and click on 'Generate Report' button to produce report",
                                    "Record Retrieval Successful",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Error found: " + SqlEx);
                        }
                    }
                } // Error handling. Handles any SQL related errors.
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                }
            } // Error handling. Checking for index out of range in array when removing the exisitng records from the table
            catch (ArrayIndexOutOfBoundsException ctrRecordsRemovalEx) {
                System.out.println("Error: " + ctrRecordsRemovalEx);
            }
        } // Error handling. Checking if a date is selected before user clicks on 'Retrieve Records' button
        catch (NullPointerException NullValueEx) {
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                    "Date not selected. Please select a date",
                    "ERROR !",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Date Not Selected. Error: " + NullValueEx);
        }
    }//GEN-LAST:event_btn_retrieveRecordsActionPerformed

    private void btn_monthlyRetrieveRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_monthlyRetrieveRecordsActionPerformed

        System.out.println("User Selected Month (Default Item Value): " + cmbMonthlyReportMonth.getSelectedItem());
        System.out.println("User Selected Month (Default Index Value): " + cmbMonthlyReportMonth.getSelectedIndex());

        // Record data retrieval for customer details table
        try {
            // Removing any exisitng records from customer details table
            int existingRowsCustomerDetailsTable = customerDetailsRecordTableModel.getRowCount();
            for (int i = existingRowsCustomerDetailsTable - 1; i >= 0; i--) {
                customerDetailsRecordTableModel.removeRow(i);
            }

            // Removing any exisitng records from transaction details table
            int existingRowsTransactionDetailsTable = transactionDetailsRecordsTableModel.getRowCount();
            for (int i = existingRowsTransactionDetailsTable - 1; i >= 0; i--) {
                transactionDetailsRecordsTableModel.removeRow(i);
            }

            if (cmbMonthlyReportMonth.getSelectedIndex() == 0) { //checks if the user has selected a month
                // Displaying message box showing error message
                JOptionPane.showMessageDialog(null,
                        "Month is not selected. Please select a month.",
                        "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("Month Not Selected.");
            } // If the user has selected a month
            else {
                String selectedMonthSqlCompatible = "";

                // Setting the user selected item string value to a date time comparison compatible string value in Microsoft SQL Server
                // Becuase the if retrving the index value, in one digts values the zero would be removed.
                // So inorder for it to be compatible with records in the database, a switch statement is used to set the relevant value to the user preferred month.
                switch (cmbMonthlyReportMonth.getSelectedItem()) {
                    case "January":
                        selectedMonthSqlCompatible = "01";
                        break;
                    case "February":
                        selectedMonthSqlCompatible = "02";
                        break;
                    case "March":
                        selectedMonthSqlCompatible = "03";
                        break;
                    case "April":
                        selectedMonthSqlCompatible = "04";
                        break;
                    case "May":
                        selectedMonthSqlCompatible = "05";
                        break;
                    case "June":
                        selectedMonthSqlCompatible = "06";
                        break;
                    case "July":
                        selectedMonthSqlCompatible = "07";
                        break;
                    case "August":
                        selectedMonthSqlCompatible = "08";
                        break;
                    case "September":
                        selectedMonthSqlCompatible = "09";
                        break;
                    case "October":
                        selectedMonthSqlCompatible = "10";
                        break;
                    case "November":
                        selectedMonthSqlCompatible = "11";
                        break;
                    case "December":
                        selectedMonthSqlCompatible = "12";
                        break;
                }

                System.out.println("Use Selected Month SQL Comparison Compatible: " + selectedMonthSqlCompatible);

                //checking if there are any transaction records available in the database for the selected month
                try (Connection verifyTransactionRecordsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        Statement verifyTransactionRecordsStmt = verifyTransactionRecordsCon.createStatement();) {

                    // Assinging SQL query
                    String verifyTransactionRecordsSqlQuery = "SELECT transaction_number, account_number, transaction_type, amount, date_time FROM "
                            + "customer_transaction WHERE convert(nvarchar(50), date_time,126) LIKE '_____" + selectedMonthSqlCompatible + "%' ";

                    // Executing SQL query
                    ResultSet verifyTransactionRecordsRs = verifyTransactionRecordsStmt.executeQuery(verifyTransactionRecordsSqlQuery);

                    System.out.println("Transaction Records Availability: " + verifyTransactionRecordsRs.next());

                    // Checking if a value is returned
                    // If no value are returned
                    if (verifyTransactionRecordsRs.next() == false) {
                        // Displaying message box showing error message
                        JOptionPane.showMessageDialog(null,
                                "No Transaction Records are Available for Selected Month. "
                                + "Please select a different month.",
                                "ERROR!",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("No Transaction Records Available");
                    } // If any values are returned
                    else {
                        // Retriveing customer record data from the database and assigning to customer details table cells
                        try (Connection retrieveCustomerRecordsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement retrieveCustomerRecordsStmt = retrieveCustomerRecordsCon.createStatement();) {

                            String retrieveCustomerRecordsSqlQuery = "SELECT DISTINCT c.account_number, c.first_name, c.middle_name, c.last_name, c.acc_type FROM customer c INNER JOIN"
                                    + " customer_transaction ct ON c.account_number = ct.account_number WHERE convert(nvarchar(50), ct.date_time,126) LIKE '_____" + selectedMonthSqlCompatible + "%' ";

                            // Executing SQL query, uses the same SQL query as verifyTransactionRecordsSqlQuery
                            ResultSet retrieveCustomerRecordsRs = retrieveCustomerRecordsStmt.executeQuery(retrieveCustomerRecordsSqlQuery);

                            // Assigning returned values to transaction details table cells
                            while (retrieveCustomerRecordsRs.next()) {
                                customerDetailsRecordTableModel.insertRow(customerDetailsRecordTableModel.getRowCount(), new Object[]{retrieveCustomerRecordsRs.getString(1),
                                    retrieveCustomerRecordsRs.getString(2), retrieveCustomerRecordsRs.getString(3), retrieveCustomerRecordsRs.getString(4), retrieveCustomerRecordsRs.getString(5)});
                            }

                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Error found: " + SqlEx);
                        }

                        // Retriveing transaction record data from the database and assigning to transaction details table cells
                        try (Connection retrieveTransactionRecordsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement retrieveTransactionRecordsStmt = retrieveTransactionRecordsCon.createStatement();) {

                            // Executing SQL query, uses the same SQL query as verifyTransactionRecordsSqlQuery
                            ResultSet retrieveTransactionRecordsRs = retrieveTransactionRecordsStmt.executeQuery(verifyTransactionRecordsSqlQuery);

                            // Assigning returned values to transaction details table cells
                            while (retrieveTransactionRecordsRs.next()) {
                                transactionDetailsRecordsTableModel.insertRow(transactionDetailsRecordsTableModel.getRowCount(), new Object[]{retrieveTransactionRecordsRs.getString(1),
                                    retrieveTransactionRecordsRs.getString(2), retrieveTransactionRecordsRs.getString(3), retrieveTransactionRecordsRs.getString(4),
                                    retrieveTransactionRecordsRs.getString(5)});
                            }
                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Error found: " + SqlEx);
                        }

                        // Displaying message box showing confirmation message
                        JOptionPane.showMessageDialog(null,
                                "Customer Details and Transaction Details for Selected Month is shown in the two tables below. "
                                + "\n Please verify records and click on 'Generate Report' button to produce report",
                                "Records Retrieval Successful",
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                } // Error handling. Handles any SQL related errors.
                catch (SQLException e) {
                    System.out.println("Error found: " + e);
                }
            }
        } catch (ArrayIndexOutOfBoundsException IndexOutofBoundEx) {
            System.out.println("Error: " + IndexOutofBoundEx);
        }

    }//GEN-LAST:event_btn_monthlyRetrieveRecordsActionPerformed

    private void btn_monthlyGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_monthlyGenerateReportActionPerformed

        // Manual monthly customer transaction report generation source code
        // Retrieving current month from local
        String monthlyLocalMonth = ltad.retrieveLocalMonth();

        System.out.println("Month: " + monthlyLocalMonth);

        // Retrieving current date from localhost
        String monthlyLocalDate = ltad.retrieveLocalDate();

        System.out.println("Date: " + monthlyLocalDate);

        String selectedMonthSqlCompatible = "";

        // Setting the user selected item string value to a date time comparison compatible string value in Microsoft SQL server
        // Becuase the if retrving the index value, in one digts values the zero would be removed.
        // So inorder for it to be compatible with records in the database, a switch statement is used to set the relevant value to the user preferred month.
        switch (cmbMonthlyReportMonth.getSelectedItem()) {
            case "January":
                selectedMonthSqlCompatible = "01";
                break;
            case "February":
                selectedMonthSqlCompatible = "02";
                break;
            case "March":
                selectedMonthSqlCompatible = "03";
                break;
            case "April":
                selectedMonthSqlCompatible = "04";
                break;
            case "May":
                selectedMonthSqlCompatible = "05";
                break;
            case "June":
                selectedMonthSqlCompatible = "06";
                break;
            case "July":
                selectedMonthSqlCompatible = "07";
                break;
            case "August":
                selectedMonthSqlCompatible = "08";
                break;
            case "September":
                selectedMonthSqlCompatible = "09";
                break;
            case "October":
                selectedMonthSqlCompatible = "10";
                break;
            case "November":
                selectedMonthSqlCompatible = "11";
                break;
            case "December":
                selectedMonthSqlCompatible = "12";
                break;
        }

        System.out.println("Use Selected Month SQL Comparison Compatible: " + selectedMonthSqlCompatible);

        // If the user doesn't select a month
        if (cmbMonthlyReportMonth.getSelectedIndex() == 0) {
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                    "Month is not selected. Please select a month.",
                    "ERROR!",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Month Not Selected.");
        } // If the user selects a month
        else {
            // Checking if any records are avialbe in the database for this month
            try (Connection checkingTransactionRecordCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                    Statement checkingTransactionRecordStmt = checkingTransactionRecordCon.createStatement();) {

                // Assigning SQL query
                String checkingTransactionRecordSqlQuery = "SELECT transaction_number, account_number, transaction_type, amount, date_time FROM "
                        + "customer_transaction WHERE convert(nvarchar(50), date_time,126) LIKE '_____" + selectedMonthSqlCompatible + "%' ";

                // Executing SQL query
                ResultSet checkingTransactionRecordRs = checkingTransactionRecordStmt.executeQuery(checkingTransactionRecordSqlQuery);

                System.out.println("Transactions Record Exist: " + checkingTransactionRecordRs);

                // If no values are returned
                if (checkingTransactionRecordRs.next() == false) {
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                            "No records are available in the database for the selected date."
                            + "\nPlease select a different month",
                            "ERROR !",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("No Records found in Database for Selected Month");
                } // If any values are returned
                else {

                    // Retrieving records from database and inserting into table
                    try {
                        // Removing all the exiting records in the customer details table and repopulating with new records
                        int cdCountRemove = customerDetailsRecordTableModel.getRowCount();

                        for (int i = cdCountRemove - 1; i >= 0; i--) {
                            customerDetailsRecordTableModel.removeRow(i);
                        }

                        // Retriving customer details from the database for the selected month
                        try (Connection retrieveCDCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement retrieveCDStmt = retrieveCDCon.createStatement();) {

                            String retrieveCDSqlQuery = "SELECT DISTINCT c.account_number, c.first_name, c.middle_name, c.last_name, c.acc_type FROM customer c INNER JOIN"
                                    + " customer_transaction ct ON c.account_number = ct.account_number WHERE convert(nvarchar(50), ct.date_time,126) LIKE '_____" + selectedMonthSqlCompatible + "%' ";

                            // Executing SQL query
                            ResultSet retrieveCDResult = retrieveCDStmt.executeQuery(retrieveCDSqlQuery);

                            // Creating new table rows and inserting record data into them
                            while (retrieveCDResult.next()) {
                                customerDetailsRecordTableModel.insertRow(customerDetailsRecordTableModel.getRowCount(), new Object[]{retrieveCDResult.getString(1),
                                    retrieveCDResult.getString(2), retrieveCDResult.getString(3), retrieveCDResult.getString(4), retrieveCDResult.getString(5)});
                            }
                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Error found: " + SqlEx);
                        }

                        // Removing all the exiting records in the transaction details table and repopulating with new records
                        int tdCountRemove = transactionDetailsRecordsTableModel.getRowCount();

                        for (int i = tdCountRemove - 1; i >= 0; i--) {
                            transactionDetailsRecordsTableModel.removeRow(i);
                        }

                        // Retriving transaction details from the database for the selected month
                        try (Connection retrieveTDCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                Statement retrieveTDStmt = retrieveTDCon.createStatement();) {

                            // Same SQL query as in checkingTransactionRecordSqlQuery
                            // Executing SQL query
                            ResultSet retrieveTDResult = retrieveTDStmt.executeQuery(checkingTransactionRecordSqlQuery);

                            // Creating new table rows and inserting record data into them
                            while (retrieveTDResult.next()) {
                                transactionDetailsRecordsTableModel.insertRow(transactionDetailsRecordsTableModel.getRowCount(), new Object[]{retrieveTDResult.getString(1),
                                    retrieveTDResult.getString(2), retrieveTDResult.getString(3), retrieveTDResult.getString(4), retrieveTDResult.getString(5)});
                            }
                        } // Error handling. Handles any SQL related errors.
                        catch (SQLException SqlEx) {
                            System.out.println("Error found: " + SqlEx);
                        }
                    } // Error handling. Checking for index out of range in array when removing the exisitng records from the table
                    catch (ArrayIndexOutOfBoundsException ctrRecordsRemovalEx) {
                        System.out.println("Error: " + ctrRecordsRemovalEx);
                    }

                    // Message box to verify if the user wants to generate report according to the data shown in the table
                    int userChoice = JOptionPane.showConfirmDialog(null, "Report will be generated according to data shown in the two tables."
                            + "\nDo you want to continue? ", "Verification", JOptionPane.YES_NO_OPTION);

                    // If the user selects 'Yes' option 
                    if (userChoice == 0) {
                        File checkFileExistence = new File("../Reports/Monthly_Customer_Transaction_Record_Reports/Manual/Manual_Monthly_Customer_Transaction_Records_'" + monthlyLocalDate + "'.pdf");

                        boolean checkFileExistenceResult = checkFileExistence.exists();

                        System.out.println("Automatic Monthly Report Existk: " + checkFileExistenceResult);

                        // If there is report already with the same filename
                        if (checkFileExistenceResult == true) {
                            // Message box to verify if the user wants to generate report according to the data shown in the table
                            int checkFileExistenceUserChoice = JOptionPane.showConfirmDialog(null, "Manual Monthly Customer Transaction Report was already generated for this Month."
                                    + "\nDo you want to continue?  (This action will overwrite the exisitng report)", "WARNING!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                            // If the user selects ' Yes' option
                            if (checkFileExistenceUserChoice == 0) {
                                // Report generation if user selectes 'Yes'
                                // Calling method to generate manual daily report
                                // The implementation is placed in another class, becuase the same implementation will repeat twice. 
                                // Without repeating the same procedure twice we have implemented the procedure into a diffenent class and called it twice.
                                String reportGenerationStatus = rg.generateManualMonthlyCTReport(selectedMonthSqlCompatible);

                                System.out.println("Manual Daily Report Generation Status: " + reportGenerationStatus);

                                // Automatically opening the PDF document after generation in user's default PDF opening application
                                // Checks if auto preview report check box is selected
                                if (jcb_monthlyAutoPreviewReport.isSelected()) {
                                    // Opening PDF document
                                    File myFile = new File("../Reports/Monthly_Customer_Transaction_Record_Reports/Manual/Manual_Monthly_Customer_Transaction_Records_'" + monthlyLocalDate + "'.pdf");
                                    try {
                                        Desktop.getDesktop().open(myFile);
                                    } // Error handling. Handles any data input and output errors.
                                    catch (IOException IoEx) {
                                        Logger.getLogger(Teller.class.getName()).log(Level.SEVERE, null, IoEx);
                                    }

                                    // Displays the directory pathway
                                    File directoryPathway = new File("./");
                                    System.out.println("Directory Absolute Pathway: " + directoryPathway.getAbsolutePath());

                                }
                            }
                        } // If there is no report existing
                        else {
                            // Report generation if a report with same filename doesn't exist
                            // Calling method to generate manual daily report
                            // The implementation is placed in another class, becuase the same implementation will repeat twice. 
                            // Without repeating the same procedure twice we have implemented the procedure into a diffenent class and called it twice.
                            String reportGenerationStatus = rg.generateManualMonthlyCTReport(selectedMonthSqlCompatible);

                            System.out.println("Manual Daily Report Generation Status: " + reportGenerationStatus);

                            // Automatically opening the PDF document after generation in user's default PDF opening application
                            // Checks if auto preview report check box is selected
                            if (jcb_monthlyAutoPreviewReport.isSelected()) {
                                // Opening PDF document
                                File myFile = new File("../Reports/Monthly_Customer_Transaction_Record_Reports/Manual/Manual_Monthly_Customer_Transaction_Records_'" + monthlyLocalDate + "'.pdf");
                                try {
                                    Desktop.getDesktop().open(myFile);
                                } // Error handling. Handles any data input and output errors.
                                catch (IOException IoEx) {
                                    Logger.getLogger(Teller.class.getName()).log(Level.SEVERE, null, IoEx);
                                }

                                // Displays the directory pathway
                                File directoryPathway = new File("./");
                                System.out.println("Directory Absolute Pathway: " + directoryPathway.getAbsolutePath());

                            }
                        }
                    }
                }
            } // Error handling. Handles any SQL related errors.
            catch (SQLException SqlEx) {
                System.out.println("Error: " + SqlEx);
            }
        }
    }//GEN-LAST:event_btn_monthlyGenerateReportActionPerformed

    private void btn_monthlyClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_monthlyClearMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_monthlyClearMouseEntered

    private void btn_monthlyClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_monthlyClearActionPerformed

        // Sets date picker to null value (resets)
        cmbMonthlyReportMonth.select(0);

        // Resets auto preview report check box
        jcb_monthlyAutoPreviewReport.setSelected(false);

        // Removing all the exiting records in the customer details table
        int cdCountRemove = customerDetailsRecordTableModel.getRowCount();

        for (int i = cdCountRemove - 1; i >= 0; i--) {
            customerDetailsRecordTableModel.removeRow(i);
        }

        // Removing all the exiting records in the transaction details table
        int tdCountRemove = transactionDetailsRecordsTableModel.getRowCount();

        for (int i = tdCountRemove - 1; i >= 0; i--) {
            transactionDetailsRecordsTableModel.removeRow(i);
        }

    }//GEN-LAST:event_btn_monthlyClearActionPerformed

    private void SubmitSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitSAActionPerformed

        // Firstly the transaction must be successful and it should have been inserted into the database
        /**
         * Receipt generation code block must be includes inside the same IF
         * block, right after the successful insertion of the transaction record
         * to the database
         */
        // Generating customer receipt source code
        // Retrieving current time from localhost
        String localTime = ltad.retrieveLocalTimeWith12HourClock();

        System.out.println("Current Time: " + localTime);

        // Retrieving current date and time from localhost 
        String localDateTimeCReceipt = ltad.retrieveLocalDateTimeCReceipt();

        System.out.println("Current Date and Time: " + localDateTimeCReceipt);

        // Retrieving the account number from the panel and assigning to a int variable
        String enteredAcountNumberString = DAccno_Txt.getText();
        // INT data type size is not enough for this length
        int enteredAcountNumberInt = Integer.parseInt(enteredAcountNumberString);

        // Checking if such transaction has occurred from the database
        try (Connection verifyTransactionCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement verifyTransactionStmt = verifyTransactionCon.createStatement();) {

            // Assigning SQL query
            String verifyTransactionSqlQuery = "SELECT TOP 1 * FROM CustomerTransactionDeposit WHERE "
                    + "(ansNSAccountNumber = '" + enteredAcountNumberInt + "' OR absBSAccountNumber = '" + enteredAcountNumberInt + "' OR "
                    + "apsPSAccountNumber = '" + enteredAcountNumberInt + "') ORDER BY TransactionDateTime DESC";

            // Executing SQL query
            ResultSet verifyTransactionRs = verifyTransactionStmt.executeQuery(verifyTransactionSqlQuery);

            // If there is a transaction record this generates a receipt
            if (verifyTransactionRs.next()) {

                /**
                 * Variable declaration to assign value from the database later
                 * Declared because it will be accessible from anywhere within
                 * this code block
                 */
                int transactionNumberDB = 0;
                String transactionDateTimeDB = "";

                // Retrieving transaction number and transaction type from the database, customer_transaction relation
                try (Connection retrievingTransactiondetailsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        Statement retrievingTransactiondetailsStmt = retrievingTransactiondetailsCon.createStatement();) {

                    // Assigning SQL query
                    String retrievingTransactiondetailsSqlQuery = "SELECT TOP 1 DTransactionNumber, TransactionDateTime FROM CustomerTransactionDeposit WHERE "
                            + "(ansNSAccountNumber = '" + enteredAcountNumberInt + "' OR absBSAccountNumber = '" + enteredAcountNumberInt + "' OR "
                            + "apsPSAccountNumber = '" + enteredAcountNumberInt + "') ORDER BY TransactionDateTime DESC";

                    // Executing SQL query
                    ResultSet retrievingTransactiondetailsRs = retrievingTransactiondetailsStmt.executeQuery(retrievingTransactiondetailsSqlQuery);

                    if (retrievingTransactiondetailsRs.next()) {
                        transactionNumberDB = retrievingTransactiondetailsRs.getInt(1);
                        transactionDateTimeDB = retrievingTransactiondetailsRs.getString(2);
                    }
                } // Error handling. Checks for SQL related issues
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                }

                // Checks if transaction type is (D) deposit
                if (transactionNumberDB != 0) {
                    // Calling receipt generation method and passing the relevant data
                    String customerReceiptGenerationStatus = crg.CRGeneration(enteredAcountNumberInt, transactionNumberDB, "Deposit", transactionDateTimeDB);

                    System.out.println("Customer Receipt Generation Status: " + customerReceiptGenerationStatus);

                    // Displaying message box showing confirmation message
                    JOptionPane.showMessageDialog(null,
                            "Customer Transaction Receipt has been Generated",
                            "CONFIRMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Customer Transaction Receipt has been Generated");

                    if (jcb_autoPreviewReceipt.isSelected()) {
                        File myFile = new File("../Receipts/Customer_Receipts/Deposit_Receipts/Customer_Transaction_Receipt_'" + transactionNumberDB + "'_'" + transactionDateTimeDB + "'.pdf");
                        try {
                            Desktop.getDesktop().open(myFile);
                        } // Error handling. Handles any data input and output errors.
                        catch (IOException IoEx) {
                            Logger.getLogger(Teller.class.getName()).log(Level.SEVERE, null, IoEx);
                        }
                    }
                } else {
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                            "No Transaction has been Executed",
                            "ERROR!",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("No Transaction has been Executed");
                }
            } // If there is no record in the database, an error message is shown
            else {
                // Displaying message box showing error message
                JOptionPane.showMessageDialog(null,
                        "No Transaction has been Excecuted",
                        "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("No Transaction has been Executed");
            }
        } // Error handling. Checks for SQL related issues.
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                    "Error Occurred in SQL Connection",
                    "ERROR!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_SubmitSAActionPerformed

    private void btnWithdrawalSubmitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWithdrawalSubmitMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnWithdrawalSubmitMouseEntered

    private void btnWithdrawalSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWithdrawalSubmitActionPerformed

        // Firstly the transaction must be successful and it should have been inserted into the database
        /**
         * Receipt generation code block must be includes inside the same IF
         * block, right after the successful insertion of the transaction record
         * to the database
         */
        // Generating customer receipt source code
        // Retrieving current time from localhost
        String localTime = ltad.retrieveLocalTimeWith12HourClock();

        System.out.println("Current Time: " + localTime);

        // Retrieving current date from localhost
        String localDate = ltad.retrieveLocalDate();

        System.out.println("Current Date: " + localDate);

        // Retrieving the account number from the panel and assigning to a int variable
        String customerAcountNumberString = txtWithdrawalAccountNo.getText();
        // INT data type size is not enough for this length
        int enteredAcountNumberInt = Integer.parseInt(customerAcountNumberString);

        // Checking if such transaction has occurred from the database
        try (Connection verifyTransactionCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                Statement verifyTransactionStmt = verifyTransactionCon.createStatement();) {

            // Assigning SQL query
            String verifyTransactionSqlQuery = "SELECT TOP 1 * FROM CustomerTransactionWithdrawal WHERE "
                    + "(ansNSAccountNumber = '" + enteredAcountNumberInt + "' OR absBSAccountNumber = '" + enteredAcountNumberInt + "' OR "
                    + "apsPSAccountNumber = '" + enteredAcountNumberInt + "') ORDER BY TransactionDateTime DESC";

            // Executing SQL query
            ResultSet verifyTransactionRs = verifyTransactionStmt.executeQuery(verifyTransactionSqlQuery);

            // If there is a transaction record this generates a receipt
            if (verifyTransactionRs.next()) {

                /**
                 * Variable declaration to assign value from the database later
                 * Declared because it will be accessible from anywhere within
                 * this code block
                 */
                int transactionNumberDB = 0;
                String transactionDateTimeDB = "";

                // Retrieving transaction number and transaction type from the database, customer_transaction relation
                try (Connection retrievingTransactiondetailsCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        Statement retrievingTransactiondetailsStmt = retrievingTransactiondetailsCon.createStatement();) {

                    // Assigning SQL query
                    String retrievingTransactiondetailsSqlQuery = "SELECT TOP 1 WTransactionNumber, TransactionDateTime FROM CustomerTransactionWithdrawal WHERE "
                            + "(ansNSAccountNumber = '" + enteredAcountNumberInt + "' OR absBSAccountNumber = '" + enteredAcountNumberInt + "' OR "
                            + "apsPSAccountNumber = '" + enteredAcountNumberInt + "') ORDER BY TransactionDateTime DESC";

                    // Executing SQL query
                    ResultSet retrievingTransactiondetailsRs = retrievingTransactiondetailsStmt.executeQuery(retrievingTransactiondetailsSqlQuery);

                    if (retrievingTransactiondetailsRs.next()) {
                        transactionNumberDB = retrievingTransactiondetailsRs.getInt(1);
                        transactionDateTimeDB = retrievingTransactiondetailsRs.getString(2);
                    }
                } // Error handling. Checks for SQL related issues
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                }

                // Checks if transaction type is (D) deposit
                if (transactionNumberDB != 0) {
                    // Calling receipt generation method and passing the relevant data
                    String customerReceiptGenerationStatus = crg.CRGeneration(enteredAcountNumberInt, transactionNumberDB, "Withdrawal", transactionDateTimeDB);

                    System.out.println("Customer Receipt Generation Status: " + customerReceiptGenerationStatus);

                    // Displaying message box showing confirmation message
                    JOptionPane.showMessageDialog(null,
                            "Customer Transaction Receipt has been Generated",
                            "CONFIRMATION",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Customer Transaction Receipt has been Generated");

                    if (jcb_autoPreviewReceipt.isSelected()) {
                        File myFile = new File("../Receipts/Customer_Receipts/Withdrawal_Receipts/Customer_Transaction_Receipt_'" + transactionNumberDB + "'_'" + transactionDateTimeDB + "'.pdf");
                        try {
                            Desktop.getDesktop().open(myFile);
                        } // Error handling. Handles any data input and output errors.
                        catch (IOException IoEx) {
                            Logger.getLogger(Teller.class.getName()).log(Level.SEVERE, null, IoEx);
                        }
                    }
                } else {
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                            "No Transaction has been Executed",
                            "ERROR!",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("No Transaction has been Executed");
                }
            } // If there is no record in the database, an error message is shown
            else {
                // Displaying message box showing error message
                JOptionPane.showMessageDialog(null,
                        "No Transaction has been Excecuted",
                        "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("No Transaction has been Executed");
            }
        } // Error handling. Checks for SQL related issues.
        catch (SQLException SqlEx) {
            System.out.println("Error found: " + SqlEx);
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                    "Error Occurred in SQL Connection",
                    "ERROR!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnWithdrawalSubmitActionPerformed

    private void listAccountStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listAccountStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listAccountStatusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

        // Clearing all the user entered values from the textboxes and clearing the validation labels
        txtFName.setText("");
        lblFNameValidator.setText("");

        txtMName.setText("");
        lblMNameValidator.setText("");

        txtLName.setText("");
        lblLNameValidator.setText("");

        txtPassportNumber.setText("");
        lblPassportNumberValidator.setText("");

        txtEmailAddress1.setText("");
        lblEmailAddress1Validator.setText("");

        txtEmailAddress2.setText("");
        lblEmailAddress2Validator.setText("");

        txtPhoneNumber1.setText("");
        lblPhoneNumber1Validator.setText("");

        txtPhoneNumber2.setText("");
        lblPhoneNumber2Validator.setText("");

        txtLaneAddress.setText("");
        lblLaneAddressValidator.setText("");

        txtCity.setText("");
        lblCityValidator.setText("");
        
        txtMonthlyIncomeRate.setText("");
        lblMonthlyIncomeRateValidator.setText("");

        listAccountType.setSelectedIndex(0);

        txtInitialDeposit.setText("0.00");
        lblInitialDepositValidator.setText("");

        txtAccountNumber.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountActionPerformed

        // Value Example: Henry
        // Value insertion is mandatory
        System.out.println((Pattern.matches("[a-zA-Z ]+", txtFName.getText())));

        // Value Example: Jacksone
        // Value insertion is mandatory
        System.out.println((Pattern.matches("[a-zA-Z ]+", txtLName.getText())));

        // Value Example: 330022102
        // Value insertion is mandatory
        System.out.println((Pattern.matches("[0-9]{9}+", txtPassportNumber.getText())));

        // Value Example: sample@sample.sample
        // Value insertion is mandatory
        // Must use '\' bacnkslash to add a backslash in quoatation since it is a speacial character used for instances like '\n'
        // '\w' is the short form of '[a-zA-Z0-9]'
        System.out.println((Pattern.matches("([\\w]+[@][a-zA-Z]+([\\.]?[a-zA-Z]?)+)+", txtEmailAddress1.getText())));

        // Value Example: 2587419635
        // Value insertion is mandatory
        System.out.println((Pattern.matches("([0-9]{10})+", txtPhoneNumber1.getText())));

        // Value Example: A43, Beach Lane
        // Value insertion is mandatory
        System.out.println((Pattern.matches("([\\w]+)+", txtLaneAddress.getText())));

        // Value Example: Liverpool
        // Value insertion is mandatory
        System.out.println((Pattern.matches("[\\w]+", txtCity.getText())));

        // Checking if the user has selected a account type
        // Value insertion is mandatory
        System.out.println(((listAccountType.getSelectedItem()).toString() != "--SELECT ACCOUNT TYPE--"));

        // Value Example: 150.00
        // Value insertion is mandatory
        System.out.println((Pattern.matches("([0-9]+[\\.]?[0-9]{2}+)", txtInitialDeposit.getText())));
        System.out.println(txtInitialDeposit.getText());
        // Value Example: 45000002
        // Value insertion is mandatory
        System.out.println((Pattern.matches("([0-9]{8})+", txtAccountNumber.getText())));

        // Checking if the user has selected a account status
        // Value insertion is mandatory
        System.out.println((listAccountStatus.getSelectedItem()).toString() != "--SELECT ACCOUNT STATUS--");

        
        // Checking if valid is inserted all the required fields
        // This uses a static method to compare the text with the regex expression
        if (    
            // Value Example: Henry
            // Value insertion is mandatory
            (Pattern.matches("[a-zA-Z ]+", txtFName.getText()) == true) && 

            // Value Example: Jacksone
            // Value insertion is mandatory
            (Pattern.matches("[a-zA-Z ]+", txtLName.getText()) == true) && 

            // Value Example: 330022102
            // Value insertion is mandatory
            (Pattern.matches("[0-9]{9}+", txtPassportNumber.getText()) == true) && 

            // Value Example: sample@sample.sample
            // Value insertion is mandatory
            // Must use '\' bacnkslash to add a backslash in quoatation since it is a speacial character used for instances like '\n'
            // '\w' is the short form of '[a-zA-Z0-9]'
            (Pattern.matches("([\\w]+[@][a-zA-Z]+([\\.]?[a-zA-Z]?)+)+", txtEmailAddress1.getText()) == true) &&

            // Value Example: 2587419635
            // Value insertion is mandatory
            (Pattern.matches("([0-9]{10})+", txtPhoneNumber1.getText()) == true) &&

            // Value Example: A43, Beach Lane
            // Value insertion is mandatory
            (Pattern.matches("([\\w]+)+", txtLaneAddress.getText()) == true) && 

            // Value Example: Liverpool
            // Value insertion is mandatory
            (Pattern.matches("[\\w]+", txtCity.getText()) == true) && 
                
            // Value Example: 25000.00
            // Value insertion is mandatory
            (Pattern.matches("^[0-9]+[.]+[0-9]+$", txtMonthlyIncomeRate.getText()) == true) && 

            // Checking if the user has selected a account type
            // Value insertion is mandatory
            ((listAccountType.getSelectedItem()).toString() != "--SELECT ACCOUNT TYPE--") && 

            // Value Example: 150.00
            // Value insertion is mandatory
            (Pattern.matches("([0-9]+[\\.]?[0-9]{2}+)", txtInitialDeposit.getText()) == true) && 

            // Value Example: 45000002
            // Value insertion is mandatory
            (Pattern.matches("([0-9]{8})+", txtAccountNumber.getText()) == true) && 
            // Checking if the user has selected a account status
            // Value insertion is mandatory
            ((listAccountStatus.getSelectedItem()).toString() != "--SELECT ACCOUNT STATUS--")  ||

            // Value Example: Adman Andy
            // Value insertion is optional
            (Pattern.matches("[a-zA-Z ]*", txtMName.getText()) == true) ||  

            // Value Example: sample@sample.sample
            // Value insertion is mandatory
            // Must use '\' bacnkslash to add a backslash in quoatation since it is a speacial character used for instances like '\n'
            // '\w' is the short form of '[a-zA-Z0-9]'
            (Pattern.matches("([\\w]+[@][a-zA-Z]+([\\.]?[a-zA-Z]?)+)*", txtEmailAddress2.getText()) == true) ||

            // Value Example: 2587419635
            // Value insertion is mandatory
            (Pattern.matches("([0-9]{10})*", txtPhoneNumber2.getText()) == true)  
   
        ) 
        {
            
            // Calling 'calculateNoOfCustomerAccounts' method to calculate the number of customer accounts for this customer
            int countOfExistingCustomerAccount = noca.calculateNoOfExistingCustomerAccounts(txtPassportNumber.getText());
            
            System.out.println(countOfExistingCustomerAccount);
            
            // Checking if the number of customer accounts is lower than five
            if(countOfExistingCustomerAccount < 5){
                

                
                // Declaring variable to count the number of successful SQL execution for entering customer details and show the appropriate message
                int customerRegistrationComplete = 0;

                // Retrieving the tellerID from the database using the systemLoginID value
                try (Connection retrievingtellerIDCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                        Statement retrievingtellerIDStmt = retrievingtellerIDCon.createStatement();) {

                    // Assigning SQL query
                    String retrievingtellerIDSqlQuery = "SELECT TellerID FROM Teller WHERE slSystemLoginID = '" + systemLoginIDGlobal + "'";

                    // Executing SQL query
                    ResultSet retrievingtellerIDRs = retrievingtellerIDStmt.executeQuery(retrievingtellerIDSqlQuery);

                    if (retrievingtellerIDRs.next()) {
                        tellerIDDB = retrievingtellerIDRs.getString(1);
                    }
                } 
                // Error handling. Checks for SQL related issues
                catch (SQLException SqlEx) {
                    System.out.println("Error found: " + SqlEx);
                    // Displaying message box showing error message
                    JOptionPane.showMessageDialog(null,
                            "Error Occurred in SQL Database Connection",
                            "New Customer Registration - ERROR!",
                            JOptionPane.ERROR_MESSAGE);
                }
                System.out.println(tellerIDDB);
                // Declaring variable to store the user selected account status
                String userSelectedAccountStatus = (String) listAccountStatus.getSelectedItem();

                // Declaring variable to store the user selected status ID
                String userSelectedAccountStatusID = "";

                // Switch statement to assign the appropriate accountstatusID with the selected account status
                switch (userSelectedAccountStatus.toString()) {
                    case "ACTIVE":
                        userSelectedAccountStatusID = "AS000001";
                        break;
                    case "ONHOLD":
                        userSelectedAccountStatusID = "AS000002";
                        break;
                    case "DISABLED":
                        userSelectedAccountStatusID = "AS000003";
                        break;
                    default:
                        userSelectedAccountStatusID = "";
                        break;
                }

                // Displaying an error message if the user did not select an account status
                if (userSelectedAccountStatusID == "") {
                    JOptionPane.showMessageDialog(null,
                            "Please Select an Account Status",
                            "New Customer Registration - ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } 
                else {
                    // Checking if this customer is already registered or not
                    try (Connection checkingCustomerExistanceCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                            Statement checkingCustomerExistanceStmt = checkingCustomerExistanceCon.createStatement();) {

                        // Assigning SQL query
                        String checkingCustomerExistanceSqlQuery = "SELECT * FROM Customer WHERE PassportNumber = '" + txtPassportNumber.getText() + "'";

                        // Executing SQL query
                        ResultSet checkingCustomerExistanceRs = checkingCustomerExistanceStmt.executeQuery(checkingCustomerExistanceSqlQuery);

                        if (checkingCustomerExistanceRs.next() == true) {

                            // Declaring variable to store each individual SQL statement at a given moment
                            String newAccountSQLQuery = "";

                            // Declaring variable to stote the return value of the SQL execution, insertingNewAccountDetails() method
                            Boolean newAccountCreationSuccess = false;

                            // Checking if user didn't select an option and will show an error message
                            if (userSelectedAccountType == "--SELECT ACCOUNT TYPE--") {
                                JOptionPane.showMessageDialog(null,
                                        "Please Select an Account Type !",
                                        "Account Generation Halted!",
                                        JOptionPane.WARNING_MESSAGE);
                            } 
                            else if (userSelectedAccountType == "NORMAL SAVINGS") {
                                /* There is no need to insert the newly generate account number as this column in the relation is automatically incremented 
                                by one upon record insertion*/
                                newAccountSQLQuery = "INSERT INTO AccountNormalSavings (NSAccountBalance, asAccountStatusID, "
                                        + "cPassportNumber, tTellerID_RegisteredBy) VALUES ('" + txtInitialDeposit.getText() + "', "
                                        + "'" + userSelectedAccountStatusID + "', '" + txtPassportNumber.getText() + "', '" + tellerIDDB + "')";

                                // Calling method to insert account details
                                newAccountCreationSuccess = anc.insertingNewAccountDetails(newAccountSQLQuery);

                                if (newAccountCreationSuccess == false) {
                                    customerRegistrationComplete += 1;
                                }

                            } 
                            else if (userSelectedAccountType == "BONUS SAVINGS") {

                                /* There is no need to insert the newly generate account number as this column in the relation is automatically incremented 
                                by one upon record insertion*/
                                newAccountSQLQuery = "INSERT INTO AccountBonusSavings (BSAccountBalance, asAccountStatusID, "
                                        + "cPassportNumber, tTellerID_RegisteredBy) VALUES ('" + txtInitialDeposit.getText() + "', "
                                        + "'" + userSelectedAccountStatusID + "', '" + txtPassportNumber.getText() + "', '" + tellerIDDB + "')";

                                // Calling method to insert account details
                                newAccountCreationSuccess = anc.insertingNewAccountDetails(newAccountSQLQuery);

                            } 
                            else if (userSelectedAccountType == "PREMIER SAVINGS") {
                                /* There is no need to insert the newly generate account number as this column in the relation is automatically incremented 
                                by one upon record insertion*/
                                newAccountSQLQuery = "INSERT INTO AccountPremierSavings (PSAccountBalance, asAccountStatusID, "
                                        + "cPassportNumber, tTellerID_RegisteredBy) VALUES ('" + txtInitialDeposit.getText() + "', "
                                        + "'" + userSelectedAccountStatusID + "', '" + txtPassportNumber.getText() + "', '" + tellerIDDB + "')";

                                // Calling method to insert account details
                                newAccountCreationSuccess = anc.insertingNewAccountDetails(newAccountSQLQuery);
                            }

                            // Displaying message box showing confirmation message
                            JOptionPane.showMessageDialog(null,
                                "New Customer Account has been created",
                                "Adding New Customer - Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                        } 
                        else if (checkingCustomerExistanceRs.next() == false) { // If the customer has not registered

                            // Inserting customer details to the database, Customer relation
                            try (Connection InsertingCustomerCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                    Statement InsertingCustomerStmt = InsertingCustomerCon.createStatement();) {

                                // Assigning SQL query
                                String InsertingCustomerSqlQuery = "INSERT INTO Customer (PassportNumber, FirstName, MiddleName, LastName, "
                                        + "LaneAddress, City, MonthlyIncomeRate, tTellerID_RegisteredBy) VALUES ('" + txtPassportNumber.getText() + "', "
                                        + "'" + txtMName.getText() + "', '" + txtMName.getText() + "', '" + txtLName.getText() + "', '" + txtLaneAddress.getText() + "',"
                                        + " '" + txtCity.getText() + "', '"+ txtMonthlyIncomeRate.getText() +"', '" + tellerIDDB + "')";

                                // Executing SQL query
                                Boolean InsertingCustomerNumberRs = InsertingCustomerStmt.execute(InsertingCustomerSqlQuery);

                                // Checking if the SQL execution returned false
                                // If it was false the records has been added and customerRegistrationComplete would be incremented by one
                                if (InsertingCustomerNumberRs == false) {
                                    customerRegistrationComplete += 1;
                                }

                            } // Error handling. Checks for SQL related issues
                            catch (SQLException SqlEx) {
                                System.out.println("Error found: " + SqlEx);
                                // Displaying message box showing error message
                                JOptionPane.showMessageDialog(null,
                                        "Error Occurred in SQL Database Connection",
                                        "New Customer Registration - ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            // Declaring variable to store each individual SQL statement at a given moment
                            String newAccountSQLQuery = "";

                            // Declaring variable to stote the return value of the SQL execution, insertingNewAccountDetails() method
                            Boolean newAccountCreationSuccess = false;

                            // Checking if user didn't select an option and will show an error message
                            if (userSelectedAccountType == "--SELECT ACCOUNT TYPE--") {
                                JOptionPane.showMessageDialog(null,
                                        "Please Select an Account Type !",
                                        "Account Generation Halted!",
                                        JOptionPane.WARNING_MESSAGE);
                            } 
                            else if (userSelectedAccountType == "NORMAL SAVINGS") {
                                /* There is no need to insert the newly generate account number as this column in the relation is automatically incremented 
                                by one upon record insertion*/
                                newAccountSQLQuery = "INSERT INTO AccountNormalSavings (NSAccountBalance, asAccountStatusID, "
                                        + "cPassportNumber, tTellerID_RegisteredBy) VALUES ('" + txtInitialDeposit.getText() + "', "
                                        + "'" + userSelectedAccountStatusID + "', '" + txtPassportNumber.getText() + "', '" + tellerIDDB + "')";

                                // Calling method to insert account details
                                newAccountCreationSuccess = anc.insertingNewAccountDetails(newAccountSQLQuery);

                            } 
                            else if (userSelectedAccountType == "BONUS SAVINGS") {
                                /* There is no need to insert the newly generate account number as this column in the relation is automatically incremented 
                                by one upon record insertion*/
                                newAccountSQLQuery = "INSERT INTO AccountBonusSavings (BSAccountBalance, asAccountStatusID, "
                                        + "cPassportNumber, tTellerID_RegisteredBy) VALUES ('" + txtInitialDeposit.getText() + "', "
                                        + "'" + userSelectedAccountStatusID + "', '" + txtPassportNumber.getText() + "', '" + tellerIDDB + "')";

                                // Calling method to insert account details
                                newAccountCreationSuccess = anc.insertingNewAccountDetails(newAccountSQLQuery);

                            } 
                            else if (userSelectedAccountType == "PREMIER SAVINGS") {
                                /* There is no need to insert the newly generate account number as this column in the relation is automatically incremented 
                                by one upon record insertion*/
                                newAccountSQLQuery = "INSERT INTO AccountPremierSavings (PSAccountBalance, asAccountStatusID, "
                                        + "cPassportNumber, tTellerID_RegisteredBy) VALUES ('" + txtInitialDeposit.getText() + "', "
                                        + "'" + userSelectedAccountStatusID + "', '" + txtPassportNumber.getText() + "', '" + tellerIDDB + "')";

                                // Calling method to insert account details
                                newAccountCreationSuccess = anc.insertingNewAccountDetails(newAccountSQLQuery);
                            }

                            // Displaying message box showing confirmation message
                            JOptionPane.showMessageDialog(null,
                                "New Savings Account has been created",
                                "Adding New Customer - Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);


                            // Checking if the user has entered one or two email addresses
                            // Inserting customer email address to the database, CustomerEmailAddress relation
                            if ( (txtEmailAddress1.getText()).length() != 0 && (txtEmailAddress2.getText()).length() == 0 ){

                                try (Connection insertingEmailOneCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                        Statement insertingEmailOneStmt = insertingEmailOneCon.createStatement();) {

                                    // Assigning SQL query
                                    String insertingEmailOneSqlQuery = "INSERT INTO CustomerEmailAddress VALUES "
                                            + "('" + txtPassportNumber.getText() + "', '" + txtEmailAddress1.getText() + "')";

                                    // Executing SQL query
                                    Boolean insertingEmailOneRs = insertingEmailOneStmt.execute(insertingEmailOneSqlQuery);

                                    // Checking if the SQL execution returned false
                                    // If it was false the records has been added and customerRegistrationComplete would be incremented by one
                                    if (insertingEmailOneRs == false) {
                                        customerRegistrationComplete += 1;
                                    }
                                } // Error handling. Checks for SQL related issues
                                catch (SQLException SqlEx) {
                                    System.out.println("Error found: " + SqlEx);
                                    // Displaying message box showing error message
                                    JOptionPane.showMessageDialog(null,
                                            "Error Occurred in SQL Database Connection",
                                            "New Customer Registration - ERROR!",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } 
                            else if ((txtEmailAddress1.getText()).length() != 0 && (txtEmailAddress2.getText()).length() != 0) {
                                try (Connection insertingEmailTwoCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                        Statement insertingEmailTwoStmt = insertingEmailTwoCon.createStatement();) {

                                    // Assigning SQL query
                                    String insertingEmailTwoSqlQuery = "INSERT INTO CustomerEmailAddress VALUES "
                                            + "('" + txtPassportNumber.getText() + "', '" + txtEmailAddress1.getText() + "'), "
                                            + "('" + txtPassportNumber.getText() + "', '" + txtEmailAddress2.getText() + "')";

                                    // Executing SQL query
                                    Boolean insertingEmailTwoRs = insertingEmailTwoStmt.execute(insertingEmailTwoSqlQuery);

                                    // Checking if the SQL execution returned false
                                    // If it was false the records has been added and customerRegistrationComplete would be incremented by one
                                    if (insertingEmailTwoRs == false) {
                                        customerRegistrationComplete += 1;
                                    }
                                } // Error handling. Checks for SQL related issues
                                catch (SQLException SqlEx) {
                                    System.out.println("Error found: " + SqlEx);
                                    // Displaying message box showing error message
                                    JOptionPane.showMessageDialog(null,
                                            "Error Occurred in SQL Database Connection",
                                            "New Customer Registration - ERROR!",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            // Checking if the user has entered one or two phone numbers
                            // Inserting customer phone number to the database, CustomerPhoneNumber srelation
                            if ( (txtPhoneNumber1.getText()).length() != 0 && (txtPhoneNumber2.getText()).length() == 0 ) {

                                try (Connection insertingPhoneOneCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                        Statement insertingPhoneOneStmt = insertingPhoneOneCon.createStatement();) {

                                    // Assigning SQL query
                                    String insertingPhoneOneSqlQuery = "INSERT INTO CustomerPhoneNumber VALUES "
                                            + "('" + txtPassportNumber.getText() + "', '" + txtPhoneNumber1.getText()+ "')";

                                    // Executing SQL query
                                    Boolean insertingPhoneOneRs = insertingPhoneOneStmt.execute(insertingPhoneOneSqlQuery);

                                    // Checking if the SQL execution returned false
                                    // If it was false the records has been added and customerRegistrationComplete would be incremented by one
                                    if (insertingPhoneOneRs == false) {
                                        customerRegistrationComplete += 1;
                                    }
                                } // Error handling. Checks for SQL related issues
                                catch (SQLException SqlEx) {
                                    System.out.println("Error found: " + SqlEx);
                                    // Displaying message box showing error message
                                    JOptionPane.showMessageDialog(null,
                                            "Error Occurred in SQL Database Connection",
                                            "New Customer Registration - ERROR!",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else if ( (txtPhoneNumber1.getText()).length() != 0 && (txtPhoneNumber2.getText()).length() != 0 ) {
                                try (Connection insertingPhoneTwoCon = DriverManager.getConnection(db.DatabaseConnectionUrl());
                                        Statement insertingPhoneTwoStmt = insertingPhoneTwoCon.createStatement();) {

                                    // Assigning SQL query
                                    String insertingPhoneTwoSqlQuery = "INSERT INTO CustomerEmailAddress VALUES "
                                            + "('" + txtPassportNumber.getText() + "', '" + txtPhoneNumber1.getText() + "'), "
                                            + "('" + txtPassportNumber.getText() + "', '" + txtPhoneNumber2.getText() + "')";

                                    // Executing SQL query
                                    Boolean insertingPhoneTwoRs = insertingPhoneTwoStmt.execute(insertingPhoneTwoSqlQuery);

                                    // Checking if the SQL execution returned false
                                    // If it was false the records has been added and customerRegistrationComplete would be incremented by one
                                    if (insertingPhoneTwoRs == false) {
                                        customerRegistrationComplete += 1;
                                    }
                                } // Error handling. Checks for SQL related issues
                                catch (SQLException SqlEx) {
                                    System.out.println("Error found: " + SqlEx);
                                    // Displaying message box showing error message
                                    JOptionPane.showMessageDialog(null,
                                            "Error Occurred in SQL Database Connection",
                                            "New Customer Registration - ERROR!",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            // Displaying message box showing confirmation message
                            JOptionPane.showMessageDialog(null,
                                "New Customer has been registered",
                                "Adding New Customer - Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            // Displaying message box showing error message
                            JOptionPane.showMessageDialog(null,
                                "Error occurred while creating new customer account",
                                "Adding New Customer - ERROR",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                    // Error handling. Checks for SQL related issues
                    catch (SQLException SqlEx) {
                        System.out.println("Error found: " + SqlEx);
                        // Displaying message box showing error message
                        JOptionPane.showMessageDialog(null,
                                "Error Occurred in SQL Database Connection",
                                "New Customer Registration - ERROR!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else{ // Executes only if the no of customer accounts is equal to five or greater than five
                System.out.println("Customer has reached maximum amount of accounts");
                // Displaying message box showing error message
                 JOptionPane.showMessageDialog(null,
                "Customer has reached maximum number of accounts",
                "Number of Existing Customer Accounts - ERROR!",
                JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            // Displaying message box showing error message
            JOptionPane.showMessageDialog(null,
                "Check if all the Mandatory fields are Filled (*) with Accurate Values",
                "New Customer Registration - ERROR!",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnCreateAccountActionPerformed

    private void btnGenerateAccountNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateAccountNumberActionPerformed

        // Generating account number for newly creating customer account
        // User selected account type will be assigned to this variable
        userSelectedAccountType = (String) listAccountType.getSelectedItem();

        // Checking if user didn't select an option and will show an error message
        if (userSelectedAccountType == "--SELECT ACCOUNT TYPE--") {
            JOptionPane.showMessageDialog(null,
                    "Please Select an Account Type !",
                    "Account Generation Halted!",
                    JOptionPane.WARNING_MESSAGE);
        } else if (userSelectedAccountType == "NORMAL SAVINGS") {

            // Retrieving the previous generated Normal Savings account number from the database
            newAccountNumber = ang.retrievingPreviousAccountNumber("NSAccountNumber", "AccountNormalSavings");

            // Assigning new account number to textbox
            txtAccountNumber.setText(String.valueOf(newAccountNumber));

        } else if (userSelectedAccountType == "BONUS SAVINGS") {

            // Retrieving the previous generated Bonus Savings account number from the database
            newAccountNumber = ang.retrievingPreviousAccountNumber("BSAccountNumber", "AccountBonusSavings");

            // Assigning new account number to textbox
            txtAccountNumber.setText(String.valueOf(newAccountNumber));

        } else if (userSelectedAccountType == "PREMIER SAVINGS") {

            // Retrieving the previous generated Premier Savings account number from the database
            newAccountNumber = ang.retrievingPreviousAccountNumber("PSAccountNumber", "AccountPremierSavings");

            // Assigning new account number to textbox
            txtAccountNumber.setText(String.valueOf(newAccountNumber));

        }
    }//GEN-LAST:event_btnGenerateAccountNumberActionPerformed

    private void listAccountTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listAccountTypeActionPerformed

        // Retrieving selected option
        String userSelectedAccountType = (String) listAccountType.getSelectedItem();

        // Displaying appropriate text to 'lblInitialDeposit' upon selected option
        if (userSelectedAccountType == "--SELECT ACCOUNT TYPE--") {
            lblInitialDeposit.setText("Initial Deposit");
        } else if (userSelectedAccountType == "NORMAL SAVINGS") {
            lblInitialDeposit.setText("Initial Deposit (£100)");
        } else if (userSelectedAccountType == "BONUS SAVINGS") {
            lblInitialDeposit.setText("Initial Deposit (£300)");
        } else if (userSelectedAccountType == "PREMIER SAVINGS") {
            lblInitialDeposit.setText("Initial Deposit (£1000)");
        }

        // Declaring variable to store the minimum amount of initial deposit validity
        float minimumInitialDeposit = 0.00f;

        switch (userSelectedAccountType) {
            case "NORMAL SAVINGS":
                minimumInitialDeposit = 100.00f;
                break;
            case "BONUS SAVINGS":
                minimumInitialDeposit = 300.00f;
                break;
            case "PREMIER SAVINGS":
                minimumInitialDeposit = 1000.00f;
                break;
            case "SELECT ACCOUNT TYPE":
                minimumInitialDeposit = 0.00f;
                break;
        }

        // Checking if the user entered value is lower to the minimum initial deposit amount
        if (minimumInitialDeposit > (Float.parseFloat(txtInitialDeposit.getText()))) {
            lblInitialDepositValidator.setText("Invalid Value");
            lblInitialDepositValidator.setForeground(Color.red);
        }


    }//GEN-LAST:event_listAccountTypeActionPerformed

    private void txtMNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMNameKeyReleased

        // Validating the user entered first name while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean mNameValidation = Pattern.matches("[a-zA-Z ]*", txtMName.getText());

        if (mNameValidation == false) {
            lblMNameValidator.setText("Invalid Value");
            lblMNameValidator.setForeground(Color.red);

            if ((txtMName.getText()).length() == 0) {
                lblMNameValidator.setText("Enter Middle Name");
                lblMNameValidator.setForeground(Color.white);
            }
        } else if (mNameValidation == true) {
            lblMNameValidator.setText("Valid Value");
            lblMNameValidator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtMNameKeyReleased

    private void txtLNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLNameKeyReleased

        // Validating the user entered last name while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean lNameValidation = Pattern.matches("[a-zA-Z ]+", txtLName.getText());

        if (lNameValidation == false) {
            lblLNameValidator.setText("Invalid Value");
            lblLNameValidator.setForeground(Color.red);

            if ((txtLName.getText()).length() == 0) {
                lblLNameValidator.setText("Enter Last Name");
                lblLNameValidator.setForeground(Color.white);
            }
        } else if (lNameValidation == true) {
            lblLNameValidator.setText("Valid Value");
            lblLNameValidator.setForeground(Color.green);
        }


    }//GEN-LAST:event_txtLNameKeyReleased

    private void txtPassportNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassportNumberKeyReleased

        // Validating the user entered passport number while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean pNumberValidation = Pattern.matches("[0-9]{9}+", txtPassportNumber.getText());

        if (pNumberValidation == false) {
            lblPassportNumberValidator.setText("Invalid Value");
            lblPassportNumberValidator.setForeground(Color.red);

            if ((txtPassportNumber.getText()).length() == 0) {
                lblPassportNumberValidator.setText("Enter Passport Number");
                lblPassportNumberValidator.setForeground(Color.white);
            }
        } else if (pNumberValidation == true) {
            lblPassportNumberValidator.setText("Valid Value");
            lblPassportNumberValidator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtPassportNumberKeyReleased

    private void txtEmailAddress1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailAddress1KeyReleased

        // Validating the user entered email address 1 while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean emailA1Validation = Pattern.matches("([\\w]+[@][a-zA-Z]+([\\.]?[a-zA-Z]?)+)+", txtEmailAddress1.getText());

        if (emailA1Validation == false) {
            lblEmailAddress1Validator.setText("Invalid Value");
            lblEmailAddress1Validator.setForeground(Color.red);

            if ((txtEmailAddress1.getText()).length() == 0) {
                lblEmailAddress1Validator.setText("Enter Email Address 1");
                lblEmailAddress1Validator.setForeground(Color.white);
            }
        } else if (emailA1Validation == true) {
            lblEmailAddress1Validator.setText("Valid Value");
            lblEmailAddress1Validator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtEmailAddress1KeyReleased

    private void txtEmailAddress2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailAddress2KeyReleased

        // Validating the user entered email address 2 while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean emailA2Validation = Pattern.matches("([\\w]+[@][a-zA-Z]+([\\.]?[a-zA-Z]?)+)?", txtEmailAddress2.getText());

        if (emailA2Validation == false) {
            lblEmailAddress2Validator.setText("Invalid Value");
            lblEmailAddress2Validator.setForeground(Color.red);

            if ((txtEmailAddress2.getText()).length() == 0) {
                lblEmailAddress2Validator.setText("Enter Email Address 2");
                lblEmailAddress2Validator.setForeground(Color.white);
            }
        } else if (emailA2Validation == true) {
            lblEmailAddress2Validator.setText("Valid Value");
            lblEmailAddress2Validator.setForeground(Color.green);
        }


    }//GEN-LAST:event_txtEmailAddress2KeyReleased

    private void txtPhoneNumber1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNumber1KeyReleased

        // Validating the user entered phone number 1 while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean pNumber1Validation = Pattern.matches("([0-9]{10})+", txtPhoneNumber1.getText());

        if (pNumber1Validation == false) {
            lblPhoneNumber1Validator.setText("Invalid Value");
            lblPhoneNumber1Validator.setForeground(Color.red);

            if ((txtPhoneNumber1.getText()).length() == 0) {
                lblPhoneNumber1Validator.setText("Enter Phone Number 1");
                lblPhoneNumber1Validator.setForeground(Color.white);
            }
        } else if (pNumber1Validation == true) {
            lblPhoneNumber1Validator.setText("Valid Value");
            lblPhoneNumber1Validator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtPhoneNumber1KeyReleased

    private void txtPhoneNumber2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNumber2KeyReleased

        // Validating the user entered phone number 2 while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean pNumber2Validation = Pattern.matches("([0-9]{10})?", txtPhoneNumber2.getText());

        if (pNumber2Validation == false) {
            lblPhoneNumber2Validator.setText("Invalid Value");
            lblPhoneNumber2Validator.setForeground(Color.red);

            if ((txtPhoneNumber2.getText()).length() == 0) {
                lblPhoneNumber2Validator.setText("Enter Phone Number 2");
                lblPhoneNumber2Validator.setForeground(Color.white);
            }
        } else if (pNumber2Validation == true) {
            lblPhoneNumber2Validator.setText("Valid Value");
            lblPhoneNumber2Validator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtPhoneNumber2KeyReleased

    private void txtLaneAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLaneAddressKeyReleased

        // Validating the user entered lane address while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean lAddressValidation = Pattern.matches("([\\w ]+[, ]*)+", txtLaneAddress.getText());

        if (lAddressValidation == false) {
            lblLaneAddressValidator.setText("Invalid Value");
            lblLaneAddressValidator.setForeground(Color.red);

            if ((txtLaneAddress.getText()).length() == 0) {
                lblLaneAddressValidator.setText("Enter Lane Address");
                lblLaneAddressValidator.setForeground(Color.white);
            }
        } else if (lAddressValidation == true) {
            lblLaneAddressValidator.setText("Valid Value");
            lblLaneAddressValidator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtLaneAddressKeyReleased

    private void txtCityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCityKeyReleased

        // Validating the user entered city while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean cityValidation = Pattern.matches("[a-zA-Z]+", txtCity.getText());

        if (cityValidation == false) {
            lblCityValidator.setText("Invalid Value");
            lblCityValidator.setForeground(Color.red);

            if ((txtCity.getText()).length() == 0) {
                lblCityValidator.setText("Enter City");
                lblCityValidator.setForeground(Color.white);
            }
        } else if (cityValidation == true) {
            lblCityValidator.setText("Valid Value");
            lblCityValidator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtCityKeyReleased

    private void txtInitialDepositKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInitialDepositKeyReleased

        // Validating the user entered initial deposit while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean initialDepositValidation = Pattern.matches("([0-9]+[\\.]?[0-9]{2}+)", txtInitialDeposit.getText());

        if (initialDepositValidation == false) {
            lblInitialDepositValidator.setText("Invalid Value");
            lblInitialDepositValidator.setForeground(Color.red);

            if ((txtInitialDeposit.getText()).length() == 0) {
                lblInitialDepositValidator.setText("Enter Initial Deposit");
                lblInitialDepositValidator.setForeground(Color.white);
            }
        } else if (initialDepositValidation == true) {
            lblInitialDepositValidator.setText("Valid Value");
            lblInitialDepositValidator.setForeground(Color.green);
        }

        String userSelectedAccountType = (String) listAccountType.getSelectedItem();

        // Declaring variable to store the minimum amount of initial deposit validity
        float minimumInitialDeposit = 0.00f;

        switch (userSelectedAccountType) {
            case "NORMAL SAVINGS":
                minimumInitialDeposit = 100.00f;
                break;
            case "BONUS SAVINGS":
                minimumInitialDeposit = 300.00f;
                break;
            case "PREMIER SAVINGS":
                minimumInitialDeposit = 1000.00f;
                break;
        }
        

        // Checking if the user entered value is lower to the minimum initial deposit amount
        if (minimumInitialDeposit > (Float.parseFloat(txtInitialDeposit.getText()))) {
            lblInitialDepositValidator.setText("Invalid Value");
            lblInitialDepositValidator.setForeground(Color.red);
        }

    }//GEN-LAST:event_txtInitialDepositKeyReleased

    private void txtFNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFNameKeyReleased

        // Validating the user entered first name while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean fNameValidation = Pattern.matches("[a-zA-Z ]+", txtFName.getText());

        if (fNameValidation == false) {
            lblFNameValidator.setText("Invalid Value");
            lblFNameValidator.setForeground(Color.red);

            if ((txtFName.getText()).length() == 0) {
                lblFNameValidator.setText("Enter First Name");
                lblFNameValidator.setForeground(Color.white);
            }
        } else if (fNameValidation == true) {
            lblFNameValidator.setText("Valid Value");
            lblFNameValidator.setForeground(Color.green);
        }

    }//GEN-LAST:event_txtFNameKeyReleased

    private void jPanel16ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel16ComponentShown
        // Displaying message box showing to show mandatory fields message (*)
        JOptionPane.showMessageDialog(null,
            "Do the following to register a new customer: \n"
                    + "    Enter First Name\n"
                    + "    Enter Last Name\n"
                    + "    Enter Passport Number\n"
                    + "    Enter Email Address\n"
                    + "    Enter Phone Number\n"
                    + "    Enter Lane Address\n"
                    + "    Enter City\n"
                    + "    Enter Monthly Income Rate\n"
                    + "    Select Account Type\n"
                    + "    Enter Initial Deposit\n"
                    + "    Click on Account Number Generate\n"
                    + "    Select Account Status",
            "New Customer Registration",
            JOptionPane.INFORMATION_MESSAGE);
        
        JOptionPane.showMessageDialog(null,
            "Do the following to create a new account: \n"
                    + "    Enter Passport Number\n"
                    + "    Select Account Type\n"
                    + "    Enter Initial Deposit\n"
                    + "    Click on Account Number Generate\n"
                    + "    Select Account Status",
            "New Account Creation",
            JOptionPane.INFORMATION_MESSAGE);
        
        JOptionPane.showMessageDialog(null,
                    "To register a new customer, \n"
                    + "Please Fill all the fields that has a red Asterisk(*) next to it.",
            "New Customer Registration",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jPanel16ComponentShown

    private void txtMonthlyIncomeRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonthlyIncomeRateKeyReleased
        
        // Validating the user entered monthly income rate while user is typing
        // This uses a static method to compare the text with the regex expression
        Boolean monthlyIncomeRateValidation = Pattern.matches("([0-9]+[.]*[0-9]*)+", txtMonthlyIncomeRate.getText());

        if (monthlyIncomeRateValidation == false) {
            lblMonthlyIncomeRateValidator.setText("Invalid Value");
            lblMonthlyIncomeRateValidator.setForeground(Color.red);

            if ((txtMonthlyIncomeRate.getText()).length() == 0) {
                lblMonthlyIncomeRateValidator.setText("Enter Monthly Income Rate");
                lblMonthlyIncomeRateValidator.setForeground(Color.white);
            }
        } else if (monthlyIncomeRateValidation == true) {
            lblMonthlyIncomeRateValidator.setText("Valid Value");
            lblMonthlyIncomeRateValidator.setForeground(Color.green);
        }
        
    }//GEN-LAST:event_txtMonthlyIncomeRateKeyReleased
    static int xcord,ycord;

    private void jMenuBar2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar2MousePressed
        xcord = evt.getX();
        ycord = evt.getY();
    }//GEN-LAST:event_jMenuBar2MousePressed

    private void jMenuBar2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar2MouseDragged
      int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xcord, y - ycord);
    }//GEN-LAST:event_jMenuBar2MouseDragged

    private void DepositBonusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepositBonusActionPerformed
        // TODO add your handling code here:
        String Type = ACCType_Txt.getText();
        DecimalFormat decimalCorrection = new DecimalFormat("#.00");
        if (Type.equals("NORMAL SAVINGS")) {

            String Formulae = decimalCorrection.format(Double.valueOf(DEPAmount_Txt.getText()) * 1.00125);
            FinalDeposit_Txt.setText(Formulae);

        } else if (Type.equals("BONUS SAVINGS")) {

            String Formulae = decimalCorrection.format((Double.valueOf(DEPAmount_Txt.getText()) * 1.03));
            FinalDeposit_Txt.setText(Formulae);

        } else if (Type.equals("PRIMIER SAVINGS")) {

            String Formulae = decimalCorrection.format((Double.valueOf(DEPAmount_Txt.getText()) * 1.07));
            FinalDeposit_Txt.setText(Formulae);

        }
    }//GEN-LAST:event_DepositBonusActionPerformed

    private void DepositBonusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DepositBonusMouseEntered
        // TODO add your handling code here:
        DepositBonus.setToolTipText("Click to add Bonus Interest to deposit amount.");
    }//GEN-LAST:event_DepositBonusMouseEntered

    private void FinalDeposit_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalDeposit_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FinalDeposit_TxtActionPerformed

    private void DEPAmount_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEPAmount_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DEPAmount_TxtActionPerformed

    private void ACCType_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACCType_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ACCType_TxtActionPerformed

    private void currentBalance_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentBalance_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currentBalance_TxtActionPerformed

    private void holderName_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holderName_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_holderName_TxtActionPerformed

    private void DAccno_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAccno_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DAccno_TxtActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Teller().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ACCType_Txt;
    private javax.swing.JButton CheckSA1;
    private javax.swing.JButton CheckSA_Btn;
    private javax.swing.JButton ClearSA1;
    private javax.swing.JButton ClearSA_Btn;
    private javax.swing.JTextField DAccno_Txt;
    private javax.swing.JTextField DCurrentBalance_Txt;
    private javax.swing.JTextField DEPAmount_Txt;
    private javax.swing.JButton DepositBonus;
    private javax.swing.JPanel Deposit_pnl;
    private javax.swing.JTextField Dwithdraw_Txt;
    private javax.swing.JLabel FName_Lbl;
    private javax.swing.JLabel FName_Lbl3;
    private javax.swing.JButton FetchAcc_Btn;
    private javax.swing.JTextField FinalDeposit_Txt;
    private javax.swing.JTabbedPane NewUser;
    private javax.swing.JButton SubmitSA;
    private javax.swing.JLabel WDRL_Lbl;
    private javax.swing.JTextField WHolder_Txt;
    private javax.swing.JTextArea bonusINFO_tarea;
    private javax.swing.JButton btnCreateAccount;
    private javax.swing.JButton btnGenerateAccountNumber;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnWithdrawalSubmit;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_generateReport;
    private javax.swing.JButton btn_monthlyClear;
    private javax.swing.JButton btn_monthlyGenerateReport;
    private javax.swing.JButton btn_monthlyRetrieveRecords;
    private javax.swing.JButton btn_retrieveRecords;
    private java.awt.Choice cmbMonthlyReportMonth;
    private javax.swing.JTextField currentBalance_Txt;
    private javax.swing.JLabel dAccount;
    private javax.swing.JLabel dAmmount_Lbl;
    private javax.swing.JLabel dBalance_Lbl;
    private javax.swing.JLabel dName_Lbl;
    private javax.swing.JPanel dOption_pnl;
    private javax.swing.JPanel dReport_pnl;
    private javax.swing.JPanel dReport_pnl1;
    private javax.swing.JLabel dType_Lbl;
    private javax.swing.JSplitPane dailyReport_jsp;
    private javax.swing.JLabel date;
    private javax.swing.JLabel day_Lbl;
    private javax.swing.JTextField holderName_Txt;
    private javax.swing.JTextArea interestINFO_tarea;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox jcb_autoPreviewReceipt;
    private javax.swing.JCheckBox jcb_autoPreviewReceiptWithdrawal;
    private javax.swing.JCheckBox jcb_autoPreviewReport;
    private javax.swing.JCheckBox jcb_monthlyAutoPreviewReport;
    private org.jdesktop.swingx.JXDatePicker jdp_selectDate;
    private javax.swing.JLabel jlbl_localDate;
    private javax.swing.JLabel jlbl_localTime;
    private javax.swing.JTable jtb_customerDetailsRecord;
    private javax.swing.JTable jtb_customerTransactionRecords;
    private javax.swing.JTable jtb_transactionDetailsRecords;
    private javax.swing.JLabel lblAccountNumber;
    private javax.swing.JLabel lblAccountStatus;
    private javax.swing.JLabel lblAccountType;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblCityValidator;
    private javax.swing.JLabel lblEmailAddress1;
    private javax.swing.JLabel lblEmailAddress1Validator;
    private javax.swing.JLabel lblEmailAddress2;
    private javax.swing.JLabel lblEmailAddress2Validator;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblFNameValidator;
    private javax.swing.JLabel lblImportantAsterisk1;
    private javax.swing.JLabel lblImportantAsterisk10;
    private javax.swing.JLabel lblImportantAsterisk11;
    private javax.swing.JLabel lblImportantAsterisk12;
    private javax.swing.JLabel lblImportantAsterisk2;
    private javax.swing.JLabel lblImportantAsterisk3;
    private javax.swing.JLabel lblImportantAsterisk4;
    private javax.swing.JLabel lblImportantAsterisk5;
    private javax.swing.JLabel lblImportantAsterisk6;
    private javax.swing.JLabel lblImportantAsterisk7;
    private javax.swing.JLabel lblImportantAsterisk8;
    private javax.swing.JLabel lblImportantAsterisk9;
    private javax.swing.JLabel lblInitialDeposit;
    private javax.swing.JLabel lblInitialDepositValidator;
    private javax.swing.JLabel lblLName;
    private javax.swing.JLabel lblLNameValidator;
    private javax.swing.JLabel lblLaneAddress;
    private javax.swing.JLabel lblLaneAddressValidator;
    private javax.swing.JLabel lblMName;
    private javax.swing.JLabel lblMNameValidator;
    private javax.swing.JLabel lblMonthlyIncomeRateValidator;
    private javax.swing.JLabel lblMonthlyInvoiceRate;
    private javax.swing.JLabel lblPassportNumber;
    private javax.swing.JLabel lblPassportNumberValidator;
    private javax.swing.JLabel lblPhoneNumber1;
    private javax.swing.JLabel lblPhoneNumber1Validator;
    private javax.swing.JLabel lblPhoneNumber2;
    private javax.swing.JLabel lblPhoneNumber2Validator;
    private javax.swing.JLabel lbl_autoStatus;
    private javax.swing.JLabel lbl_automaticlDailyCT;
    private javax.swing.JLabel lbl_customerTransactionRecords;
    private javax.swing.JLabel lbl_manualDailyCT1;
    private javax.swing.JLabel lbl_mautoStatus1;
    private javax.swing.JLabel lbl_mautomaticlDailyCT1;
    private javax.swing.JLabel lbl_mcustomerDetailsRecord;
    private javax.swing.JLabel lbl_mmanualDailyCT2;
    private javax.swing.JLabel lbl_mstatus;
    private javax.swing.JLabel lbl_mtransactionDetailsRecords;
    private javax.swing.JLabel lbl_selectDate1;
    private javax.swing.JLabel lbl_selectMonth;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JComboBox<String> listAccountStatus;
    private javax.swing.JComboBox<String> listAccountType;
    private javax.swing.JMenuItem logout;
    private javax.swing.JTabbedPane mInterest_pnl;
    private javax.swing.JSplitPane monthlyReport_jsp;
    private javax.swing.JPanel nCustomer_pnl;
    private javax.swing.JPanel rOptions_jbl;
    private javax.swing.JPanel rOptions_jbl1;
    private javax.swing.JTabbedPane reports_pnl;
    private javax.swing.JTabbedPane savingsAcc_pnl;
    private javax.swing.JLabel total_Lbl;
    private javax.swing.JTextField txtAccountNumber;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtEmailAddress1;
    private javax.swing.JTextField txtEmailAddress2;
    private javax.swing.JTextField txtFName;
    private javax.swing.JTextField txtInitialDeposit;
    private javax.swing.JTextField txtLName;
    private javax.swing.JTextField txtLaneAddress;
    private javax.swing.JTextField txtMName;
    private javax.swing.JTextField txtMonthlyIncomeRate;
    private javax.swing.JTextField txtPassportNumber;
    private javax.swing.JTextField txtPhoneNumber1;
    private javax.swing.JTextField txtPhoneNumber2;
    private javax.swing.JTextField txtWithdrawalAccountNo;
    private javax.swing.JLabel wACCT;
    private javax.swing.JLabel wBalance_Lbl;
    private javax.swing.JLabel wName_Lbl;
    private javax.swing.JPanel wOption_pnl;
    private javax.swing.JPanel withdrawal_pnl;
    // End of variables declaration//GEN-END:variables
}
