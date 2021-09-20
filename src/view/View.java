/*
 * Copyright 2021 Stanisław Florczyk
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Stasio
 */
public abstract class View extends JFrame
{  
    protected JScrollPane scrollPanel = new JScrollPane(); 
    public View()
    {
        super();
        createWorkspace();
    }
    public View(String s)
    {
        super(s);
        createWorkspace();
    }
    public JTextArea infoArea  = new JTextArea(); 
    public abstract void createAndShow();
    public abstract void clearWorkSpace();
    /**
     * Wyświetla informacje dla użytkownika wyłączając aktywne okno.
     * @param str informacja do wyświetlenia.
     */
    public void warrningWindow(String str)
    {
        this.setEnabled(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        warningWindow = new JFrame();
        warningWindow.setLayout(null);
        warningWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        warningWindow.setVisible(true);
        warningWindow.setTitle("Uwaga");
        warningWindow.setSize(str.length() * 8, 120);
        warningWindow.setResizable(false);
        warningWindow.setLocation(dim.width/2-warningWindow.getSize().width/2, dim.height/2-warningWindow.getSize().height/2);
        warningWindow.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                doClick();
            }
        });
        
        JLabel info  = new JLabel();
        info.setBounds(0, 0,str.length() * 8, 50);
        info.setText(str);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setHorizontalTextPosition(SwingConstants.CENTER);
        warningWindow.add(info);

        JButton ok = new JButton("OK");
        ok.setBounds(warningWindow.getWidth() / 2 - 50, 50, 100, 30);
        ok.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {               
                warningWindow.dispatchEvent(new WindowEvent(warningWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        warningWindow.add(ok);
        warningWindow.repaint();
    }  
    public void warrningWindow(String str, Integer x, Integer y)
    {
        this.setEnabled(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        warningWindow = new JFrame();
        warningWindow.setLayout(null);
        warningWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        warningWindow.setVisible(true);
        warningWindow.setTitle("Uwaga");
        warningWindow.setSize(str.length() * 8, 120);
        warningWindow.setResizable(false);
        warningWindow.setLocation(x, y);
        warningWindow.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                doClick();
            }
        });
        
        JLabel info  = new JLabel();
        info.setBounds(0, 0,str.length() * 8, 50);
        info.setText(str);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setHorizontalTextPosition(SwingConstants.CENTER);
        warningWindow.add(info);

        JButton ok = new JButton("OK");
        ok.setBounds(warningWindow.getWidth() / 2 - 50, 50, 100, 30);
        ok.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {               
                warningWindow.dispatchEvent(new WindowEvent(warningWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        warningWindow.add(ok);
        warningWindow.repaint();
    }  
    protected void centerWindow(int w, int h)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(w,h)); 
        setLocation(dim.width/2-w/2, dim.height/2-h/2);
    }
    private void doClick()
    {
        setEnabled(true);
    }
    private void createWorkspace() 
    {
        infoArea = new JTextArea();
        infoArea.setColumns(20);
        infoArea.setRows(5);
        infoArea.setEditable(false);
        scrollPanel.setViewportView(infoArea);
        scrollPanel.setPreferredSize(new Dimension(800, 600));
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
    private JFrame warningWindow;
}
