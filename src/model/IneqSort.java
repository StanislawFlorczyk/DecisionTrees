/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

/**
 * Klasa służaca do sortowania atrybutów ilościowych.
 * @author Stasio
 */
public class IneqSort implements Comparable<IneqSort>
{
    /**
     * Dana
     */
    Double data;    
    /**
     * Klasa
     */
    String category;        //Klasa
    /**
     * Tworzy obiekt.
     * @param d dana.
     * @param s klasa.
     */
    public IneqSort(double d, String s)
    {
        data = new Double(d);
        category = s; 
    }
    @Override
    public int compareTo(IneqSort o) //Funkcja porównująca
    {
        return data.compareTo(o.data);
    }
}
