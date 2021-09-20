/*
 * Copyright 2021 Stanisław Florczyk
 */
package controler;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import model.MeasuresEnum;
import model.Model;
import model.QualityAnalysisBuilder;
import model.TreeData;
import view.ViewTreeVisualization;
import view.View;
import view.ViewConfig;

/**
 *
 * @author Stasio
 */
public class ControlerMainFrame extends Controler
{
    private View view;
    
    public ControlerMainFrame(Model m, View v)
    {
        model = m;
        view = v;
    } 
    /**
     * Tworzy nowy projekt - resetuje aplikacje.
     */
    public void menuFileNewClicked(final int mode)
    {
        view.setEnabled(false);
        JFrame warningWindow = new JFrame();
        warningWindow.setLayout(null);
        warningWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        warningWindow.setLocationRelativeTo(view);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
        warningWindow.setVisible(true);
        warningWindow.setTitle("Ostrzeżenie");
        warningWindow.setSize(400, 120);
        warningWindow.setResizable(false);
        warningWindow.setLocation(dim.width/2-200, dim.height/2-60);
        
        JLabel info  = new JLabel();
        info.setBounds(0, 0, 400, 50);
        info.setText("Czy na pewno chcesz rozpocząć nowy projekt");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setHorizontalTextPosition(SwingConstants.CENTER);
        warningWindow.add(info);
        
        JButton ok = new JButton("OK");
        ok.setBounds(50, 50, 100, 30);
        ok.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.clearWorkSpace();
                model.clearModel();
                view.setEnabled(true);
                ((JFrame)(((JButton)e.getSource()).getParent().getParent().getParent().getParent())).dispose();
                view.repaint();
                switch(mode)
                {
                    case 0:
                        break;
                    case 1:
                        new FileChooserLoadTrainingSet(model);
                        break;
                    case 2:
                        new FileChooserLoadModel(model);
                    default:
                        break;
                }
            }
        });
        warningWindow.add(ok);
        JButton cancel = new JButton("Anuluj");
        cancel.setBounds(250, 50, 100, 30);
        cancel.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.setEnabled(true);
                ((JFrame)(((JButton)e.getSource()).getParent().getParent().getParent().getParent())).dispose();    
            }
        });
        warningWindow.add(cancel);
        warningWindow.repaint();
    }
    /**
     * Uruchamia moduł wczytywania danych
     */
    public void menuFileLoadTrainnigSetClicked()
    {
        if(model.trainingSet != null || model.root != null)
        {
            menuFileNewClicked(1);
        }else
        {
            new FileChooserLoadTrainingSet(model);
        } 
    }  
    public void menuFileLoadTestSetButtonClicked()
    {
        if(model.testSet != null)
        {
            view.setEnabled(false);
            JFrame warningWindow = new JFrame();
            warningWindow.setLayout(null);
            warningWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            warningWindow.setLocationRelativeTo(view);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
            warningWindow.setVisible(true);
            warningWindow.setTitle("Ostrzeżenie");
            warningWindow.setSize(400, 120);
            warningWindow.setResizable(false);
            warningWindow.setLocation(dim.width/2-200, dim.height/2-60);

            JLabel info  = new JLabel();
            info.setBounds(0, 0, 400, 50);
            info.setText("Zbior Testowy istnieje czy napewno chcesz wczytać nowy?");
            info.setHorizontalAlignment(SwingConstants.CENTER);
            info.setHorizontalTextPosition(SwingConstants.CENTER);
            warningWindow.add(info);

            JButton ok = new JButton("OK");
            ok.setBounds(50, 50, 100, 30);
            ok.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    new FileChooserLoadTestSet(model);
                }
            });
            warningWindow.add(ok);
            JButton cancel = new JButton("Anuluj");
            cancel.setBounds(250, 50, 100, 30);
            cancel.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    view.setEnabled(true);
                    ((JFrame)(((JButton)e.getSource()).getParent().getParent().getParent().getParent())).dispose();    
                }
            });
            warningWindow.add(cancel);
            warningWindow.repaint();
        }else
        {
            new FileChooserLoadTestSet(model);
        }
    }
    /**
     * Funkcja zapisująca model drzewa decyzyjnego do pliku.
     */
    public void menuFileSaveModelClicked()
    {
        if(model.root != null)
        {     
            new FileChooserSaveModel(model.root);
        }else
        {
            view.warrningWindow("Drzewo decyzyjne nie jest obliczone");
        }    
    }
    public void menuFileSaveTextClicked()
    {
        if(!view.infoArea.getText().isEmpty())
        {
            new FileChooserSaveText(view.infoArea.getText());
        }else
        {
            view.warrningWindow("Nie nic do zapisania - Przestrzeń robocza jest pusta");
        }
    }
    /**
     * Funkcja zapisująca reprezentacje regułową do pliku.
     */
    public void menuFileSaveRulesClicked()
    {
        if(model.root != null)
        {     
            new FileChooserSaveRules(model.root);
        }else
        {
            view.warrningWindow("Drzewo decyzyjne nie jest obliczone");
        }    
    }
    /**
     * Funkcja wczytująca drzewo decyzyjne z pliku.
     */
    public void menuFileLoadModelClicked()
    {
        if(model.trainingSet != null || model.root != null)
        {
            menuFileNewClicked(2);
        } else
        {
            new FileChooserLoadModel(model);
        }
    }
    /**
     * Czyści przestrzeń roboczą.
     */
    public void menuEditClearClicked() 
    {
        view.clearWorkSpace();
    }
    public void menuEditClearTestSetClicked()
    {
        model.testSet = null;
    }
    public void menuViewTrainingSetDataClicked()
    {
        if(model.trainingSet != null)
        {     
            setTabSize();
            view.infoArea.append("\nDane uczące: \n\n");
            view.infoArea.repaint();
            for(int i = 0; i < model.trainingSet.headlines.size(); i++)
            {
                view.infoArea.append("\t" + model.trainingSet.headlines.get(i));
            }
            view.infoArea.append("\n\n");
            view.infoArea.repaint();
            for(int i = 0; i < model.trainingSet.table.get(0).size(); i++)
            {
                for(ArrayList a : model.trainingSet.table)
                {
                    view.infoArea.append("\t" + a.get(i));
                }
                view.infoArea.append("\n");
                if(i > 10000)
                {
                    view.infoArea.append("\t...więcej niż 10 000 rekordów." );
                    break;
                }
            }            
            view.repaint();
        }else
        {
            if(model.root == null)
            {
                view.warrningWindow("Nie wczytano danych lub wczytane dane są niepoprawne.");
            }else
            {
                view.warrningWindow("Nie można wyświetlić danych tabelarycznych. \nDrzewo zostało stworzone za pomocą modelu.");
            }        
        }  
    }
    public void menuViewTestSetDataClicked()
    {
        if(model.testSet != null)
        {     
            setTabSize();
            view.infoArea.append("\nDane Testowe: \n\n");
            view.infoArea.repaint();
            for(int i = 0; i < model.testSet.headlines.size(); i++)
            {
                view.infoArea.append("\t" + model.testSet.headlines.get(i));
            }
            view.infoArea.append("\n\n");
            view.infoArea.repaint();
            for(int i = 0; i < model.testSet.table.get(0).size(); i++)
            {
                for(ArrayList a : model.testSet.table)
                {
                    view.infoArea.append("\t" + a.get(i));
                }
                view.infoArea.append("\n");
                if(i > 10000)
                {
                    view.infoArea.append("\t...więcej niż 10 000 rekordów." );
                    break;
                }
            }            
            view.repaint();
        }else
        {
            if(model.root == null)
            {
                view.warrningWindow("Nie wczytano danych lub wczytane dane są niepoprawne.");
            }else
            {
                view.warrningWindow("Nie można wyświetlić danych tabelarycznych. \nDrzewo zostało stworzone za pomocą modelu.");
            }        
        }  
    }
    
    public void menuViewTestSetClassificationDataClicked()
    {
        if(model.testSet != null)
        {     
            setTabSize();
            view.infoArea.append("\nDane Testowe: \n\n");
            view.infoArea.repaint();
            for(int i = 0; i < model.testSet.headlines.size(); i++)
            {
                view.infoArea.append("\t" + model.testSet.headlines.get(i));
            }
            view.infoArea.append("\tWynik klasyfikacji");
            view.infoArea.append("\n\n");
            view.infoArea.repaint();
            for(int i = 0; i < model.testSet.table.get(0).size(); i++)
            {
                for(ArrayList a : model.testSet.table)
                {
                    view.infoArea.append("\t" + a.get(i));
                }
                view.infoArea.append("\t" + model.classificate(i));
                view.infoArea.append("\n");
                if(i > 10000)
                {
                    view.infoArea.append("\t...więcej niż 10 000 rekordów." );
                    break;
                }
            }            
            view.repaint();
        }else
        {
            if(model.root == null)
            {
                view.warrningWindow("Nie wczytano danych lub wczytane dane są niepoprawne.");
            }else
            {
                view.warrningWindow("Nie można wyświetlić danych tabelarycznych. \nDrzewo zostało stworzone za pomocą modelu.");
            }        
        }  
    }
    /**
     * Uruchamia moduł wyswietlenia informacji o danych uczących.
     */
    public void menuViewInfoClicked() 
    {
        if(model.trainingSet!= null)
        {    
            setTabSize();
            view.infoArea.append("\nNagłówki:\n\n");
            for(int i = 0; i < model.trainingSet.headlines.size()-1; i++)
            {
                view.infoArea.append(model.trainingSet.headlines.get(i) + ":\t Cecha ");
                if(model.trainingSet.types.get(i))
                {
                    view.infoArea.append("Ilościowa.\n");
                }else
                {
                    view.infoArea.append("Jakościowa.\n");
                }
            }
            view.infoArea.append(model.trainingSet.headlines.get(model.trainingSet.headlines.size()-1) + ":\t Klasa.");
            
            view.infoArea.append("\n\n\nMożliwe Wartości Cech Jakościowych:\n\n");
            for(int i = 0; i<model.trainingSet.headlines.size();i++)
            {
                if(!model.trainingSet.types.get(i))
                {
                    view.infoArea.append(model.trainingSet.headlines.get(i) + ":\t");
                    for(int j = 0; j < model.trainingSet.uniqueValues.get(i).size(); j++)
                    {
                        view.infoArea.append(model.trainingSet.uniqueValues.get(i).get(j) + ";    ");
                    }   
                        view.infoArea.append("\n");
                }   
            }
            view.repaint();
        }else
        {
            if(model.root == null)
            {
                view.warrningWindow("Nie wczytano danych lub wczytane dane są niepoprawne.");
            }else
            {
                view.warrningWindow("Nie można wyświetlić informacji o pliku danych uczących. \nDrzewo zostało stworzone za pomocą modelu.");
            }
        }         
    }
    /**
     * Uruchamia moduł wyświetlania reprezentacji regułowej drzewa decyzyjnego.
     */
    public void menuViewRulesClicked()
    {
        if(model.root != null)
        {     
            view.infoArea.append(model.root.getRules());
            view.repaint();
        }else
        {
            view.warrningWindow("Drzewo decyzyjne nie jest obliczone");
        }    
    }
    /**
     * Uruchamia moduł wyświetlania reprezentacji graficznej drzewa decyzyjnego.
     */
    public void menuViewTreeClicked()
    {
        if(model.root != null)
        {     
            new ViewTreeVisualization(model);
        }else
        {
            view.warrningWindow("Drzewo decyzyjne nie jest obliczone"); 
        }     
    }
    /**
     * Uruchamia moduł obliczania modelu drzewa decyzyjnego.
     */
    public void menuRunCalcClicked() 
    {
        if(model.trainingSet != null)
        {    
            view.infoArea.append("\nRozpoczęcie Obliczeń: \n\n ");
            view.infoArea.repaint();
            model.root = new TreeData(model);
            view.infoArea.append("\nDrzewo decyzyjne obliczone poprawnie: \n\n ");
            view.repaint();
        }else
        {
            if(model.root != null)
            {
                view.warrningWindow("Model został wczytany z pliku.");
            }else
            {
                view.warrningWindow("Nie wczytano danych lub wczytane dane są niepoprawne.");
            }     
        }       
    }
    public void menuRunStatsClicked()
    {
        if(model.root != null)
        {    
            if(model.testSet != null)
            {
                view.infoArea.append("\nObliczanie Metryk Modelu: \n\n");
                view.infoArea.repaint();  
                view.infoArea.setTabSize(20);
                QualityAnalysisBuilder qa = new QualityAnalysisBuilder(model);
                view.infoArea.append("-------------------------------------\n");
                view.infoArea.append("Macierz Pomyłek:\n\n");
                view.infoArea.append(qa.drawConfusionMatrix() + "**********\n");
                view.infoArea.append("Ogólne metryki modelu:\n\n");
                view.infoArea.append("(ACC)  Dokładność:\t" + qa.getAccuracy() +"\n\n");
                view.infoArea.append("(PV)   Częstość:\n");
                for(int i = 0; i < qa.getSize(); i++)
                {
                    view.infoArea.append("\t" + 
                            model.testSet.uniqueValues.get(model.testSet.uniqueValues.size()-1).get(i) +
                            ":\t" + qa.getPrevalence(i) + "\n");
                }
                view.infoArea.append("\n**********\n");
                view.infoArea.append("Miary dotyczące poszczególnych klas - kolejno, każda jest uważana za klase normalną\n\n");
                for(int i = 0; i < qa.getSize(); i++)
                {
                    view.infoArea.append( "\n\n\t" + model.testSet.uniqueValues.get(model.testSet.uniqueValues.size()-1).get(i) + "\n\n");
                    view.infoArea.append( "\t\t(ACC)  Dokładność:\t" + qa.getAccuracy(i) + "\n");
                    view.infoArea.append( "\t\t(PPV)  Precyzja:\t" + qa.getPrecision(i) + "\n");
                    view.infoArea.append( "\t\t(NPV)  Negatywna Precyzja:\t" + qa.getNegativePrecision(i)+ "\n");
                    view.infoArea.append( "\t\t(TPR)  Czułość:\t" + qa.getSensitivity(i)+ "\n");
                    view.infoArea.append( "\t\t(SPC)  Specyficzność:\t" + qa.getSpecificity(i)+ "\n\n"); 
                    view.infoArea.append( "\t\t(F-Score)  Punktacja F:\t" + qa.getFScore(i)+ "\n"); 
                    view.infoArea.append( "\t\t(MCC)  Współczynnik Mathews:\t" + qa.getMatthews(i)+ "\n\n"); 
                }
                view.infoArea.append("Uśrednione wyniki:\n\n");
                view.infoArea.append( "(ACC)  Dokładność:\t" + qa.getAverageMeasure(MeasuresEnum.ACCURACY) + "\n");
                view.infoArea.append( "(PPV)  Precyzja:\t" + qa.getAverageMeasure(MeasuresEnum.PRECISION) + "\n");
                view.infoArea.append( "(NPV)  Negatywna Precyzja:\t" + qa.getAverageMeasure(MeasuresEnum.NEGATIVE_PRECISION)+ "\n");
                view.infoArea.append( "(TPR)  Czułość:\t" + qa.getAverageMeasure(MeasuresEnum.SENSITIVITY)+ "\n"); 
                view.infoArea.append( "(SPC)  Specyficzność:\t" + qa.getAverageMeasure(MeasuresEnum.SPECIFICITY)+ "\n\n");
                view.infoArea.append( "(F-Score)  Punktacja F:\t" + qa.getAverageMeasure(MeasuresEnum.F_SCORE)+ "\n"); 
                view.infoArea.append( "(MCC)  Współczynnik Mathews:\t" + qa.getAverageMeasure(MeasuresEnum.MATTHEWS)+ "\n\n"); 
                view.repaint();
            }else
            {
                view.warrningWindow("Do analizy jakości wymagany jest zbiór testowy");
            }
        }else
        {
            view.warrningWindow("Należy najpierw obliczyć drzewo decyzyjne");    
        }    
    }
    /**
     * Funkcja służąca doklasyfikacji danych.
     */
    public void menuRunClassificationClicked()
    {
        if(model.root != null)
        {            
            ControlerClassification c = new ControlerClassification(model.root,  view.infoArea);   
            view.repaint();
        }else
        {
            view.warrningWindow("Drzewo decyzyjne nie jest obliczone");     
        }
    }
    /**
     * Wyswietla informacje o aplikacji.
     */
    public void menuSettingsParameterButtonClicked()
    {
        ControlerConfig cc = new ControlerConfig(model);
        ViewConfig vp = new ViewConfig(view.infoArea, cc);
        cc.setView(vp);
        vp.createAndShow();
    }
    public void menuHelpInfoClicked() 
    {
        view.infoArea.setText("Apikacja służąca do budowy i pracy z drzewami decyzyjnymi typu C4.5 \n"
                + "Autor: Stanisław Florczyk \n"
                + "Praca dyplomowa magisterska na kierunku  Informatyka\n"
                + "Opiekun pracy dyplomowej dr inż. Grzegorz Słoń\n"
                + "Kielce, 2016");
        view.repaint();
    }
    /**
     * Ustawienie długości akapitów pod wzgledem danych wejściowych
     */
    private void setTabSize()
    {
        if(model.trainingSet!= null)
        {   
            int tab = 0;
            for(String s : model.trainingSet.headlines)
            {
                if(s.length() > tab)
                {
                    tab = s.length();
                }
            }
            view.infoArea.setTabSize(tab*3/4);
        }
    }
    /**
     * Wychodzi z programu.
     */
    public void exitButtonClicked() 
    {
        System.exit(0);
    }
}
