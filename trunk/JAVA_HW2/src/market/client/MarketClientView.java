/*
 * MarketClientView.java
 *
 * Created on 2007�?1�?0�? 下午8:46
 */

package market.client;

import bank.*;
import market.server.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.UUID;
import java.util.Vector;
import javax.swing.JList;

/**
 *
 * @author  Ricky
 */
public class MarketClientView extends javax.swing.JFrame {
    // use it to accept bank interface object
    private Bank bankObj = null;
    // use it to accept bank account interface object
    private BankAccount bankAccount = null;
    // use it to accept market server interface object
    private MarketServer serverObj = null;
    // use it to accept market account object
    private ClientAccount marketAccount = null;
    // use it to accept client interface object
    private ClientInterface clientObj = null;
    private String clientName = null;
    private UUID clientID = null;
    
    private Vector<ItemForSell> allItemForSell = new Vector<ItemForSell>();
    private Vector<ItemForSell> itemICanBuy = new Vector<ItemForSell>();
    private Vector<ItemForSell> wishItems = new Vector<ItemForSell>();
    private Vector<ItemForSell> myItemForSell = new Vector<ItemForSell>();
    private Vector<String> allItemNameForSell = new Vector<String>();
    private Vector<String> wishItemName = new Vector<String>();
    private Vector<String> myItemNameForSell = new Vector<String>();
    
    /** Creates new form MarketClientView */
    public MarketClientView() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemList = new javax.swing.JList();
        comboBox = new javax.swing.JComboBox();
        buyItemButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        newAccountMenuItem = new javax.swing.JMenuItem();
        getAccountMenuItem = new javax.swing.JMenuItem();
        deleteAccountMenuItem = new javax.swing.JMenuItem();
        balanceMenuItem = new javax.swing.JMenuItem();
        depositMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        registerMenuItem = new javax.swing.JMenuItem();
        sellItemMenuItem = new javax.swing.JMenuItem();
        wishMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        nameTextField.setEditable(false);

        itemList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(itemList);

        comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All items for sell", "My Wish", "My items for sell" }));
        comboBox.setEnabled(false);
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        buyItemButton.setText("Buy it");
        buyItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyItemButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        jMenuItem1.setText("exit");
        fileMenu.add(jMenuItem1);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        jMenu1.setText("Bank");

        newAccountMenuItem.setText("Open New Account");
        newAccountMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAccountMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(newAccountMenuItem);

        getAccountMenuItem.setText("Get Account");
        getAccountMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getAccountMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(getAccountMenuItem);

        deleteAccountMenuItem.setText("Delete Account");
        jMenu1.add(deleteAccountMenuItem);

        balanceMenuItem.setText("Balance");
        balanceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(balanceMenuItem);

        depositMenuItem.setText("Deposit");
        depositMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(depositMenuItem);

        menuBar.add(jMenu1);

        jMenu2.setText("Market");

        registerMenuItem.setText("Register");
        registerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(registerMenuItem);

        sellItemMenuItem.setText("Sell Item");
        sellItemMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellItemMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(sellItemMenuItem);

        wishMenuItem.setText("Wish");
        wishMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wishMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(wishMenuItem);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(startButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buyItemButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(buyItemButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        try {
            clientObj = new ClientImpl(this);
            bankObj = (Bank) Naming.lookup("rmi://192.168.11.3:1099/SEB");
            serverObj = (MarketServer) Naming.lookup("rmi://192.168.11.3:1099/TaobaoServer");
            nameTextField.setText(clientName);
            comboBox.setEnabled(true);
            comboBox.setEditable(false);
        } catch (NotBoundException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
	}//GEN-LAST:event_startButtonActionPerformed

    // open a new bank account
    private void newAccountMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAccountMenuItemActionPerformed
        try {
            // TODO add your handling code here:
            bankAccount = bankObj.createAccount(clientName);
            } catch (RemoteException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    NewAccountDlg dialog = new NewAccountDlg(MarketClientView.this, true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                }
            });
    }//GEN-LAST:event_newAccountMenuItemActionPerformed

    // get the bank account
    private void getAccountMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getAccountMenuItemActionPerformed
        try {            
            bankAccount = bankObj.getAccount(clientName);
            JOptionPane.showConfirmDialog(this, "Got your bank account");
        } catch (RemoteException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
	}//GEN-LAST:event_getAccountMenuItemActionPerformed
      

    // deposit to bank account
    private void depositMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositMenuItemActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DepositDlg dialog = new DepositDlg(MarketClientView.this, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_depositMenuItemActionPerformed

    private void registerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                 
            // TODO add your handling code here:
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    RegisterInMarketDlg dialog = new RegisterInMarketDlg(MarketClientView.this, true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                }
            });
    }                                                                                                

    private void balanceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceMenuItemActionPerformed
        // TODO add your handling code here:
        try {            
            float _balance = bankAccount.getBalance();                                               
            textArea.append("Balance of your account: $" + _balance + "\n");
        } catch (RemoteException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_balanceMenuItemActionPerformed

    private void wishMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wishMenuItemActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WishItemDlg dialog = new WishItemDlg(MarketClientView.this, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_wishMenuItemActionPerformed

    private void sellItemMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellItemMenuItemActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SellItemDlg dialog = new SellItemDlg(MarketClientView.this, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_sellItemMenuItemActionPerformed

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
        try {
            // TODO add your handling code here:
            // comboBox = (JComboBox)evt.getSource();
            int index = comboBox.getSelectedIndex();
            itemList.setLayoutOrientation(JList.VERTICAL);
            itemICanBuy.clear();
            allItemNameForSell.clear();
            wishItemName.clear();
            myItemNameForSell.clear();
            switch (index) {
                // select items for sell, use item type "unknown" to ask for all the items for sell
                case 0:
                    allItemForSell = serverObj.getSellsItemsByType(ItemType.Unknown);
                    for (Iterator<ItemForSell> it = allItemForSell.iterator(); it.hasNext();) {
                        ItemForSell itemForSell = it.next();
                        if (this.clientID != itemForSell.getSellerClientID()) {
                            itemICanBuy.add(itemForSell);
                        }
                    }

                    for (Iterator it = itemICanBuy.iterator(); it.hasNext();) {
                        ItemForSell object = (ItemForSell) it.next();
                        allItemNameForSell.add(object.getName());
                    }
                    itemList.setListData(allItemNameForSell);
                    break;
                // select all the items that I wished            
                case 1:
                    wishItems = marketAccount.getWantedItems();                   
                    for (Iterator it = wishItems.iterator(); it.hasNext();) {
                        ItemForSell object = (ItemForSell) it.next();
                        wishItemName.add(object.getName());
                    }
                    itemList.setListData(wishItemName);
                    break;
                // select items that I wanna sell
                case 2:
                    myItemForSell = marketAccount.getSellersItem();                    
                    for (Iterator it = myItemForSell.iterator(); it.hasNext();) {
                        ItemForSell object = (ItemForSell) it.next();
                        myItemNameForSell.add(object.getName());
                    }
                    itemList.setListData(myItemNameForSell);
                    break;
                default: 
                    break;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboBoxActionPerformed

    private void buyItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyItemButtonActionPerformed
        // TODO add your handling code here:
        if ((comboBox.getSelectedIndex() == 0) && (itemList.getSelectedIndex() != -1)) {
            try {
                int index = itemList.getSelectedIndex();
                ItemForSell itemForSell = itemICanBuy.elementAt(index);
                boolean result = serverObj.buyItem(itemForSell, marketAccount);
                String resultStr = null;
                if (result == true) {
                    resultStr = "Wow! This is yours now.";
                }
                else {
                    resultStr = "Sorry, buy-action failed. Please try again!";
                }
                textArea.append(resultStr + "\n");
            } catch (RemoteException ex) {
                Logger.getLogger(MarketClientView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else JOptionPane.showMessageDialog(this, "Invalid Operation!");
    }//GEN-LAST:event_buyItemButtonActionPerformed
    
    public void setMarketAccount(ClientAccount marketAcc) {
        this.marketAccount = marketAcc;
    }
    
    public ClientAccount getMarketAccount() {
        return marketAccount;
    }
    
    private void setClientName(String name) {
        this.clientName = name;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public MarketServer getServerObj() {
        return serverObj;
    }
    
    public BankAccount getBankAccount() {
        return bankAccount;
    }
    
    public ClientInterface getClientObj() {
        return clientObj;
    }
    
    public void setClientID(UUID cID) {
        this.clientID = cID;
    }
    
    public void addMessage(String message) {
        textArea.append(message + "\n");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MarketClientView view = new MarketClientView();
                view.setVisible(true);
                view.setClientName(args[0]);
                
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem balanceMenuItem;
    private javax.swing.JButton buyItemButton;
    private javax.swing.JComboBox comboBox;
    private javax.swing.JMenuItem deleteAccountMenuItem;
    private javax.swing.JMenuItem depositMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem getAccountMenuItem;
    private javax.swing.JList itemList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JMenuItem newAccountMenuItem;
    private javax.swing.JMenuItem registerMenuItem;
    private javax.swing.JMenuItem sellItemMenuItem;
    private javax.swing.JButton startButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JMenuItem wishMenuItem;
    // End of variables declaration//GEN-END:variables
    
}
