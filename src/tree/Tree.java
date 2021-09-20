/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import model.Model;
import view.View;
import view.ViewMainFrame;



/**
 *
 * @author Stasio
 */
public class Tree 
{                                                     
    private View view;  
    private Model model;
    public Tree(int w, int h)
    {    
        model = new Model();
        view = new ViewMainFrame(model, w,h);
        view.createAndShow();
    }  
    public static void main(String[] args) 
    {
        new Tree(1200,800);
    }   
}
