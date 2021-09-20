/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import controler.exceptions.EmptyFile;
import controler.exceptions.WrongFormat;
import model.Model;
import model.TrainingSet;

/**
 *
 * @author Stasio
 */
public class FileChooserLoadTrainingSet extends FileChooserLoad
{
    public FileChooserLoadTrainingSet(Model m)
    {
        super(m);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Pliki z danymi w formacie CSV", "csv");
        fc.setFileFilter(csvFilter);
    }  
    @Override
    protected void doLoad(File f)
    {
        try 
        { 
            model.trainingSet = new TrainingSet(f);
            log.append("Dane uczące drzewa wczytane poprawnie.\n");
            if(!model.checkTestSet())
            {
                log.append("Zbiór testowy nie pasuje do zbioru uczącego.\n");
            }
        } catch (FileNotFoundException ex) 
        {
            log.append("Nie odnaleziono pliku: " + f.getName() + ".\n");
            model.trainingSet = null;
        } catch (IOException ex) 
        {
            log.append("Bład odczytu.\n");
            model.trainingSet = null;
        } catch (WrongFormat ex) 
        {
            log.append("Plik jest uszkodzony lub zawiera niewłaściwe dane.\n");
            model.trainingSet = null;
        } catch (EmptyFile ex) 
        {
            log.append("Plik jest pusty.\n");
            model.trainingSet = null;
        } 
    }
}
