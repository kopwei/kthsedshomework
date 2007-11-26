/*
 * RegisterInMarketDlg.java
 *
 * Created on 2007年11月21日, 上午3:04
 */

package market.client;

import market.server.MarketServer;
import java.rmi.RemoteException;
import java.util.UUID;
import javax.swing.JOptionPane;

/**
 *
 * @author  Ricky
 */
public class RegisterInMarketDlg extends javax.swing.JDialog {
    private MarketServer serverObj = null;
    private MarketClientView clientView = null;
    /** Creates new form RegisterInMarketDlg */
    public RegisterInMarketDlg(MarketClientView parent, boolean modal) {
        super(parent, modal);
        this.clientView = parent;
        serverObj = parent.getServerObj();
        initComponents();
        this.setLocationRelativeTo(parent);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passWordLabel = new javax.swing.JLabel();
        OkButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        accountNameLabel = new javax.swing.JLabel();
        userNameTestField = new javax.swing.JTextField();
        bankAccountNameLabel = new javax.swing.JLabel();
        bankAccountNameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registry");
        setResizable(false);

        passWordLabel.setText("Password:");

        OkButton.setText("OK");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        accountNameLabel.setText("User Name");

        bankAccountNameLabel.setText("Bank Account Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userNameTestField)
                    .addComponent(passWordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bankAccountNameTextField)
                    .addComponent(accountNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bankAccountNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accountNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(passWordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bankAccountNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bankAccountNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(OkButton))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        // TODO add your handling code here:
        try {
            String userName = userNameTestField.getText();
            char[] password = passwordField.getPassword();
            String bankAccountName = bankAccountNameTextField.getText();
            
            if (userName.length() < 1 || password.length < 6 || bankAccountName.length() < 1) {
                JOptionPane.showMessageDialog(rootPane, "Invalid input");
                return;
            }
            UUID marketAccID = serverObj.login(userName, password, bankAccountName);                                        
            clientView.setMarketAccountID(marketAccID);
            // give the client interface object to server
            serverObj.addClientNotifyObject(clientView.getClientObj(), marketAccID);
            clientView.addMessage("Registration in market succeeds");
            clientView.setBankAccountName(bankAccountName);
            this.dispose();
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(rootPane, "Login failed with invalid password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_OkButtonActionPerformed

    
    /**
     * @param args the command line arguments
     */

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OkButton;
    private javax.swing.JLabel accountNameLabel;
    private javax.swing.JLabel bankAccountNameLabel;
    private javax.swing.JTextField bankAccountNameTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel passWordLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField userNameTestField;
    // End of variables declaration//GEN-END:variables
    
}
