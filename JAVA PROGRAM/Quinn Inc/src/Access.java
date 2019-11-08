/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author ranul
 */
public class Access extends javax.swing.JFrame {

    /**
     * Creates new form Access
     */
    public Access() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AccessPanel = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        UName = new javax.swing.JLabel();
        Password = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        usernameField = new javax.swing.JTextField();
        Selection = new javax.swing.JLabel();
        Services = new javax.swing.JLabel();
        selectedRole = new javax.swing.JComboBox<>();
        bankName = new javax.swing.JLabel();
        Slogan = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Trademark = new javax.swing.JLabel();
        login = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setResizable(false);
        setSize(new java.awt.Dimension(923, 596));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        AccessPanel.setBackground(new java.awt.Color(0, 123, 146));
        AccessPanel.setMaximumSize(new java.awt.Dimension(1200, 800));
        AccessPanel.setMinimumSize(new java.awt.Dimension(1200, 800));
        AccessPanel.setPreferredSize(new java.awt.Dimension(1200, 800));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/QUINN_LOGO.gif"))); // NOI18N
        logo.setText("jLabel2");

        jPanel1.setBackground(new java.awt.Color(18, 63, 72));

        UName.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        UName.setForeground(new java.awt.Color(255, 255, 255));
        UName.setText("Username");

        Password.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        Password.setForeground(new java.awt.Color(255, 255, 255));
        Password.setText("Password");

        passwordField.setBackground(new java.awt.Color(254, 252, 217));
        passwordField.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        usernameField.setBackground(new java.awt.Color(254, 252, 217));
        usernameField.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameFieldKeyPressed(evt);
            }
        });

        Selection.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        Selection.setForeground(new java.awt.Color(255, 255, 255));
        Selection.setText("Select:");

        Services.setBackground(new java.awt.Color(18, 63, 150));
        Services.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        Services.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Services.setText("QUINN BANKING SERVICES");

        selectedRole.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selectedRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECT ROLE--", "Bank Teller", "Bank Manager", "Bank Data Administrator", " " }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Services, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(237, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UName)
                    .addComponent(Password)
                    .addComponent(Selection))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectedRole, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(220, 220, 220))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Services, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UName)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Password))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Selection)
                    .addComponent(selectedRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(129, 129, 129))
        );

        bankName.setFont(new java.awt.Font("Bodoni MT", 0, 36)); // NOI18N
        bankName.setForeground(new java.awt.Color(255, 255, 255));
        bankName.setText("QUINN BANK");

        Slogan.setFont(new java.awt.Font("Script MT Bold", 0, 18)); // NOI18N
        Slogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Slogan.setText("Guarding Day & Night");

        jPanel2.setBackground(new java.awt.Color(10, 10, 18));

        Trademark.setBackground(new java.awt.Color(0, 0, 51));
        Trademark.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Trademark.setForeground(new java.awt.Color(255, 255, 255));
        Trademark.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Trademark.setText("PROPERTY OF QUINN INCORPORATION");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Trademark, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Trademark, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        login.setBackground(new java.awt.Color(41, 169, 25));
        login.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        login.setForeground(new java.awt.Color(255, 255, 255));
        login.setText("LOGIN");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AccessPanelLayout = new javax.swing.GroupLayout(AccessPanel);
        AccessPanel.setLayout(AccessPanelLayout);
        AccessPanelLayout.setHorizontalGroup(
            AccessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AccessPanelLayout.createSequentialGroup()
                .addGroup(AccessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccessPanelLayout.createSequentialGroup()
                        .addGap(327, 327, 327)
                        .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AccessPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(AccessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AccessPanelLayout.createSequentialGroup()
                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(374, 374, 374)
                                .addGroup(AccessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bankName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Slogan, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AccessPanelLayout.setVerticalGroup(
            AccessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccessPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(AccessPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccessPanelLayout.createSequentialGroup()
                        .addComponent(bankName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Slogan))
                    .addComponent(logo))
                .addGap(48, 48, 48)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(login)
                .addGap(63, 63, 63)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(AccessPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(AccessPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed

    }//GEN-LAST:event_passwordFieldActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
       //Opens the Relevant Frame for Authorised User
       
//      MORE AUTHETICATION FUNCTIONS WILL BE ADDED WITH DB IMPLEMENTATION 27/10/2019
       JComboBox cmb = (selectedRole);
       Object selected = cmb.getSelectedItem();
       
       if(cmb.getSelectedIndex()==0) {
           JOptionPane.showMessageDialog(null, 
                              "Please Select your role position to continue.", 
                              "WARNING !", 
                              JOptionPane.WARNING_MESSAGE);
       }
       if(usernameField.getText().isEmpty()) {
           JOptionPane.showMessageDialog(null, 
                              "Please Enter Your Credentials.", 
                              "ERROR !", 
                              JOptionPane.ERROR_MESSAGE);
       }
       else if(selected.toString().equals("Bank Teller"))
       {     
           Teller t1 = new Teller();
           t1.setVisible(true);
           //closes the login form prevent unnecessary tab creation
           this.setVisible(false);
       }
       else if(selected.toString().equals("Bank Manager"))
       {
           Manager m1 = new Manager();
           m1.setVisible(true);
           //closes the login form prevent unnecessary tab creation
           this.setVisible(false);
       }
       else if(selected.toString().equals("Bank Data Administrator"))
       {
           AdminPanel a1 = new AdminPanel();
           a1.setVisible(true);
           //closes the login form prevent unnecessary tab creation
           this.setVisible(false);
       }
        
    }//GEN-LAST:event_loginActionPerformed

    private void usernameFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameFieldKeyPressed

    }//GEN-LAST:event_usernameFieldKeyPressed
    
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

    }//GEN-LAST:event_formMouseDragged
  
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
            java.util.logging.Logger.getLogger(Access.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Access.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Access.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Access.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Access().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccessPanel;
    private javax.swing.JLabel Password;
    private javax.swing.JLabel Selection;
    private javax.swing.JLabel Services;
    private javax.swing.JLabel Slogan;
    private javax.swing.JLabel Trademark;
    private javax.swing.JLabel UName;
    private javax.swing.JLabel bankName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton login;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JComboBox<String> selectedRole;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
