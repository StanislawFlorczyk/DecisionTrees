/*
 * Copyright 2021 Stanisław Florczyk
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
