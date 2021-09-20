/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.TreeData;

/**
 *
 * @author Stasio
 */
public class FileChooserSaveModel extends FileChooserSave
{
    TreeData root;
    public FileChooserSaveModel(TreeData td)
    {
        super();
        root = td;
        FileNameExtensionFilter modelFilter = new FileNameExtensionFilter("Pliki z modelem Drzewa Decyzyjnego", "model");
        fc.setFileFilter(modelFilter);
    }

    @Override
    protected void doSave(File path)
    {
        ObjectOutputStream oos = null;
        FileOutputStream fout;
        try{
            if(fc.getSelectedFile().isFile())
            {
                fout = new FileOutputStream(path.toString());
            }else
            {
                fout = new FileOutputStream(path.toString() + ".model");
            }
            oos = new ObjectOutputStream(fout);
            oos.writeObject(root);
            log.append("Zapisano plik: " + root.fileName + ".model\n");
            log.append("W Folderze: " + fc.getSelectedFile() + "\n");
        } catch (Exception e) {
            log.append("Błąd Zapisu.\n");
        } finally 
        {
            if(oos  != null)
            {
                try
                {
                    oos.close();
                } catch (IOException ex)
                {
                    log.append("Nie można zamknąć pliku.\n");
                }
            } 
        }    
    }
}
