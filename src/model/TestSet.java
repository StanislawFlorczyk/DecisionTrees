/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

import controler.exceptions.EmptyFile;
import controler.exceptions.WrongFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Stasio
 */
public class TestSet extends LoadedData
{
    
    public TestSet(File f) throws FileNotFoundException, IOException, EmptyFile, WrongFormat
    {
        super(f);
    }
    public TestSet(LoadedData trainingSet, Integer value)
    {
        super();
        fileName = trainingSet.fileName;
        headlines = (ArrayList<String>)trainingSet.headlines.clone();
        types = (ArrayList<Boolean>)trainingSet.types.clone();
        uniqueValues = (ArrayList<ArrayList>)trainingSet.uniqueValues.clone();
        for(String s: headlines)
        {
            table.add(new ArrayList());
        }
        //Losowe przeniesienie częsci zbioru uczącego do zbioru testowego
        //value - wielkość zbioru w % zbioru uczącego
        addToTestSet(trainingSet, value);
    }
    /**
     * Losowe przeniesienie częsci zbioru uczącego do zbioru testowego
     * @param trainingSet zbiór uczący
     * @param value częśc zbioru (w %)
     */
    public final void addToTestSet(LoadedData trainingSet, Integer value)
    {
        Random gen = new Random();
        Integer s = trainingSet.table.get(0).size();
        s = (value * s) / 100;
        for (int i = 0; i < s; i++)
        {       
            int index = gen.nextInt(trainingSet.table.get(0).size());
            for(int j = 0; j < trainingSet.table.size(); j++)
            {
                table.get(j).add(trainingSet.table.get(j).remove(index));
            }
        }
    }
}
