/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import javax.swing.JFileChooser;
import model.Model;

/**
 *
 * @author Stasio
 */
public abstract class FileChooserLoad extends FileChooser
{
    public FileChooserLoad(Model m)
    {
        super(m);
        window.setTitle("Wczytywanie");
        openButton.setText("Wczytaj Plik...");
        fc.setApproveButtonText("Wczytaj");
    }
    
    @Override
    protected void actionButtonClicked() 
    {
        int returnVal = fc.showOpenDialog(window);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            log.append("Rozpoczęto Wczytywanie.\n");
            doLoad(fc.getSelectedFile());     
        } else 
        {
            log.append("Anulowano.\n");
        }
        log.setCaretPosition(log.getDocument().getLength());
    }
    
    protected abstract void doLoad(File f);
}
