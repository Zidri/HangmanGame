/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_hangman;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author joannaahannigan
 */
public class GameFrame extends javax.swing.JFrame {

    //alphabet array
//    String alphakeys = "qwertyuiopasdfghjklzxcvbnm";
//    char []aralpha = alphakeys.toCharArray();
    char[] aralpha = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    ArrayList<String> arlWordList = new ArrayList();

    //keyboard arraylist
    ArrayList<Keyboard> arlKeys = new ArrayList();
    //hangman arraylist
    JLabel[] arHang;
    int hangcnt = 0;

    //get words from dictionary file
    String word;
    char arword[];
    String filePath = "src/assignment_hangman/popwords.txt";

    //track win/loss
    int win = 0;
    int loss = 0;

    //random number gen -> for new word
    Random RNG;

    public GameFrame() {
        initComponents();

        RNG = new Random();

        keySetup();
        wordSetup();

        jlAnnounce.setVisible(false);

        //add HANGMAN to arHang
        this.arHang = new JLabel[]{
            jlHangH, jlHangA, jlHangN, jlHangG, jlHangM, jlHangA2, jlHangN2
        };

        //make HANGMAN invisible
        for (int i = 0; i < arHang.length; i++) {
            arHang[i].setVisible(false);
        }

        jbNewGame.setVisible(false);

        //make words arraylist
    }

    void endGame(int winLose) {
        //disallow input
        jpKeys.removeAll();

        //show word
        for (int i = 0; i < arword.length; i++) {
            jpWord.getComponent(i).setVisible(true);
        }

        //reset hangcnt
        hangcnt = 0;

        switch (winLose) {
            case 0:
                jlAnnounce.setText("You Lose! Play Again?");
                jlAnnounce.setVisible(true);
                jbNewGame.setVisible(true);
                loss++;
                jlLose.setText("Losses: " + loss);
                break;
            case 1:
                jlAnnounce.setText("You Win! Play Again?");
                jlAnnounce.setVisible(true);
                jbNewGame.setVisible(true);
                win++;
                jlWin.setText("Wins: " + win);
                break;
        }

        repaint();

    }

    //check if letter in word
    void letterCheck(Keyboard letterkey) {
        letterkey.setChecked(true);

        char letter = letterkey.getText().toLowerCase().toCharArray()[0];
        int inWord = 0;
        int viscnt = 0;

        //check if letter in word -> color green if yes
        for (int i = 0; i < arword.length; i++) {
            if (letter == arword[i]) {
                inWord++;
                //change key color to green
                letterkey.setBgCol(Color.green);
                repaint();
                //make letter in word visible
                jpWord.getComponent(i).setVisible(true);
            }
        }

        //stop if word guessed
        for (int i = 0; i < arword.length; i++) {
            if (jpWord.getComponent(i).isVisible()) {
                viscnt++;
            }
            if (viscnt >= arword.length) {
                endGame(1);
            }
        }

        //if letter not in word -> color gray
        if (inWord == 0) {
            inWord = 0;
            //change key color to gray
            letterkey.setBgCol(Color.lightGray);
            repaint();

            //set hangman visible
            if (hangcnt < 7) {
                arHang[hangcnt].setVisible(true);
                hangcnt++;
            }
            else {
                endGame(0);
            }
        }
    }

    //get word array from txt file
    void readTxtFile() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;

            while ((line = reader.readLine()) != null) {
                // add word to array
                arlWordList.add(line);
            }
        }
        catch (IOException e) {
            System.out.println("Error");
        }
    }

    //set up new random word
    void wordSetup() {
        jpWord.removeAll();
        readTxtFile();
        //randomize word
        word = arlWordList.get(RNG.nextInt(arlWordList.size())).toLowerCase();
        arword = word.toCharArray();
//        System.out.println(arword);
        jlWordLength.setText("Word Length: " + arword.length);

        int x = 0;
        int y = 0;

        for (int i = 0; i < arword.length; i++) {
            Keyboard wp = new Keyboard();

            wp.setLocation(x, y);
            wp.setSize(60, 60);
            wp.setVisible(false);
            wp.setBgCol(Color.white);
            wp.setText((arword[i] + "").toUpperCase());

            //add control to arraylist
            jpWord.add(wp);

            //add to panal
            jpWord.add(wp);

            x = x + 60;

            //control rows + cols
            if (i != 0 && (i % 11) == 0) {
                y = y + 60;
                x = 0;
            }

        }//end of for
        jpWord.setSize((arword.length * 60), 60);

        jpWord.repaint();
    }

    //set up keyboard
    void keySetup() {

        jpKeys.removeAll();
        arlKeys.clear();

        int x = 0;
        int y = 0;

        for (int i = 1; i <= 26; i++) {
            Keyboard kp = new Keyboard();

            kp.setLocation(x, y);
            kp.setSize(75, 75);
            kp.setVisible(true);
            kp.setBgCol(Color.white);
            kp.setText((aralpha[i] + "").toUpperCase());
            kp.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!kp.Checked) {
                        letterCheck(kp);
                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

            //add control to arraylist
            arlKeys.add(kp);

            //add to panal
            jpKeys.add(kp);

            x = x + 75;

            //control rows + cols
            if ((i % 9) == 0) {
                y = y + 75;
                x = 0;
            }

        }//end of for

        jpKeys.repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpKeys = new javax.swing.JPanel();
        jlHangN = new javax.swing.JLabel();
        jlHangH = new javax.swing.JLabel();
        jlHangM = new javax.swing.JLabel();
        jlHangA = new javax.swing.JLabel();
        jlHangN2 = new javax.swing.JLabel();
        jlHangG = new javax.swing.JLabel();
        jlHangA2 = new javax.swing.JLabel();
        jpWord = new javax.swing.JPanel();
        jlWin = new javax.swing.JLabel();
        jlAnnounce = new javax.swing.JLabel();
        jbNewGame = new javax.swing.JButton();
        jlLose = new javax.swing.JLabel();
        jlWordLength = new javax.swing.JLabel();
        jlDifLev = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmEasy = new javax.swing.JMenuItem();
        jmHard = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(550, 550));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jpKeys.setBackground(new java.awt.Color(0, 0, 0));
        jpKeys.setPreferredSize(new java.awt.Dimension(700, 273));

        javax.swing.GroupLayout jpKeysLayout = new javax.swing.GroupLayout(jpKeys);
        jpKeys.setLayout(jpKeysLayout);
        jpKeysLayout.setHorizontalGroup(
            jpKeysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        jpKeysLayout.setVerticalGroup(
            jpKeysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        jlHangN.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangN.setForeground(new java.awt.Color(225, 0, 0));
        jlHangN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangN.setText("N");

        jlHangH.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangH.setForeground(new java.awt.Color(225, 0, 0));
        jlHangH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangH.setText("H");

        jlHangM.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangM.setForeground(new java.awt.Color(225, 0, 0));
        jlHangM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangM.setText("M");

        jlHangA.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangA.setForeground(new java.awt.Color(225, 0, 0));
        jlHangA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangA.setText("A");

        jlHangN2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangN2.setForeground(new java.awt.Color(225, 0, 0));
        jlHangN2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangN2.setText("N");

        jlHangG.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangG.setForeground(new java.awt.Color(225, 0, 0));
        jlHangG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangG.setText("G");

        jlHangA2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlHangA2.setForeground(new java.awt.Color(225, 0, 0));
        jlHangA2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlHangA2.setText("A");

        jpWord.setBackground(new java.awt.Color(0, 0, 0));
        jpWord.setPreferredSize(new java.awt.Dimension(700, 100));

        javax.swing.GroupLayout jpWordLayout = new javax.swing.GroupLayout(jpWord);
        jpWord.setLayout(jpWordLayout);
        jpWordLayout.setHorizontalGroup(
            jpWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 724, Short.MAX_VALUE)
        );
        jpWordLayout.setVerticalGroup(
            jpWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jlWin.setText("Wins: 0");

        jlAnnounce.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlAnnounce.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAnnounce.setText("You Win! Play Again?");

        jbNewGame.setText("New Game");
        jbNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNewGameActionPerformed(evt);
            }
        });

        jlLose.setText("Losses: 0");

        jlWordLength.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlWordLength.setText("Word Length: 0");

        jlDifLev.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDifLev.setText("Difficulty: Easy");

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem1.setText("Quit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Difficulty");

        jmEasy.setText("Easy");
        jmEasy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmEasyActionPerformed(evt);
            }
        });
        jMenu2.add(jmEasy);

        jmHard.setText("Hard");
        jmHard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmHardActionPerformed(evt);
            }
        });
        jMenu2.add(jmHard);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jpWord, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlHangH, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlHangA, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlHangN, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlHangG, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlHangM, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlHangA2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jlHangN2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlAnnounce, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbNewGame)
                            .addComponent(jpKeys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jlWin)
                        .addGap(18, 18, 18)
                        .addComponent(jlLose)
                        .addGap(93, 93, 93)
                        .addComponent(jlWordLength, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jlDifLev, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlWordLength, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlDifLev, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlWin)
                            .addComponent(jlLose))))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlHangH, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlHangA, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlHangN, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlHangG, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlHangM, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlHangA2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlHangN2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jpWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jlAnnounce)
                .addGap(6, 6, 6)
                .addComponent(jbNewGame)
                .addGap(50, 50, 50)
                .addComponent(jpKeys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jbNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNewGameActionPerformed

        //make keyboard and word
        keySetup();
        wordSetup();

        for (int i = 0; i < arlKeys.size(); i++) {
           arlKeys.get(i).setChecked(false);
        }
        
        for (int i = 0; i < arword.length; i++) {
            jpWord.getComponent(i).setVisible(false);
        }
        //make HANGMAN invisible
        for (int i = 0; i < arHang.length; i++) {
            arHang[i].setVisible(false);
        }
        
        //hide win lose announce
        jlAnnounce.setVisible(false);
        jbNewGame.setVisible(false);
        this.requestFocus();
    }//GEN-LAST:event_jbNewGameActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        for (int i = 1; i < aralpha.length; i++) {
            if (evt.getKeyChar() == aralpha[i]) {
                
                if (!arlKeys.get(i-1).isChecked()) {
                                       
                    System.out.println(aralpha[i]); 
                    letterCheck(arlKeys.get(i-1));
                }
            }
            else if(evt.getKeyChar() == KeyEvent.VK_ENTER && jbNewGame.isVisible()){
                jbNewGame.doClick();
            }
        }        
    }//GEN-LAST:event_formKeyPressed

    private void jmEasyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmEasyActionPerformed
        filePath = "src/assignment_hangman/popwords.txt";
        jlDifLev.setText("Difficulty: Easy");
        jbNewGame.doClick();        
    }//GEN-LAST:event_jmEasyActionPerformed

    private void jmHardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmHardActionPerformed
        filePath = "src/assignment_hangman/words.txt";
        jlDifLev.setText("Difficulty: Hard");
        jbNewGame.doClick();
    }//GEN-LAST:event_jmHardActionPerformed

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
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JButton jbNewGame;
    private javax.swing.JLabel jlAnnounce;
    private javax.swing.JLabel jlDifLev;
    private javax.swing.JLabel jlHangA;
    private javax.swing.JLabel jlHangA2;
    private javax.swing.JLabel jlHangG;
    private javax.swing.JLabel jlHangH;
    private javax.swing.JLabel jlHangM;
    private javax.swing.JLabel jlHangN;
    private javax.swing.JLabel jlHangN2;
    private javax.swing.JLabel jlLose;
    private javax.swing.JLabel jlWin;
    private javax.swing.JLabel jlWordLength;
    private javax.swing.JMenuItem jmEasy;
    private javax.swing.JMenuItem jmHard;
    private javax.swing.JPanel jpKeys;
    private javax.swing.JPanel jpWord;
    // End of variables declaration//GEN-END:variables
}
