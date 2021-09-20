/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

/**
 *
 * @author Stasio
 */
public class ConfusionMatrix
{
    private Integer[][] array;
    public Integer size;
    ConfusionMatrix(Integer s)
    {
        size = s;
        array = new Integer[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                array[i][j] = 0;
            }
        }
    }  
    @Override
    public String toString()
    {
        String tmp = "";
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                tmp = tmp.concat(array[i][j] + "\t");
            }
            tmp = tmp.concat("\n");
        }
        return tmp;
    }
    public Integer get(Integer i, Integer j)
    {
        return array[i][j];
    }
    public Integer getTP(Integer index)
    {
        return array[index][index];
    }
    public Integer getTN(Integer index)
    {
        Integer tmp = 0;
        for(int i = 0; i < size; i++)
        {
            if(i != index)
            {
                for(int j = 0; j < size; j++)
                {
                    if(j != index)
                    {
                        tmp += array[i][j];
                    } 
                }
            }
        }
        return tmp;
    }
    public Integer getFN(Integer index)
    {
        Integer tmp = 0;
        for(int j = 0; j < size; j++)
        {
            if(j != index)
            {
                tmp += array[index][j];
            }
        }
        return tmp;
    }
    public Integer getFP(Integer index)
    {
        Integer tmp = 0;
        for(int i = 0; i < size; i++)
        {
            if(i != index)
            {
                tmp += array[i][index];
            }
        }
        return tmp;
    }
    
    public Integer getOverallTrue()
    {
        Integer tmp = 0;
        for(int i = 0; i < size; i++)
        {
            tmp += array[i][i];
        }
        return tmp;
    }
    public Integer getOveralFalse()
    {
        Integer tmp = 0;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(i!=j)
                {
                    tmp += array[i][j];
                }
            }
        }
        return tmp;
    }
    public Integer getOverallFalseNegative()
    {
        Integer tmp = 0;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(j > i)
                {
                    tmp += array[i][j];
                }
            }
        }
        return tmp;
    }
    public Integer getOverallFalsePositive()
    {
        Integer tmp = 0;
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(i > j)
                {
                    tmp += array[i][j];
                }
            }
        }
        return tmp;
    }

    public void bump(Integer i)
    {
        array[i][i]++;
    }
    public void bump(Integer i, Integer j)
    {
        if(i != -1 && j != -1)
        {
            array[i][j]++;
        }
    }
}
