/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Model;
import model.TreeData;

/**
 *
 * @author Stasio
 */
public class FileChooserLoadModel extends FileChooserLoad
{
    public FileChooserLoadModel(Model m)
    {
        super(m);
        FileNameExtensionFilter modelFilter = new FileNameExtensionFilter("Pliki z modelem Drzewa Decyzyjnego", "model");
        fc.setFileFilter(modelFilter);
        openButton.setText("Wczytaj model...");
    }   
    @Override
    protected void doLoad(File f)
    {
        FileInputStream fis = null;
        try
        {   
            fis = new FileInputStream(f);
            ObjectInputStream objectinputstream = new ObjectInputStream(fis);
            model.trainingSet = null;
            model.testSet = null;
            model.root = (TreeData) objectinputstream.readObject();
            model.root.model = model;
            log.append("Drzewo wczytano poprawnie.\n");
        } catch (IOException | ClassNotFoundException ex)
        {
            log.append("Błąd Odczytu.\n");
        } finally
        {
            try
            {
                if(fis != null)
                {
                    fis.close();
                }
            } catch (IOException ex)
            {
                log.append("Nie można zamknąć pliku.\n");
            }
        }
    }
}
