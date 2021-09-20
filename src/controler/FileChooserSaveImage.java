/*
 * Copyright 2021 Stanisław Florczyk
 */
package controler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.WorkSpace;

/**
 *
 * @author Stasio
 */
public class FileChooserSaveImage extends FileChooserSave
{
    WorkSpace img;
    public FileChooserSaveImage(WorkSpace p)
    {
        super();
        img = p;
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("Pliki z obrazami w formacie PNG", "png");
        fc.setFileFilter(pngFilter);
    }
    @Override
    protected void doSave(File path)
    {
        try
        {
        BufferedImage bi = new BufferedImage(img.width, img.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = bi.createGraphics();
        img.drawTree(g);
            if(fc.getSelectedFile().isFile())
            {
                ImageIO.write(bi, "png", path);
            }else
            {
                ImageIO.write(bi, "png", new File(path.toString() + ".png"));
            }
            log.append("Pomyślnie zapisano plik w folderze: " + fc.getSelectedFile() + "\n");
        } catch (Exception ex)
        {
            log.append("Nie można zapisać pliku.\n");
        } catch (OutOfMemoryError E) 
        {
            log.append("Za mało Pamięci.\n");
        }       
    }
    
}
