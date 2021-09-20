/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import view.ViewClassification;

/**
 *
 * @author Stasio
 */
public class Classification
{
    public TreeData node;
    public JTextArea textA; 
    
    private ViewClassification view;

    public Classification(TreeData n, ViewClassification vc, JTextArea ta)
    {
        node = n; 
        view = vc;
        view.setSize(node.category.length() * 8 + 500 + 20, 100);
        textA = ta;
        if(node.isLeaf)
        {
            textA.append("Wszystkie dane należą do klasy:\t");
            textA.append(node.category + "\n");
            return;
        }
        
        textA.append("\nJeżeli " + node.category + " = ");
        
        doClassificate();
    }
    public final void doClassificate()
    { 
        if(node.isLeaf)
        {
            textA.append(" to \tObiekt należy do klasy:\t\t" + node.category);
            view.dispose();
            return;
        }   
        view.info.setSize(node.category.length() * 8 + 50, 50);
        view.info.setText("Podaj Atrybut:    \t" + node.category);
        
        if(node.testType)
        {
            JTextField tf = new JTextField();
            tf.setPreferredSize(new Dimension(200, 50));
            view.input = tf;
        }else
        {
            JComboBox<String> cb = new JComboBox<>();
            cb.setPreferredSize(new Dimension(200, 50));
            cb.setEditable(false);
            for(Object o : node.testUniquesValues)
            {
                cb.addItem((String)o);
            }
            view.input = cb;
        }
        view.addInput();
        
        view.createAndShow();
        view.repaint();
    }
}
