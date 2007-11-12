/*
 * HangmanServerView.java
 *
 * Created on November 12, 2007, 5:23 PM
 */

package hangmanserver;

import java.util.Vector;

/**
 *
 * @author  Kop
 */
public class HangmanServerView extends javax.swing.JFrame {
    
    /** Creates new form HangmanServerView */
    public HangmanServerView() {
        initComponents();
        clientList.setListData(clientVector);
    }
    
    public void addClient(String clientInfo) {
        clientVector.add(clientInfo);
        clientList.updateUI();
    }
    
    public void removeClient(String clientInfo) {
        clientVector.remove(clientInfo);
        clientList.updateUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageAreaScrollPane = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        clientListScrollPane = new javax.swing.JScrollPane();
        clientList = new javax.swing.JList();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hangman Server");

        messageArea.setColumns(20);
        messageArea.setEditable(false);
        messageArea.setRows(5);
        messageArea.setDoubleBuffered(true);
        messageAreaScrollPane.setViewportView(messageArea);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");

        clientList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        clientListScrollPane.setViewportView(clientList);

        statusLabel.setText("Stoped");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopButton))
                    .addComponent(clientListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(messageAreaScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(clientListScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stopButton)
                    .addComponent(startButton)
                    .addComponent(statusLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
    // TODO add your handling code here:
        viewCmd.Start();
        statusLabel.setText("Running...");
}//GEN-LAST:event_startButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList clientList;
    private javax.swing.JScrollPane clientListScrollPane;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JScrollPane messageAreaScrollPane;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
    
    
    private HangmanServerViewCmd viewCmd = new HangmanServerViewCmd(this);
    
    private Vector<String> clientVector = new Vector<String>();
}