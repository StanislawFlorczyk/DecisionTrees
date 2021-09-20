/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

/**
 *
 * @author Stasio
 */
public class Model
{ 
    public TrainingSet trainingSet;
    public TestSet testSet;   
    public TreeConfig parameters;
    public TreeData root;
    public Model()
    {
        clearModel();
    }
    public final void clearModel()
    {
        trainingSet = null;
        testSet = null;
        parameters = new TreeConfig();
        root = null;
    }
    public boolean checkTestSet()
    {
        if(testSet != null && trainingSet != null)
        {
            for(String s : trainingSet.headlines)
            {
                if(!testSet.headlines.contains(s))
                {
                    testSet = null;
                    return false;
                }
            } 
        }
        return true;
    }
    public String classificate(Integer testIndex)
    {
        if(root != null)
        {
            return doClassificate(root, testIndex);
        }
        return "Model nie jest obliczony";
    }
    private String doClassificate(TreeData node, Integer testIndex)
    {
        if(node.isLeaf)
        {
            return node.category;
        }
        Integer attIndex = -1;
        for(int i = 0; i < testSet.headlines.size(); i++)
        {
            if(node.category.equals(testSet.headlines.get(i)))
            {
                attIndex = i;
                break;
            }
        }
        if(node.testType)
        {
            if(node.breakPoint >= Double.parseDouble((String)testSet.table.get(attIndex).get(testIndex)))
            {
                return doClassificate(node.branches.get(0), testIndex);
            }else
            {
                return doClassificate(node.branches.get(1), testIndex);
            }
        }else
        {
            String tmp = (String)testSet.table.get(attIndex).get(testIndex);
            for(TreeData td : node.branches)
            {
                if(td.up.equals(tmp))
                {
                    return doClassificate(td, testIndex);
                }
            }    
        }
        return null;
    }
}
