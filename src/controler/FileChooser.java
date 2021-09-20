/*
 * Copyright 2021 Stanisław Florczyk
 */
package controler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import model.Model;

/**
 *
 * @author Stasio
 */
public abstract class FileChooser extends Controler
{
 
    /**
     * okno modułu wczytania pliku.
     */
    protected JFrame window = new JFrame("Wczytywanie pliku");  
    /**
     * Panel modułu wczytania pliku.
     */
    protected JPanel panel = new JPanel(new BorderLayout());  
    /**
     * Przycisk Wyszukania pliku i przycisk OK.
     */
    protected JButton openButton, okButton;  
    /**
     * Pole informacyjne.
     */
    protected JTextArea log;   
    /**
     * Obiekt wyszukiwarki plików.
     */
    protected JFileChooser fc; 
 
    /**
     * Główny konstruktor bazowo służy do wczytywania danych uczących.
     * Do innego działania należy użyć jednej z klas dziedziczących.
     * @param t główny obiekt programu.
     */
    public FileChooser() 
    {   
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
        window.setPreferredSize(new Dimension(500, 300));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-250, dim.height/2-150);   
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        log.setPreferredSize(new Dimension(400, 200));
        JScrollPane logScrollPane = new JScrollPane(log);
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setAcceptAllFileFilterUsed(false);
        openButton = new JButton("Wyszukaj Plik...");
        openButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                actionButtonClicked();
            }  
        });
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                window.setVisible(false);
            }  
        });
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(openButton);
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        panel.add(logScrollPane, BorderLayout.CENTER);
        window.add(panel);
        window.pack();
        window.setVisible(true);
    }    
    public FileChooser(Model m) 
    {   
        this();
        model = m;
    }    
    protected abstract void actionButtonClicked();
}
