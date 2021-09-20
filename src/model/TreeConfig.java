/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Stasio
 */
public class TreeConfig
{
    public Integer accuracy;
    public ArrayList<String> skippedTests = new ArrayList<>();
    public TreeConfig()
    {
        accuracy = 100;
    }
}
