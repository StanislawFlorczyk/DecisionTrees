/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controler.ControlerMainFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import model.Model;

/**
 *
 * @author Stasio
 */
public class ViewMainFrame extends View
{
    private ControlerMainFrame controler;
    /**
     * Pasek Menu.
     */
    private JMenuBar menuBar = new JMenuBar(); 
    /**
     * Stopka.
     */
    private JLabel bottomPanel = new JLabel();
    /**
     * Przycisk modułu wczytywania danych.
     */
    private JLabel loadButton = new JLabel();
    /**
     * Przycisk modułu wyświetlania danych.
     */
    private JLabel dataButton = new JLabel();
    /**
     * Przycisk modułu wyświetlania regułowej reprezentacji drzewa decyzyjnego.
     */
    private JLabel ruleButton = new JLabel();
    /**
     * Przycisk modułu wyświetlania graficznej reprezentacji drzewa decyzyjnego.
     */
    private JLabel treeButton = new JLabel();    
    /**
     * Przycisk modułu obliczania modelu drzewa decyzyjnego.
     */
    private JLabel calcButton = new JLabel();
    /**
     * Przycisk modułu wyświetlania informacji o danych uczących.
     */
    private JLabel infoButton = new JLabel();
    /**
     * Przycisk wyjścia z programu.
     */
    private JLabel exitButton = new JLabel();  
    public ViewMainFrame(Model m, int w, int h)
    { 
        controler = new ControlerMainFrame(m, this);
        centerWindow(w,h);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }
    public ViewMainFrame()
    {
        this(new Model(),1200, 800);
    }
    @Override
    public void clearWorkSpace()
    {
        infoArea.setText("");
        repaint();
    }
    @Override
    public void createAndShow()
    {
        createLayout();
        createControlPanel();
        createBottomPanel();
        createMenu();    
        this.pack();
        setVisible(true);
        this.repaint();
    }
    /**
     * Wyrównuje elementy okna.
     */
    private void createLayout() 
    {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addComponent(dataButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addComponent(infoButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addComponent(ruleButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addComponent(treeButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addGap(50)
                .addComponent(calcButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))   
            .addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(ruleButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(treeButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                .addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
        );
    }   
    /**
     * Tworzy panel sterowania.
     */
    private void createControlPanel() 
    {
        loadButton.setVisible(false);
        dataButton.setVisible(false);
        ruleButton.setVisible(false);
        treeButton.setVisible(false);
        calcButton.setVisible(false);
        infoButton.setVisible(false);
        exitButton.setVisible(false);
        loadButton.setToolTipText("Wczytaj Plik z Danymi");
        loadButton.setIcon(new ImageIcon(getClass().getResource("images/loadIcon.png"), "Wczytaj Drzewo"));
        loadButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.menuFileLoadTrainnigSetClicked();
            }
        });
        dataButton.setToolTipText("Wyświetl Dane Tabelaryczne");
        dataButton.setIcon(new ImageIcon(getClass().getResource("images/dateIcon.png"), "Pokaz Dane"));
        dataButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.menuViewTrainingSetDataClicked();
            }
        });
        ruleButton.setToolTipText("Wyświetl Reprezentacje Regułową");
        ruleButton.setIcon(new ImageIcon(getClass().getResource("images/ruleViewIcon.png"), "Pokaz Reguły"));
        ruleButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.menuViewRulesClicked();
            }
        });
        treeButton.setToolTipText("Wyświetl Reprezentacje Graficzną");
        treeButton.setIcon(new ImageIcon(getClass().getResource("images/treeViewIcon.png"), "Pokaz Drzewo"));
        treeButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.menuViewTreeClicked();
            }
        });
        calcButton.setToolTipText("Oblicz...");
        calcButton.setIcon(new ImageIcon(getClass().getResource("images/calcIcon.png"), "Oblicz drzewo decyzyjne"));
        calcButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.menuRunCalcClicked();
            }      
        });
        infoButton.setToolTipText("Informacje o Danych Uczących");
        infoButton.setIcon(new ImageIcon(getClass().getResource("images/infoIcon.png"), "Informacje o danych"));
        infoButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.menuViewInfoClicked();
            }      
        });
        exitButton.setToolTipText("Wyjdź z Programu");
        exitButton.setIcon(new ImageIcon(getClass().getResource("images/exitIcon.png"), "Wyjście z programu"));
        exitButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                controler.exitButtonClicked();
            }        
        });
    }   
    /**
     * Tworzy stopkę.
     */
    private void createBottomPanel() 
    {
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        bottomPanel.setHorizontalAlignment(SwingConstants.CENTER);  
        bottomPanel.setText("Stanisław Florczyk - Wykorzystanie struktury drzewa decyzyjnego typu C4.5 w procesach klasyfikacji - 2016r.");
    }     
    /**
     * Tworzy menu główne.
     */
    private void createMenu() 
    {
        createMenuFile();
        createMenuEdit();
        createMenuView();
        createMenuRun();
        createMenuSettings();
        createMenuHelp();
    }
    /**
     * Stworzenie Menu Plik.
     */
    private void createMenuFile()
    {
        JMenu menu = new JMenu("Plik");
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Nowy Projekt");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileNewClicked(0);
            }
        });
        menuItem.setToolTipText("Resetowanie Danych Aplikacji");
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Wczytaj Zbiór Uczący...");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileLoadTrainnigSetClicked();
            }
        });
        menuItem.setToolTipText("Wczytanie Zbioru Uczącego z Pliku");
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Wczytaj Zbiór Testowy...");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileLoadTestSetButtonClicked();
            }
        });
        menuItem.setToolTipText("Wczytanie Zbioru Testowego z Pliku");
        menu.add(menuItem);
        menu.addSeparator();
        
        JMenu subMenu = new JMenu("Zapisz..."); 
        
        menuItem = new JMenuItem();
        menuItem.setText("Zapisz przestrzeń roboczą");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileSaveTextClicked();
            }
        });
        menuItem.setToolTipText("Zapisywanie Przestrzeni Roboczej do Pliku");
        subMenu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Zapisz model");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileSaveModelClicked();
            }
        });
        menuItem.setToolTipText("Zapisywanie Modelu Drzewa Decyzyjnego do Pliku");
        subMenu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Zapisz reprezentacje regułową");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileSaveRulesClicked();
            }
        });
        menuItem.setToolTipText("Zapisywanie Reprezentacji Regułowej Drzewa Decyzyjnego do Pliku");
        subMenu.add(menuItem);
          
        menu.add(subMenu);

        menuItem = new JMenuItem();
        menuItem.setText("Wczytaj Drzewo Decyzyjne");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuFileLoadModelClicked();
            } 
        });
        menuItem.setToolTipText("Wczytuje wcześniej przygotowane drzewo decyzyjne");
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem();
        menuItem.setText("Wyjście");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });
        menuItem.setToolTipText("Wyjdź z Programu");
        menu.add(menuItem);
        
        menuBar.add(menu);
    }
    /**
     * Stworzenie Menu Edycja.
     */
    private void createMenuEdit()
    {   
        JMenu menu = new JMenu("Edycja");
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Wyczyść Przestrzeń Roboczą");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuEditClearClicked();
            }    
        });
        menuItem.setToolTipText("Czyszczenie Przestrzeni Roboczej");
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Wyczyść Zbiór Testowy");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuEditClearTestSetClicked();
            }    
        });
        menuItem.setToolTipText("Czyszczenie Przestrzeni Roboczej");
        menu.add(menuItem);
        menuBar.add(menu);   
    }
    /**
     * Stworzenie Menu Widok.
     */
    private void createMenuView()
    {
        JMenu menu = new JMenu("Widok");
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Dane Uczące");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuViewTrainingSetDataClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Dane Tabelaryczne"); 
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Dane Testowe");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuViewTestSetDataClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Dane Tabelaryczne"); 
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Dane Testowe + Wyniki Klasyfikacji");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuViewTestSetClassificationDataClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Dane Tabelaryczne"); 
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Informacje o pliku");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuViewInfoClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Dane Tabelaryczne"); 
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Reguły");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuViewRulesClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Reprezentacje Regułową Drzewa Decyzyjnego");
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Drzewo");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuViewTreeClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Drzewo Decyzyjne");
        menu.add(menuItem);
        menuBar.add(menu);  
    }
    /**
     * Stworzenie Menu Uruchom.
     */
    private void createMenuRun()
    {
        JMenu menu = new JMenu("Uruchom");
        JMenuItem menuItem = new JMenuItem(); 
        menuItem.setText("Zbuduj Drzewo");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuRunCalcClicked();
            }    
        });
        menuItem.setToolTipText("Oblicza Drzewo Decyzyjne"); 
        menu.add(menuItem);
        
        menuItem = new JMenuItem();
        menuItem.setText("Klasyfikuj Dane");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuRunClassificationClicked();
            }    
        });
        menuItem.setToolTipText("Oblicza Drzewo Decyzyjne"); 
        menu.add(menuItem);
        menuItem = new JMenuItem();
        menuItem.setText("Oblicz metryki");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuRunStatsClicked();
            }    
        });
        menuItem.setToolTipText("Oblicza wskaźniki analizy jakości"); 
        menu.add(menuItem);
        menuBar.add(menu);
    }
    /**
     * Stworzenie Menu Ustawienia.
     */
    private void createMenuSettings()
    {
        JMenu menu = new JMenu("Ustawienia");
        JCheckBoxMenuItem menuItemC = new JCheckBoxMenuItem("Panel Sterowania");
        menuItemC.setToolTipText("Pokazuje/Ukrywa Panel Sterowania"); 
        menuItemC.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(((JCheckBoxMenuItem) e.getSource()).getState())
                {
                    loadButton.setVisible(true);
                    dataButton.setVisible(true);
                    ruleButton.setVisible(true);
                    treeButton.setVisible(true);
                    calcButton.setVisible(true);
                    infoButton.setVisible(true);
                    exitButton.setVisible(true);
                }else
                {
                    loadButton.setVisible(false);
                    dataButton.setVisible(false);
                    ruleButton.setVisible(false);
                    treeButton.setVisible(false);
                    calcButton.setVisible(false);
                    infoButton.setVisible(false);
                    exitButton.setVisible(false);
                }
            }    
        });
        menu.add(menuItemC);
        
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Parametry Drzewa");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuSettingsParameterButtonClicked();
            }    
        });
        menuItem.setToolTipText("Edytuj sposób obliczania drzewa"); 
        menu.add(menuItem);
        menuBar.add(menu);
    }
    /**
     * Stworzenie Menu Pomoc.
     */
    private void createMenuHelp()
    {
        JMenu menu = new JMenu("Pomoc");
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Informacje o Programie");
        menuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                controler.menuHelpInfoClicked();
            }    
        });
        menuItem.setToolTipText("Pokazuje Informacje o Programie"); 
        menu.add(menuItem); 
        menuBar.add(menu);
        
        setJMenuBar(menuBar);
    }
}
