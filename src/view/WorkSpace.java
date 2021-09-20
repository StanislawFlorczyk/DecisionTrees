/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JSlider;
import model.TreeConfig;
import model.TreeData;

/**
 * Panel do przedstawienia graficznej reprezentacji drzewa decyzyjnego.
 * @author Stasio
 */
public class WorkSpace extends JPanel
{
    /**
     * Szerokość Panelu.
     */
    public int width;
    /**
     * Wysokość panelu.
     */
    public int height;
    /**
     *  Kolor Węzłów.
     */
    private Color node;
    /**
     * Kolor Liści.
     */
    private Color category;
    /**
     * Czcionka.
     */
    private Font font;
    /**
     * Suwak rozmiaru z klasy Tree. 
     */
    private JSlider size; 
    /**
     * Suwak skali z klasy Tree. 
     */ 
    private JSlider scale;
    /**
     * Parametry Modelu
     */
    private TreeConfig parameters;   
    /**
     * Korzeń drzewa.
     */
    TreeData root;
 
    /**
     * Domyślny konstruktor tworzący pusty panel.
     */
    public WorkSpace()
    {
        super(); 
        setPreferredSize(new Dimension(width = 500,height = 500));
        root = null;
    }
    /**
     * Konstruktor wywyoływany przy rysowaniu drzewa.
     * @param td dane drzewa.
     * @param sc skala elemetów.
     * @param si wielkość drzewa decyzyjnego.
     */
    public WorkSpace(TreeData td, JSlider sc, JSlider si, TreeConfig tc)
    {
        super();
        root = td;
        scale = sc;
        size = si;
        parameters = tc;
        node = new Color(100, 150, 200);
        category = new Color(100, 200, 200);
        setPreferredSize(new Dimension(width = 500,height = 100));  
    }
    @Override
    public void paintComponent(Graphics g)
    {
        if(root != null)
        {
            super.paintComponent(g);
            font = new Font("Times New Roman", Font.PLAIN, scale.getValue());
            width = size.getValue() * root.leafs * 10;
            height = scale.getValue() * root.getHeight() * 9;
            setPreferredSize(new Dimension(width, height));
            g.clearRect(0, 0, width, height);
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);

            g.setColor(node);
            g.fillRect(scale.getValue(), scale.getValue(), scale.getValue(), scale.getValue());
            g.setColor(Color.black);
            g.drawString("- Węzły", 2*scale.getValue() + 5, scale.getValue() + (int) (0.75*scale.getValue()));
            g.setColor(category);
            g.fillRect(scale.getValue(), 3*scale.getValue(), scale.getValue(), scale.getValue());
            g.setColor(Color.black);
            g.drawString("- Klasy", 2*scale.getValue() + 5, 3*scale.getValue() + (int) (0.75*scale.getValue()));
            g.setFont(font);
            g.drawString("Drzewo Obliczono z dokładnością :" + parameters.accuracy.toString(), 2*scale.getValue() + 5, 5*scale.getValue() + (int) (0.75*scale.getValue()));
            drawBlock(g, root,(int) (width/2), scale.getValue(), width); 
        }else
        {
            super.paintComponent(g);
            g.clearRect(0, 0, width, height);
            g.setColor(Color.white);
        }
    }
    /**
     * Rekurencyjna funkcja rysująca pojedyńczy element drzewa decyzyjnego.
     * @param gr obiekt Graphics
     * @param r obiekt węzła.
     * @param w współżedna x.
     * @param h wspóżedna y.
     * @param spaceW dostępna przestrzeń.
     */
    private void drawBlock(Graphics gr, TreeData r, int w, int h, int spaceW) 
    {
        if(r.isLeaf)
        {
            gr.setColor(category);
            gr.fillRect((int)(w-2.5*scale.getValue()), h, 5*scale.getValue(), 5*scale.getValue());
            gr.setColor(Color.black);
            gr.drawString(r.category, w-2*scale.getValue(), h+3*scale.getValue());
        }else
        {
            gr.setColor(node);
            gr.fillOval((int)(w-4.5*scale.getValue()), h, 9*scale.getValue(), 5*scale.getValue());
            gr.setColor(Color.black);
            gr.drawString(r.category.toUpperCase(), w-3*scale.getValue(), h+3*scale.getValue());
            double distance = spaceW / r.branches.size();
            for(int i = 0; i < r.branches.size(); i++)
            {
                gr.drawLine(w, h+5*scale.getValue(), (int)((w-spaceW/2)+((i*distance+(i+1)*distance)/2)),h+9*scale.getValue());
                gr.drawString(r.branches.get(i).up, (int)(((w-spaceW/2)+((i*distance+(i+1)*distance)/2))-3*scale.getValue()), h+8*scale.getValue());
                drawBlock(gr, r.branches.get(i),(int)((w-spaceW/2)+((i*distance+(i+1)*distance)/2)),h+9*scale.getValue(),(int)distance); 
            }
        }
    }
    public void drawTree(Graphics g)
    {
        g.clearRect(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        g.setColor(node);
        g.fillRect(scale.getValue(), scale.getValue(), scale.getValue(), scale.getValue());
        g.setColor(Color.black);
        g.drawString("- Węzły", 2*scale.getValue() + 5, scale.getValue() + (int) (0.75*scale.getValue()));
        g.setColor(category);
        g.fillRect(scale.getValue(), 3*scale.getValue(), scale.getValue(), scale.getValue());
        g.setColor(Color.black);
        g.drawString("- Klasy", 2*scale.getValue() + 5, 3*scale.getValue() + (int) (0.75*scale.getValue()));
        g.setFont(font);
        drawBlock(g, root,(int) (width/2), scale.getValue(), width); 
    }
    public void clear()
    {
        Graphics g  = this.getGraphics();
        g.clearRect(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        this.paint(g);
    }
}
