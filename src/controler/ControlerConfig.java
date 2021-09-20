/*
 * Copyright 2021 Stanisław Florczyk
 */
package controler;

import java.util.List;
import model.Model;
import model.TestSet;
import view.ViewConfig;

/**
 *
 * @author Stasio
 */
public class ControlerConfig extends Controler
{
    private ViewConfig view;

    public ControlerConfig(Model m)
    {
        model = m;
    }   
    public void setAccuracy(Integer value)
    {
        model.parameters.accuracy = value;
    }
    public void skipTest(String test)
    {
        if(test == null)
        {
            return;
        }
        if(!model.parameters.skippedTests.isEmpty())
        {
            for(String s : model.parameters.skippedTests)
            {
                if(s.equals(test))
                {
                    return;
                }
            }
        }
        model.parameters.skippedTests.add(test);
        view.usedTests.addElement(test);
        view.skippedTests.removeElement(test);
    }
    public void setView(ViewConfig vp)
    {
        view = vp;
    }
    public void restoreTests(List<String> tests)
    {
        if(!tests.isEmpty())
        {
            for(String s : tests)
            {
                view.usedTests.removeElement(s);
                view.skippedTests.addElement(s);
                model.parameters.skippedTests.remove(s);
            }
        }
    }
    public void createTestSet(Integer value)
    {
        if(model.testSet == null)
        {
            if(model.trainingSet != null && model.trainingSet.table.get(0).size() > 3)
            { 
                model.testSet = new TestSet(model.trainingSet, value);
                view.infoArea.append("\nDane podzielono poprawnie\n" + 
                    "Wielkość zbioru uczącego: \t" + model.trainingSet.table.get(0).size() + "\n" + 
                    "Wielkość zbioru testowego: \t" +  model.testSet.table.get(0).size() + "\n");
            }else
            {
                view.warrningWindow("Nie wczytano danych uczących lub wczytane dane są niewystarczające aby wydzielić zbiór testowy.");
            }    
        }else
        {
            model.testSet.addToTestSet(model.trainingSet, value);
            view.infoArea.append("\nDane podzielono poprawnie\n" + 
                    "Wielkość zbioru uczącego: \t" + model.trainingSet.table.get(0).size() + "\n" + 
                    "Wielkość zbioru testowego: \t" +  model.testSet.table.get(0).size() + "\n");
        }
        view.setVisible(false);
        view.dispose();
    }

    public void restoreTrainingSet()
    {
        if(model.testSet != null)
        {
            Integer startSize = model.testSet.table.get(0).size();
            for (int i = 0; i < startSize; startSize--)
            {       
                for(int j = 0; j < model.testSet.table.size(); j++)
                {
                    model.trainingSet.table.get(j).add(model.testSet.table.get(j).remove(i));
                }
            }
            model.testSet = null;
        } 
        view.repaint();
    }
    public void updateTestsSizeLabel(int value)
    {
        Integer sizeTest = 0;
        Integer sizeTrain = 0;
        if(model.trainingSet != null)
        {
             sizeTest = (model.trainingSet.table.get(0).size() * value)/100;
             sizeTrain = model.trainingSet.table.get(0).size() - sizeTest;
        }
        if(model.testSet != null)
        {
            sizeTest += model.testSet.table.get(0).size();
        }
        view.setsSizeLabel.setText(sizeTest + " / " + sizeTrain);
    }
}
