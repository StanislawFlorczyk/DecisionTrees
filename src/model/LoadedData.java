/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

import controler.exceptions.WrongFormat;
import controler.exceptions.EmptyFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Stasio
 */
public class LoadedData
{ 
    protected BufferedReader br;  
    /**
     * Nazwa pliku.
     */
    public String fileName;
    /**
     * Lista nazw atrybutów.
     */
    public ArrayList<String> headlines = new ArrayList();  
    /**
     *Lista typu atrybutów w danej kolumnie.<br><br>
     * &#09 true - cecha ilościowa,<br>
     * &#09 false - cecha jakościowa.
     */
    public ArrayList<Boolean> types = new ArrayList();          
    /**
     * Dwuwumiarowa tablica wartości atrybutów. 
     */
    public ArrayList<ArrayList> table = new ArrayList();
    /**
     * Dwuwymiarowa tablica wartości unikatowych klas i wartości dla cech jakościowych.
     */
    public ArrayList<ArrayList> uniqueValues = new ArrayList();
    
    /**
     * Wczytuje dane z pliku csv sprawdza ich poprawnosć i tworzy odpowiednie struktury do dalszej obróbki.
     * @param f - plik z danymi uczącymi.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws EmptyFile
     * @throws WrongFormat 
     */
    public LoadedData(){}
    public LoadedData(File f) throws FileNotFoundException, IOException, EmptyFile, WrongFormat 
    {
        br = new BufferedReader(new FileReader(f));
        fileName = f.getName();
        fileName = fileName.substring(0, fileName.indexOf("."));
        setHedlines();          //Wczytanie nagłówków - nazwy cech
        headlinesCheck();       //Sprawdzenie poprawności Wczytanych nagłówków
        setDataTable();         //Stworzenie Listy dla każdego atrybutu 
        setData();              //Wczytywanie Danych    
        dataCheck();            //Sprawdzenie poprawności danych
        typeCheck();            //Rozpoznanie cech jakościowych i ilosciowych
        setUniqueValues();      //Wypelnianie tablicy unikatowych wartości jakościowych    
    }
    /**
     * wczytuje pierwszą linnie - nagłowki.
     * @throws IOException
     * @throws EmptyFile 
     */
    private void setHedlines() throws IOException, EmptyFile 
    {
        String tmp;
        tmp = br.readLine();
        if(tmp == null)
        {
            throw new EmptyFile();
        }
        tmp = tmp.toLowerCase();
        int i;
        while((i = tmp.indexOf(";")) != -1)
        {
            headlines.add(tmp.substring(0, i));
            tmp = tmp.substring(i+1);
        }
        headlines.add(tmp);
    }
    /**
     * Wczytuje dane do dwuwymiarowej tablicy
     * @throws IOException 
     */
    private void setData() throws IOException 
    {
        String tmp;
        int i;
        while(true)
        {
            tmp = br.readLine();
            if(tmp == null)
            {
                break;
            }  else
            {
                tmp = tmp.toLowerCase();
                tmp = tmp.replace(",", ".");
                for(int j = 0; j < headlines.size(); j++)
                {
                    if((i = tmp.indexOf(";")) != -1)
                    {
                        table.get(j).add(tmp.substring(0, i));
                        tmp = tmp.substring(i+1);
                    }else
                    {
                        table.get(j).add(tmp);
                    }
                }     
            }    
        }
    }
    /**
     * Ustawia typy poszczególnych atrybutów<br><br>
     * &#09 true - cecha ilościowa,<br>
     * &#09 false - cecha jakościowa.
     */
    private void typeCheck() 
    {
        for(ArrayList a : table)
        {
            try
            {
                for(int j = 0; j < a.size(); j++)
                {    
                    Float.parseFloat((String) a.get(j));
                }
            }catch(NumberFormatException ex)
            {
                types.add(false);
                continue;
            }
            types.add(true);
        }
        types.set(types.size()-1, false);
    }
    /**
     * Wypełnia liste unikatowych wartości cech jakościowych i klasy.
     */
    private void setUniqueValues()           
    {
        int i = 0;
        boolean exist;
        for(Boolean a : types)
        {
            if(!a)
            {
                uniqueValues.get(i).add(table.get(i).get(0));
                for(Object s : table.get(i))
                {
                    exist = false;
                    for(Object z : uniqueValues.get(i))
                    {
                        if(s.equals(z))
                        {
                            exist = true;
                            break;
                        }
                    }
                    if(!exist)
                    {
                        uniqueValues.get(i).add(s);
                    }
                }
            }
            i++;
        }
    }
    /**
     * Dodatkowe sprawdzenie poprawnosci danych.
     * @throws WrongFormat 
     */
    private void headlinesCheck() throws WrongFormat 
    {
        if(headlines.size()<2)
        {
            throw new WrongFormat();
        }
    }
    /**
     * Tworzy listy na podstawie znanej liczby atrybutów.
     */
    private void setDataTable() 
    {
        for(int j = 0; j < headlines.size(); j++)
        {
            table.add(new ArrayList());
            uniqueValues.add(new ArrayList());
        }
    }
    /**
     * Sprawdza cały wczytany zbiór pod kątem poprawności danych i brakujących wartości.
     * @throws WrongFormat 
     */
    private void dataCheck() throws WrongFormat 
    {
        for(ArrayList a : table)
        {
            for(int j = 0; j<a.size(); j++)
            {
                if(a.get(j).equals(""))
                {
                    throw new WrongFormat();
                }
            }
        }
    }
       
}
