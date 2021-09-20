/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import controler.exceptions.EmptyFile;
import controler.exceptions.WrongFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Model;
import model.TestSet;

/**
 *
 * @author Stasio
 */
public class FileChooserLoadTestSet extends FileChooserLoad
{
    
    public FileChooserLoadTestSet(Model m)
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
            model.testSet = new TestSet(f);
            log.append("Dane testowe drzewa wczytane poprawnie.\n");
            if(!model.checkTestSet())
            {
                log.append("Zbiór testowy nie pasuje do zbioru uczącego.\n");
            }
        } catch (FileNotFoundException ex) 
        {
            log.append("Nie odnaleziono pliku: " + f.getName() + ".\n");
            model.testSet = null;
        } catch (IOException ex) 
        {
            log.append("Bład odczytu.\n");
            model.testSet = null;
        } catch (WrongFormat ex) 
        {
            log.append("Plik jest uszkodzony lub zawiera niewłaściwe dane.\n");
            model.testSet = null;
        } catch (EmptyFile ex) 
        {
            log.append("Plik jest pusty.\n");
            model.testSet = null;
        }
    }   
}
