package fr.warmadon.dev.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CopyrightPanel extends JPanel {
    
	private JTextField usernameField = new JTextField();
    public CopyrightPanel()
    {
        super();
       
        usernameField.setForeground(new Color(3, 34, 76));
        usernameField.setFont(usernameField.getFont().deriveFont(19F));
        usernameField.setCaretColor(new Color(3, 34, 76));
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(850/ 2 -78, 540/ 2 - 82, 185,40);
        super.setLayout(new GridLayout(1,1));
        super.add(usernameField);
        
        super.setPreferredSize(new Dimension(400,300));
    }
}

