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
public abstract class FileChooserSave extends FileChooser
{
    public FileChooserSave() 
    {
        super();
        window.setTitle("Zapisywanie");
        openButton.setText("Zapisz do...");
        fc.setApproveButtonText("Zapisz");
    }  
    public FileChooserSave(Model m) 
    {
        super(m);
        window.setTitle("Zapisywanie");
        openButton.setText("Zapisz do...");
        fc.setApproveButtonText("Zapisz");
    }    
    @Override
    protected void actionButtonClicked() 
    {
        int returnVal = fc.showOpenDialog(window);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            log.append("Rozpoczęto Zapisywanie.\n");
            doSave(fc.getSelectedFile());    
        } else 
        {
            log.append("Anulowano.\n");
        }
        log.setCaretPosition(log.getDocument().getLength());
    }
    protected abstract void doSave(File path);
}

