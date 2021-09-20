/*
 * Copyright 2021 Stanisław Florczyk
 */
package model;

import controler.exceptions.EmptyFile;
import controler.exceptions.WrongFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Stasio
 */
public class TrainingSet extends LoadedData
{
    public TrainingSet(File f) throws FileNotFoundException, IOException, EmptyFile, WrongFormat
    {
        super(f);
    }
}
