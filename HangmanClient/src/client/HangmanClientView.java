/*
 * HangmanClientView.java
 */

package client;

//import com.sun.org.apache.bcel.internal.generic.LoadInstruction;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * The application's main frame.
 */
public class HangmanClientView extends FrameView {

    public HangmanClientView(SingleFrameApplication app) {
        super(app);

        initComponents();
        // group all the text fields at the beginning
        groupTextFields();
        // Disable the resize of the frame
        getFrame().setResizable(false);
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = HangmanClientApp.getApplication().getMainFrame();
            aboutBox = new HangmanClientAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        HangmanClientApp.getApplication().show(aboutBox);
    }

    /**
     * 
     * @param text
     */
    public void setText(String text) {
        // Step 0) Check the text's validity
        if (null == text || 0 == text.length()) {
            return;
        }
        // Step 1) hide all the text field components
        hideAllTextFields();
        
        // Step 2) Set the text fields array;
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            JTextField jTextField = textList.get(i);
            jTextField.setVisible(true);
            Character letter = new Character(charArray[i]);
            if (!Character.isLetter(letter)) {

            }
            jTextField.setText(letter.toString());          
        }
    }
    
    /**
     * 
     * @param dangerLevel
     */
    public void setDanger(int dangerLevel) {
        // Check the parameter's validity
        if (dangerLevel < 0 || dangerLevel > 7) {
            return;
        }
        // Create the string of picture index
        try {
            String danger = "pictureLabel" + Integer.toString(dangerLevel) + ".icon";
            if (0 == dangerLevel) {
                danger = "pictureLabel.icon";
            }
            ResourceMap resourceMap = getResourceMap();
            // Set the picture
            pictureLabel.setIcon(resourceMap.getIcon(danger));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void victory() {
        // TODO : Need implementation here
        statusLabel.setText("You win");
        inputTextField.setEditable(false);
        submitButton.setEnabled(false);
        viewCmd.victory();
    }
    
     public void lose() {
        // TODO: Need implementation here
        statusLabel.setText("You lose");
        inputTextField.setEditable(false);
        submitButton.setEnabled(false);
        viewCmd.lose();
    }
    /**
     * 
     */
    private void hideAllTextFields() {
        for (JTextField tf : textList) {
            tf.setVisible(false);
        }
    }
    

    /**
     * 
     */
    private void groupTextFields()
    {
        // Add all the text fields into the arraylist for easy control
        textList.add(textFieldChar1);
        textList.add(textFieldChar2);
        textList.add(textFieldChar3);
        textList.add(textFieldChar4);
        textList.add(textFieldChar5);
        textList.add(textFieldChar6);
        textList.add(textFieldChar7);
        textList.add(textFieldChar8);
        textList.add(textFieldChar9);
        textList.add(textFieldChar10);
    }
    
    private void submitWord() {
        String word = inputTextField.getText();
        inputTextField.setText("");
        // If the word/character is already guest, then do nothing
        if (!guessedWordSet.contains(word)) {
            viewCmd.submit(word);
            addGuessedWord(word);
        }
    }
    private void addGuessedWord(String word) {
        // TODO: Need implementation here
        // Add the new guessed word into set
        guessedWordSet.add(word);
        // Create a string buffer according to set
        StringBuffer buffer = new StringBuffer();
        for (String str : guessedWordSet) {
            buffer.append(str);
            buffer.append(", ");
        }
        // Set the text area
        passedWordTextArea.setText(buffer.toString());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        textFieldChar10 = new javax.swing.JTextField();
        textFieldChar1 = new javax.swing.JTextField();
        textFieldChar2 = new javax.swing.JTextField();
        textFieldChar3 = new javax.swing.JTextField();
        textFieldChar4 = new javax.swing.JTextField();
        textFieldChar5 = new javax.swing.JTextField();
        textFieldChar6 = new javax.swing.JTextField();
        textFieldChar7 = new javax.swing.JTextField();
        textFieldChar8 = new javax.swing.JTextField();
        textFieldChar9 = new javax.swing.JTextField();
        pictureLabel = new javax.swing.JLabel();
        inputTextField = new javax.swing.JTextField();
        inputLabel = new javax.swing.JLabel();
        passedLetterLabel = new javax.swing.JLabel();
        passedWordScrollPane = new javax.swing.JScrollPane();
        passedWordTextArea = new javax.swing.JTextArea();
        submitButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu gameMenu = new javax.swing.JMenu();
        setUpMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        startButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainPanel.setFocusable(false);
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(500, 365));

        textFieldChar10.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(client.HangmanClientApp.class).getContext().getResourceMap(HangmanClientView.class);
        textFieldChar10.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar10.setText(resourceMap.getString("textFieldChar10.text")); // NOI18N
        textFieldChar10.setFocusable(false);
        textFieldChar10.setName("textFieldChar10"); // NOI18N

        textFieldChar1.setEditable(false);
        textFieldChar1.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar1.setText(resourceMap.getString("textFieldChar1.text")); // NOI18N
        textFieldChar1.setFocusable(false);
        textFieldChar1.setName("textFieldChar1"); // NOI18N

        textFieldChar2.setEditable(false);
        textFieldChar2.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar2.setText(resourceMap.getString("textFieldChar2.text")); // NOI18N
        textFieldChar2.setFocusable(false);
        textFieldChar2.setName("textFieldChar2"); // NOI18N

        textFieldChar3.setEditable(false);
        textFieldChar3.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar3.setText(resourceMap.getString("textFieldChar3.text")); // NOI18N
        textFieldChar3.setFocusable(false);
        textFieldChar3.setName("textFieldChar3"); // NOI18N

        textFieldChar4.setEditable(false);
        textFieldChar4.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar4.setText(resourceMap.getString("textFieldChar4.text")); // NOI18N
        textFieldChar4.setFocusable(false);
        textFieldChar4.setName("textFieldChar4"); // NOI18N

        textFieldChar5.setEditable(false);
        textFieldChar5.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar5.setText(resourceMap.getString("textFieldChar5.text")); // NOI18N
        textFieldChar5.setFocusable(false);
        textFieldChar5.setName("textFieldChar5"); // NOI18N

        textFieldChar6.setEditable(false);
        textFieldChar6.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar6.setText(resourceMap.getString("textFieldChar6.text")); // NOI18N
        textFieldChar6.setFocusable(false);
        textFieldChar6.setName("textFieldChar6"); // NOI18N

        textFieldChar7.setEditable(false);
        textFieldChar7.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar7.setText(resourceMap.getString("textFieldChar7.text")); // NOI18N
        textFieldChar7.setFocusable(false);
        textFieldChar7.setName("textFieldChar7"); // NOI18N

        textFieldChar8.setEditable(false);
        textFieldChar8.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar8.setText(resourceMap.getString("textFieldChar8.text")); // NOI18N
        textFieldChar8.setFocusable(false);
        textFieldChar8.setName("textFieldChar8"); // NOI18N

        textFieldChar9.setEditable(false);
        textFieldChar9.setFont(resourceMap.getFont("textFieldChar9.font")); // NOI18N
        textFieldChar9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldChar9.setText(resourceMap.getString("textFieldChar9.text")); // NOI18N
        textFieldChar9.setFocusable(false);
        textFieldChar9.setName("textFieldChar9"); // NOI18N

        pictureLabel.setIcon(resourceMap.getIcon("pictureLabel.icon")); // NOI18N
        pictureLabel.setText(resourceMap.getString("pictureLabel.text")); // NOI18N
        pictureLabel.setFocusable(false);
        pictureLabel.setName("pictureLabel"); // NOI18N

        inputTextField.setEditable(false);
        inputTextField.setText(resourceMap.getString("inputTextField.text")); // NOI18N
        inputTextField.setName("inputTextField"); // NOI18N
        inputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTextFieldActionPerformed(evt);
            }
        });

        inputLabel.setText(resourceMap.getString("inputLabel.text")); // NOI18N
        inputLabel.setName("inputLabel"); // NOI18N

        passedLetterLabel.setText(resourceMap.getString("passedLetterLabel.text")); // NOI18N
        passedLetterLabel.setName("passedLetterLabel"); // NOI18N

        passedWordScrollPane.setDoubleBuffered(true);
        passedWordScrollPane.setName("passedWordScrollPane"); // NOI18N

        passedWordTextArea.setColumns(20);
        passedWordTextArea.setEditable(false);
        passedWordTextArea.setRows(5);
        passedWordTextArea.setName("passedWordTextArea"); // NOI18N
        passedWordScrollPane.setViewportView(passedWordTextArea);

        submitButton.setText(resourceMap.getString("submitButton.text")); // NOI18N
        submitButton.setEnabled(false);
        submitButton.setName("submitButton"); // NOI18N
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(inputLabel)
                        .addGap(185, 185, 185))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(passedLetterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(textFieldChar1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldChar10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(passedWordScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(inputLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submitButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passedLetterLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passedWordScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textFieldChar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldChar10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(pictureLabel)
                        .addGap(19, 19, 19))))
        );

        menuBar.setName("menuBar"); // NOI18N

        gameMenu.setText(resourceMap.getString("gameMenu.text")); // NOI18N
        gameMenu.setName("gameMenu"); // NOI18N

        setUpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        setUpMenuItem.setText(resourceMap.getString("setUpMenuItem.text")); // NOI18N
        setUpMenuItem.setToolTipText(resourceMap.getString("setUpMenuItem.toolTipText")); // NOI18N
        setUpMenuItem.setName("setUpMenuItem"); // NOI18N
        setUpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setUpMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(setUpMenuItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(client.HangmanClientApp.class).getContext().getActionMap(HangmanClientView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        gameMenu.add(exitMenuItem);

        menuBar.add(gameMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setFocusable(false);
        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        startButton.setText(resourceMap.getString("startButton.text")); // NOI18N
        startButton.setActionCommand(resourceMap.getString("startButton.actionCommand")); // NOI18N
        startButton.setName("startButton"); // NOI18N
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        endButton.setText(resourceMap.getString("endButton.text")); // NOI18N
        endButton.setActionCommand(resourceMap.getString("endButton.actionCommand")); // NOI18N
        endButton.setName("endButton"); // NOI18N
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        statusLabel.setText(resourceMap.getString("statusLabel.text")); // NOI18N
        statusLabel.setName("statusLabel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(statusMessageLabel))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, statusPanelLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                                .addComponent(startButton)
                                .addGap(6, 6, 6))
                            .addGroup(statusPanelLayout.createSequentialGroup()
                                .addContainerGap(239, Short.MAX_VALUE)
                                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(statusMessageLabel)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusLabel)
                            .addComponent(endButton)
                            .addComponent(startButton))
                        .addContainerGap())))
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        boolean bResult = viewCmd.startButtonActionPerformed();
        // When a game starts, we will clear the guessed word set and clear the corresponding text
        // area
        if (bResult) {
            guessedWordSet.clear();
            passedWordTextArea.setText("");
            submitButton.setEnabled(true);
            inputTextField.setEditable(true);
            statusLabel.setText("Game started");
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        // TODO add your handling code here:
        viewCmd.endButtonActionPerformed();
    }//GEN-LAST:event_endButtonActionPerformed

    private void setUpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setUpMenuItemActionPerformed
        // TODO add your handling code here:
        viewCmd.setUpMenuItemActionPerformed();
    }//GEN-LAST:event_setUpMenuItemActionPerformed

    private void inputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTextFieldActionPerformed
        // TODO add your handling code here:
        submitWord();
    }//GEN-LAST:event_inputTextFieldActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        submitWord();
    }//GEN-LAST:event_submitButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton endButton;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JTextField inputTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel passedLetterLabel;
    private javax.swing.JScrollPane passedWordScrollPane;
    private javax.swing.JTextArea passedWordTextArea;
    private javax.swing.JLabel pictureLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem setUpMenuItem;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField textFieldChar1;
    private javax.swing.JTextField textFieldChar10;
    private javax.swing.JTextField textFieldChar2;
    private javax.swing.JTextField textFieldChar3;
    private javax.swing.JTextField textFieldChar4;
    private javax.swing.JTextField textFieldChar5;
    private javax.swing.JTextField textFieldChar6;
    private javax.swing.JTextField textFieldChar7;
    private javax.swing.JTextField textFieldChar8;
    private javax.swing.JTextField textFieldChar9;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    
    //private Image[] hangmanImages = new Image[8];
       
    private JDialog aboutBox;
    private ArrayList<JTextField> textList = new ArrayList<JTextField>();
    private HangmanClientCmd viewCmd = new HangmanClientCmd(this);
    private HashSet<String> guessedWordSet = new HashSet<String>();
}
