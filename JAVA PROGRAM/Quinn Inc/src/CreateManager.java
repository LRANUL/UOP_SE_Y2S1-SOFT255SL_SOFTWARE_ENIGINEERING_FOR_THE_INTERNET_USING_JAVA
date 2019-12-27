
import DatabaseConnection.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hp-Pc
 */
public class CreateManager extends javax.swing.JFrame {

    /**
     * Creates new form CreateManager
     */
    Connection conn=null;
    PreparedStatement ps=null; 
    DBConnection db;
    ResultSet rs= null;
    
    
    public CreateManager() {
        initComponents();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        db=new DBConnection();
        
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        mUName_Lbl = new javax.swing.JLabel();
        mButton1 = new javax.swing.JButton();
        mPassword_Lbl = new javax.swing.JLabel();
        mUName_Txt = new javax.swing.JTextField();
        password_Txt = new javax.swing.JTextField();
        slPosition_Lbl = new javax.swing.JLabel();
        saposition_Lbl = new javax.swing.JLabel();
        Lname_Lbl = new javax.swing.JLabel();
        Mname_Lbl = new javax.swing.JLabel();
        city_Lbl = new javax.swing.JLabel();
        LAddress_Lbl = new javax.swing.JLabel();
        branch_Lbl = new javax.swing.JLabel();
        Fname_Lbl = new javax.swing.JLabel();
        slPosition_Txt = new javax.swing.JTextField();
        branch_Txt = new javax.swing.JTextField();
        Fname_Txt = new javax.swing.JTextField();
        Lname_Txt = new javax.swing.JTextField();
        Laddress_Txt = new javax.swing.JTextField();
        city_Txt = new javax.swing.JTextField();
        Mname_Txt = new javax.swing.JTextField();
        mail_Lbl = new javax.swing.JLabel();
        email_Txt = new javax.swing.JTextField();
        position_Txt = new javax.swing.JTextField();
        login_Lbl = new javax.swing.JLabel();
        login_Txt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 123, 146));

        jPanel1.setBackground(new java.awt.Color(18, 63, 72));

        mUName_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mUName_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        mUName_Lbl.setText("Username");

        mButton1.setText("Insert");
        mButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mButton1ActionPerformed(evt);
            }
        });

        mPassword_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mPassword_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        mPassword_Lbl.setText("Password");

        slPosition_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        slPosition_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        slPosition_Lbl.setText("slPosition ID");

        saposition_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saposition_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        saposition_Lbl.setText("saPosition ID");

        Lname_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Lname_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        Lname_Lbl.setText("Last Name");

        Mname_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Mname_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        Mname_Lbl.setText("Middle Name");

        city_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        city_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        city_Lbl.setText("City");

        LAddress_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LAddress_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        LAddress_Lbl.setText("Lane Address");

        branch_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        branch_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        branch_Lbl.setText("Branch");

        Fname_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Fname_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        Fname_Lbl.setText("First Name");

        mail_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mail_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        mail_Lbl.setText("Email Address");

        login_Lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        login_Lbl.setForeground(new java.awt.Color(240, 240, 240));
        login_Lbl.setText("System login ID");

        login_Txt.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(login_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mail_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slPosition_Lbl)
                            .addComponent(mPassword_Lbl)
                            .addComponent(saposition_Lbl)
                            .addComponent(branch_Lbl)
                            .addComponent(Fname_Lbl)
                            .addComponent(Mname_Lbl)
                            .addComponent(Lname_Lbl)
                            .addComponent(city_Lbl)
                            .addComponent(LAddress_Lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mUName_Lbl))
                        .addGap(151, 151, 151)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(login_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(password_Txt)
                                .addComponent(mUName_Txt)
                                .addComponent(slPosition_Txt)
                                .addComponent(branch_Txt)
                                .addComponent(Fname_Txt)
                                .addComponent(Lname_Txt)
                                .addComponent(Laddress_Txt)
                                .addComponent(city_Txt)
                                .addComponent(Mname_Txt)
                                .addComponent(email_Txt)
                                .addComponent(position_Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(mButton1)))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mUName_Lbl)
                            .addComponent(mUName_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(password_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mPassword_Lbl))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slPosition_Lbl)
                    .addComponent(slPosition_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saposition_Lbl)
                    .addComponent(position_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(branch_Lbl)
                    .addComponent(branch_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Fname_Lbl)
                    .addComponent(Fname_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Mname_Lbl)
                    .addComponent(Mname_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lname_Lbl)
                    .addComponent(Lname_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LAddress_Lbl)
                    .addComponent(Laddress_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(city_Lbl)
                    .addComponent(city_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mail_Lbl)
                    .addComponent(email_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login_Lbl)
                    .addComponent(login_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(mButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mButton1ActionPerformed

        try {
            conn=DriverManager.getConnection(db.DatabaseConnectionUrl());
            String sql="INSERT INTO dbo.SystemLogin(UserName, Password,slpPositionId ,saAdminID) VALUES (?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,mUName_Txt.getText());
            ps.setString(2,password_Txt.getText());
            ps.setString(3,slPosition_Txt.getText());
            ps.setString(4,position_Txt.getText());
            ps.executeUpdate();
            
            String sql2="SELECT TOP 1 SystemLoginID from dbo.SystemLogin ORDER BY SystemLoginID DESC";
            ps=conn.prepareStatement(sql2);
            rs=ps.executeQuery();
            
            while(rs.next())
            {
            login_Txt.setText(rs.getString("SystemLoginID"));
            }
            
            
            String sql3="INSERT INTO dbo.Manager(Branch,FirstName,MiddleName,LastName,LaneAddress,City,EmailAddress,slSystemLoginID) VALUES (?,?,?,?,?,?,?,?)";
            ps=conn.prepareStatement(sql3);
            ps.setString(1, branch_Txt.getText());
            ps.setString(2, Fname_Txt.getText());
            ps.setString(3, Mname_Txt.getText());
            ps.setString(4, Lname_Txt.getText());
            ps.setString(5, Laddress_Txt.getText());
            ps.setString(6, city_Txt.getText());
            ps.setString(7, email_Txt.getText());
            ps.setString(8, login_Txt.getText());
            ps.executeUpdate();
            
            
            JOptionPane.showMessageDialog(null, "The manager Details are entered to database");
            
            //closes the window
            this.dispose();

        } catch (SQLException ex) {
            Logger.getLogger(CreateManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_mButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(CreateManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fname_Lbl;
    private javax.swing.JTextField Fname_Txt;
    private javax.swing.JLabel LAddress_Lbl;
    private javax.swing.JTextField Laddress_Txt;
    private javax.swing.JLabel Lname_Lbl;
    private javax.swing.JTextField Lname_Txt;
    private javax.swing.JLabel Mname_Lbl;
    private javax.swing.JTextField Mname_Txt;
    private javax.swing.JLabel branch_Lbl;
    private javax.swing.JTextField branch_Txt;
    private javax.swing.JLabel city_Lbl;
    private javax.swing.JTextField city_Txt;
    private javax.swing.JTextField email_Txt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel login_Lbl;
    private javax.swing.JTextField login_Txt;
    private javax.swing.JButton mButton1;
    private javax.swing.JLabel mPassword_Lbl;
    private javax.swing.JLabel mUName_Lbl;
    private javax.swing.JTextField mUName_Txt;
    private javax.swing.JLabel mail_Lbl;
    private javax.swing.JTextField password_Txt;
    private javax.swing.JTextField position_Txt;
    private javax.swing.JLabel saposition_Lbl;
    private javax.swing.JLabel slPosition_Lbl;
    private javax.swing.JTextField slPosition_Txt;
    // End of variables declaration//GEN-END:variables
}
