/*
 * Copyright 2021 Stanisław Florczyk
 */
package controler;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Classification;
import model.TreeData;
import view.ViewClassification;

/**
 *
 * @author Stasio
 */
public class ControlerClassification
{
    private ViewClassification view;
    private Classification model;
    
    
    public ControlerClassification(TreeData root, JTextArea info)
    {
        view = new ViewClassification(this,info);
        model  = new Classification(root, view, info); 
    }
    public void okButtonClicked()
    {
        if(model.node.testType)
        {
            JTextField tf = (JTextField) view.input;
            String tmp = tf.getText();
            tmp = tmp.replace(",", ".");
            Double d;
            try
            {
                d = Double.parseDouble(tmp);
            }catch(NumberFormatException ex)
            {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                view.warrningWindow("Podaj Liczbę",dim.width/2 - 50, dim.height/2 + 200);
                view.input.requestFocusInWindow();
                return;
            }
            view.infoArea.append(d.toString());
            if(model.node.breakPoint >= d)
            {
                model.node = model.node.branches.get(0);
            }else
            {
                model.node = model.node.branches.get(1);
            }         
        }else
        {
            String tmp = (String)((JComboBox<String>)view.input).getSelectedItem();
            view.infoArea.append(tmp);
            for(TreeData td : model.node.branches)
            {
                if(td.up.equals(tmp))
                {
                    model.node = td;
                    break;
                }
            }       
        }
        view.removeInput();
        if(!model.node.isLeaf)
        {
            view.infoArea.append(",\t"+ model.node.category + " = ");
        }
        model.doClassificate();
    } 
    public void clearClicked()
    {
        view.infoArea.setText("");
    }
}
