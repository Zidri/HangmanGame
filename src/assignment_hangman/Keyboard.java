/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_hangman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author joannaahannigan
 */
public class Keyboard extends JComponent {

    Color bgCol = Color.white;
    String text = "----";
//    int width = 50;
    boolean Checked = false;

    public Keyboard() {
        this.setForeground(bgCol);
        this.setVisible(true);        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font gameFont = new Font("Lucida Console", Font.BOLD, 18);
        
//        ORDER MATTERS!!!!
        g.setColor(Color.black);
        g.fillRect(0, 0, 100, 100);
        g.setColor(bgCol);
        g.fillRect(5, 5, 90, 90);
        g.setColor(Color.black);

        g.setFont(gameFont);

        //center text horizontaly
        int fontwidth = g.getFontMetrics().stringWidth(text);
        int left = (this.getWidth() - fontwidth) / 2;

        g.drawString(text, left, 35);
    }

    public Color getBgCol() {
        return bgCol;
    }

    public void setBgCol(Color bgCol) {
        this.bgCol = bgCol;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean Checked) {
        this.Checked = Checked;
    }
    
    
    
}

