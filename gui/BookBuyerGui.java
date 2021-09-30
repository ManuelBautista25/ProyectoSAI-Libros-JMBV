/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author manuelbautista
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agents.BookBuyerAgent;
import java.awt.FlowLayout;

public class BookBuyerGui extends JFrame {

    private BookBuyerAgent myAgent;

    private JTextField titleField;

    public BookBuyerGui(BookBuyerAgent a) {
        super(a.getLocalName());

        myAgent = a;

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(new JLabel("Book title:"));
        titleField = new JTextField(15);
        p.add(titleField);
        getContentPane().add(p, BorderLayout.CENTER);

        JButton addButton = new JButton("Try to buy");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = titleField.getText().trim();
                    myAgent.tryToBuy(title);
                    close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);
        setResizable(false);
    }

    public void showGui() {
        this.setSize(350, 150);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;

        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

    private void close() {
        this.dispose();
    }
}