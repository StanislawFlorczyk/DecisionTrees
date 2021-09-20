/*
 * Copyright 2021 Stanisław Florczyk
 */
package view;

import controler.ControlerClassification;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author Stasio
 */
public class ViewClassification extends View
{
    public JComponent input = new JTextArea();
    public JLabel info  = new JLabel();
    
    private ControlerClassification controler;
    public ViewClassification(ControlerClassification cc, JTextArea ta)
    { 
        super("Klasyfikacja");
        infoArea = ta;
        controler = cc;
    }
    @Override
    public void createAndShow()
    {
        setLayout(new BorderLayout());
        centerWindow(getSize().width, getSize().height);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        JButton ok = new JButton("Dalej");
        ok.setPreferredSize(new Dimension(100, 50));
        ok.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.okButtonClicked();
            }
        });
        add(input);
        add(info, BorderLayout.LINE_START);
        add(ok, BorderLayout.LINE_END);
        input.requestFocus();
        setAlwaysOnTop(true);
        repaint();
    }
    public void addInput()
    {
        add(input, BorderLayout.CENTER);
        input.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER)
                {
                    controler.okButtonClicked();
                }
            } 
        });
        input.setVisible(true);
    }
    public void removeInput()
    {
        remove(input);
    }
    @Override
    public void clearWorkSpace()
    {
        controler.clearClicked();
    }  
}
