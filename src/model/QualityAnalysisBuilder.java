/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

import java.util.ArrayList;
import static model.MeasuresEnum.ACCURACY;
import static model.MeasuresEnum.F_SCORE;
import static model.MeasuresEnum.MATTHEWS;
import static model.MeasuresEnum.NEGATIVE_PRECISION;
import static model.MeasuresEnum.PRECISION;
import static model.MeasuresEnum.SENSITIVITY;
import static model.MeasuresEnum.SPECIFICITY;

/**
 *
 * @author Stasio
 */
public class QualityAnalysisBuilder extends Model
{
    private ConfusionMatrix confusionMatrix;
    private final ArrayList<String> classes;
    
    
    public QualityAnalysisBuilder (Model m)
    {
        trainingSet = m.trainingSet;
        testSet = m.testSet;
        parameters = m.parameters;
        root = m.root;
        classes = trainingSet.uniqueValues.get(trainingSet.uniqueValues.size()-1);
        createConfusionMatrix();
    }
    public String drawConfusionMatrix()
    {
        String tmp = "\t";
        for(int i = 0; i < confusionMatrix.size; i++)
        {
            tmp = tmp.concat(classes.get(i).toString() + "\t");
        }
        for(int i = 0; i < confusionMatrix.size; i++)
        {
            tmp = tmp.concat("\n");
            tmp = tmp.concat(classes.get(i).toString() + "\t");
            for(int j = 0; j < confusionMatrix.size; j++)
            {
                tmp = tmp.concat(confusionMatrix.get(i, j).toString() + "\t");
            }
        }
        tmp = tmp.concat("\n");
        return tmp;
    }
    private void createConfusionMatrix()
    {
        confusionMatrix = new ConfusionMatrix(trainingSet.uniqueValues.get(trainingSet.uniqueValues.size()-1).size());
        for(int i = 0; i < testSet.table.get(0).size(); i++)
        {
            confusionMatrix.bump(classes.indexOf((String)testSet.table.get(testSet.table.size()-1).get(i)),classes.indexOf(classificate(i)));
        }
    }
    public Integer getSize()
    {
        return confusionMatrix.size;
    }
    public Double getAccuracy()
    {
        try
        {
            return confusionMatrix.getOverallTrue().doubleValue() / (confusionMatrix.getOverallTrue().doubleValue() + confusionMatrix.getOveralFalse().doubleValue());
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getAccuracy(Integer i)
    {
        try
        {
            return (confusionMatrix.getTP(i).doubleValue() + confusionMatrix.getTN(i).doubleValue())/(confusionMatrix.getTP(i).doubleValue() + confusionMatrix.getTN(i).doubleValue() + confusionMatrix.getFN(i).doubleValue() + confusionMatrix.getFP(i).doubleValue());
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getPrevalence(Integer i)
    {
        try
        {
            return (confusionMatrix.getTP(i).doubleValue() + confusionMatrix.getFN(i).doubleValue()) / (confusionMatrix.getTP(i).doubleValue() + confusionMatrix.getTN(i).doubleValue() + confusionMatrix.getFN(i).doubleValue() + confusionMatrix.getFP(i).doubleValue());
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getPrecision(Integer i)
    {
        try
        {
            return confusionMatrix.getTP(i).doubleValue() / (confusionMatrix.getTP(i).doubleValue() + confusionMatrix.getFP(i));
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getNegativePrecision(Integer i)
    {
        try
        {
            return confusionMatrix.getTN(i).doubleValue() / (confusionMatrix.getTN(i).doubleValue() + confusionMatrix.getFN(i));
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getSpecificity(Integer i)
    {
        try
        {
            return confusionMatrix.getTN(i).doubleValue() / (confusionMatrix.getTN(i).doubleValue() + confusionMatrix.getFP(i));
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getSensitivity (Integer i)
    {
        try
        {
            return confusionMatrix.getTP(i).doubleValue() / (confusionMatrix.getTP(i).doubleValue() + confusionMatrix.getFN(i));
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getFScore(Integer i)
    {
        try
        {
            Double precision = getPrecision(i);
            Double recal = getSensitivity(i);
            return 2.0 *  precision * recal  / (precision + recal);
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getMatthews(Integer i)
    {
        try
        {
            Double tp = confusionMatrix.getTP(i).doubleValue();
            Double tn = confusionMatrix.getTN(i).doubleValue();
            Double fp = confusionMatrix.getFP(i).doubleValue();
            Double fn = confusionMatrix.getFN(i).doubleValue();
            return (tp * tn - fp * fn) / (Math.sqrt((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn)));
        }catch(ArithmeticException e)
        {
            return -1.0;
        }
    }
    public Double getAverageMeasure(MeasuresEnum measure)
    {
        Double tmp = 0.0;
        for(int i = 0; i < confusionMatrix.size; i++)
        {
            switch(measure)
            {
                case ACCURACY:
                    tmp += getAccuracy(i);
                    break;
                case F_SCORE:
                    tmp += getFScore(i);
                    break;
                case MATTHEWS:
                    tmp += getMatthews(i);
                    break;
                case NEGATIVE_PRECISION:
                    tmp += getNegativePrecision(i);
                    break;
                case PRECISION:
                   tmp += getPrecision(i);
                    break;
                case SENSITIVITY:
                    tmp += getSensitivity(i);
                    break;
                case SPECIFICITY:
                    tmp += getSpecificity(i);
                    break;
            }
        }
        return tmp / confusionMatrix.size.doubleValue();
    }
}
