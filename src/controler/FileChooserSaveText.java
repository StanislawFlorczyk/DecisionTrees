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

/**
 *
 * @author Stasio
 */
class FileChooserSaveText extends FileChooserSave
{
    String text;
    public FileChooserSaveText(String t)
    {
        super();
        text = t;
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
            writer.println(text);
            log.append("Zapisano przestrzeń roboczą w pliku:\n");
            log.append(path.toString() + ".txt\n");
        } catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            log.append("Nie można dokonać zapisu.\n");
        } finally
        {
            if(writer != null)
            {
                writer.close();
            }    
        }
    }
}
