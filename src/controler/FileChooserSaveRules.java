/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.TreeData;


/**
 *
 * @author Stasio
 */
public class FileChooserSaveRules extends FileChooserSave
{
    private TreeData root;
    public FileChooserSaveRules(TreeData td)
    {
        super();
        root = td;
        FileNameExtensionFilter rulesFilter = new FileNameExtensionFilter("Pliki tekstowe", "txt");
        fc.setFileFilter(rulesFilter);
    }
    @Override
    protected void doSave(File path)
    {
        PrintWriter writer = null;
        try
        {   
            if(fc.getSelectedFile().isFile())
            {
                writer = new PrintWriter(path.toString(), "UTF-8");
            }else
            {
                writer = new PrintWriter(path.toString() + ".txt", "UTF-8");
            }
            writer.println("Plik: " + root.fileName);
            writer.println();
            writer.println(root.getRules());
            log.append("Zapisano dane z pliku plik: " + root.fileName + " w:\n");
            log.append(path.toString() + ".txt\n");
        } catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            log.append("Nie można zapisać reguł.\n");
        } finally
        {
            if(writer != null)
            {
                writer.close();
            }    
        }
    }
}
