/*
 * Copyright 2021 Stanisław Florczyk
 */
package controler.exceptions;

/**
 *
 * @author Stasio
 */
public class WrongFormat extends Exception {

    /**
     * Creates a new instance of
     * <code>WrongFormat</code> without detail message.
     */
    public WrongFormat() {
    }

    /**
     * Constructs an instance of
     * <code>WrongFormat</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongFormat(String msg) {
        super(msg);
    }
}
